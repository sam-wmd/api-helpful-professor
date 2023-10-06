package com.sam.apihelpfulprofessor.service.Langchain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sam.apihelpfulprofessor.mapper.TopicMapper;
import com.sam.apihelpfulprofessor.model.Topic;
import com.sam.apihelpfulprofessor.repository.TopicRepository;
import dev.langchain4j.chain.ConversationalRetrievalChain;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.internal.Json;
import dev.langchain4j.model.inprocess.InProcessEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import static dev.langchain4j.model.inprocess.InProcessEmbeddingModelType.ALL_MINILM_L6_V2;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class LangChainService {


    private String OPENAI_TOKEN;

    public static OpenAiChatModel CHAT_MODEL;
    public static InProcessEmbeddingModel EMBEDDING_MODEL = new InProcessEmbeddingModel(ALL_MINILM_L6_V2);

    public static ConversationalRetrievalChain CHAIN;

    EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
    EmbeddingStoreIngestor ingestor;


    TopicMapper topicMapper = TopicMapper.INSTANCE;
    private final TopicRepository topicRepository;

    @Autowired
    public LangChainService(@Value("${openaitoken}") String token,
                            TopicRepository topicRepository) {
        this.OPENAI_TOKEN = token;
        CHAT_MODEL = OpenAiChatModel.withApiKey(OPENAI_TOKEN);

        this.topicRepository = topicRepository;
    }


//    @PostConstruct
    public void buildEmbeddingStoreFromFile() throws IOException {
        List<Topic> topics = loadTopicsFromFile("static/socio-data.json");
        buildEmbeddingStore(topics);
    }

    @PostConstruct
    public void buildEmbeddingStoreFromDb() throws IOException {
        List<Topic> topics = topicRepository.findAll();
        buildEmbeddingStore(topics);
    }


    public void buildEmbeddingStore(List<Topic> topics) {
        ingestor = EmbeddingStoreIngestor.builder()
                .splitter(new CustomSentenceSplitter())
                .embeddingModel(EMBEDDING_MODEL)
                .embeddingStore(embeddingStore)
                .build();
        topics.forEach(topic -> {
            ingestor.ingest(Document.from(Json.toJson(topicMapper.toDto(topic))));
        });

        // Build LLM chain with a knowledge base of all the data from the file
        CHAIN = ConversationalRetrievalChain.builder()
                .chatLanguageModel(CHAT_MODEL)
                .retriever(EmbeddingStoreRetriever.from(embeddingStore, EMBEDDING_MODEL))
                .build();
    }


    private List<Topic> loadTopicsFromFile(String filename) throws IOException {
        // Load file and embed each object
        File file = new ClassPathResource(filename).getFile();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, new TypeReference<List<Topic>>() {});
    }


}
