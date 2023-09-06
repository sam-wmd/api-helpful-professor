package com.sam.apihelpfulprofessor.service.Milvus;

import com.sam.apihelpfulprofessor.dto.SearchResponseDto;
import com.sam.apihelpfulprofessor.dto.TopicDto;
import com.sam.apihelpfulprofessor.service.Langchain.LangChainService;
import com.sam.apihelpfulprofessor.service.Topic.TopicService;
import dev.langchain4j.data.embedding.Embedding;
import io.milvus.client.MilvusServiceClient;
import io.milvus.common.clientenum.ConsistencyLevelEnum;
import io.milvus.grpc.*;
import io.milvus.param.*;
import io.milvus.param.bulkinsert.BulkInsertParam;
import io.milvus.param.bulkinsert.GetBulkInsertStateParam;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.param.partition.CreatePartitionParam;
import io.milvus.response.BulkInsertResponseWrapper;
import io.milvus.response.GetBulkInsertStateWrapper;
import io.milvus.response.SearchResultsWrapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MilvusService {

    final TopicService topicService;
    static final MilvusServiceClient milvusClient = new MilvusServiceClient(
            ConnectParam.newBuilder()
                    .withHost("localhost")
                    .withPort(19530)
                    .build()
    );

    public static void loadCollection(String name){
        milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(name)
                .build());
    }

    @PostConstruct
    public void initMilvus(){
        dropSchema();
        createSchema();
    }
    public void dropSchema(){
        milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName("topic")
                .build());
    }
    public void createSchema(){
        FieldType id = FieldType.newBuilder()
                .withName("id")
                .withDataType(DataType.Int64)
                .withPrimaryKey(true)
                .withAutoID(false)
                .build();
        FieldType title = FieldType.newBuilder()
                .withName("title")
                .withDataType(DataType.VarChar)
                .withMaxLength(64)
                .build();
        FieldType category = FieldType.newBuilder()
                .withName("category")
                .withMaxLength(64)
                .withDataType(DataType.VarChar)
                .build();
        FieldType definition = FieldType.newBuilder()
                .withName("definition")
                .withDataType(DataType.VarChar)
                .withMaxLength(5000)
                .build();
        FieldType vector = FieldType.newBuilder()
                .withName("vector")
                .withDataType(DataType.FloatVector)
                .withDimension(384)
                .build();
        CreateCollectionParam createCollectionReq = CreateCollectionParam.newBuilder()
                .withCollectionName("topic")
                .withDescription("Topic search")
                .withShardsNum(2)
                .addFieldType(id)
                .addFieldType(category)
                .addFieldType(title)
                .addFieldType(definition)
                .addFieldType(vector)
                .withEnableDynamicField(false)
                .build();

        R<RpcStatus> ret = milvusClient.createCollection(createCollectionReq);
        if (ret.getStatus() != R.Status.Success.getCode()) {
            throw new RuntimeException("Failed to create collection (topic)! Error: " + ret.getMessage());
        }
        milvusClient.createPartition(
                CreatePartitionParam.newBuilder()
                        .withCollectionName("topic")
                        .withPartitionName("2023")
                        .build()
        );

        insertData();
    }

    public void insertData(){
        loadCollection("topic");
        BulkInsertParam param = BulkInsertParam.newBuilder()
                .withCollectionName("topic")
                .addFile("socio-data-embeddings.json")
                .build();
        R<ImportResponse> response = milvusClient.bulkInsert(param);
        BulkInsertResponseWrapper wrapper = new BulkInsertResponseWrapper(response.getData());
        Long taskId = wrapper.getTaskID();
        checkTaskState(taskId);
    }


    ImportState checkTaskState(Long taskId){
        GetBulkInsertStateParam param = GetBulkInsertStateParam.newBuilder()
                .withTask(taskId)
                .build();

        R<GetImportStateResponse> response = milvusClient.getBulkInsertState(param);
        GetBulkInsertStateWrapper wrapper = new GetBulkInsertStateWrapper(response.getData());
        ImportState state = wrapper.getState();
        long importedCount;
        String failedReason;
        int progress;

        while (state.name() != ImportState.ImportCompleted.name()){
            response = milvusClient.getBulkInsertState(param);
            wrapper = new GetBulkInsertStateWrapper(response.getData());
            state = wrapper.getState();
            importedCount = wrapper.getImportedCount();
            failedReason = wrapper.getFailedReason();
            progress = wrapper.getProgress();
            log.info(state.name());
            if (state.equals(ImportState.ImportFailed)) {
                log.info("Failed reason : " + failedReason);
                break;
            } else {
                log.info("Progress : " + progress);
                log.info("Imported count : "+importedCount);
            }

        }
        return state;
    }

    public SearchResponseDto query(Embedding searchVector){
        final IndexType INDEX_TYPE = IndexType.IVF_FLAT;   // IndexType
        final String INDEX_PARAM = "{\"nlist\":1500}";     // ExtraParam
        milvusClient.createIndex(
                CreateIndexParam.newBuilder()
                        .withCollectionName("topic")
                        .withFieldName("vector")
                        .withIndexType(INDEX_TYPE)
                        .withMetricType(MetricType.L2)
                        .withSyncMode(Boolean.FALSE)
                        .withExtraParam(INDEX_PARAM)
                        .build()
        );

        loadCollection("topic");
        final Integer SEARCH_K = 5;                       // TopK
        final String SEARCH_PARAM = "{\"nprobe\":10, \"offset\":5}";    // Params
        List<String> search_output_fields = Arrays.asList("title");
        List<List<Float>> searchVectorList = Arrays.asList(searchVector.vectorAsList());
        SearchParam searchParam = SearchParam.newBuilder()
                .withCollectionName("topic")
                .withConsistencyLevel(ConsistencyLevelEnum.STRONG)
                .withMetricType(MetricType.L2)
                .withOutFields(search_output_fields)
                .withTopK(SEARCH_K)
                .withVectors(searchVectorList)
                .withVectorFieldName("vector")
                .build();
        R<SearchResults> respSearch = milvusClient.search(searchParam);
        SearchResultsWrapper wrapperSearch = new SearchResultsWrapper(respSearch.getData().getResults());
        List<SearchResultsWrapper.IDScore> idScores = wrapperSearch.getIDScore(0);
        List<?> titleList = wrapperSearch.getFieldData("title", 0);
        log.info("idScores" + idScores);
        log.info("titleList : " + titleList);
        Float score = idScores.get(0).getScore();
        String title = titleList.get(0).toString();

        TopicDto topicDto = topicService.findTopicWithExamples(title);

        return new SearchResponseDto(topicDto,score);
    }


    public SearchResponseDto search(String search){
        Embedding embedding = LangChainService.EMBEDDING_MODEL.embed(search);
        return query(embedding);
    }
}
