package orgsarava338.sadhanakaala.infra.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.bson.Document;
import org.springframework.lang.NonNull;

import orgsarava338.sadhanakaala.constants.DbConstants;
import orgsarava338.sadhanakaala.infra.component.MongoDocLoader;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Slf4j
@Configuration
@ConditionalOnProperty(name = "sadhanakaala.db-init", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class DatabaseInitializer {

    private final MongoTemplate mongoTemplate;
    private final MongoDocLoader mongoDocLoader;

    @PostConstruct
    public void init() {
        createCollectionWithValidator(DbConstants.USERS_COLLECTION, DbConstants.USERS_VALIDATOR_PATH);
        createCollectionWithValidator(DbConstants.TIMERS_COLLECTION, DbConstants.TIMERS_VALIDATOR_PATH);
        createCollectionWithValidator(DbConstants.TIMER_SESSIONS_COLLECTION, DbConstants.TIMER_SESSIONS_VALIDATOR_PATH);

        applyIndexes(DbConstants.USERS_COLLECTION, DbConstants.USERS_INDEXES_PATH);
        applyIndexes(DbConstants.TIMERS_COLLECTION, DbConstants.TIMERS_INDEXES_PATH);
        applyIndexes(DbConstants.TIMER_SESSIONS_COLLECTION, DbConstants.TIMER_SESSIONS_INDEXES_PATH);
    }

    private void createCollectionWithValidator(@NonNull String collectionName, @NonNull String validatorPath) {
        String action = "Initialize";
        try {

            Document validatorDocument = mongoDocLoader.loadValidatorDocument(validatorPath);

            boolean isCollectionExists = mongoTemplate.collectionExists(collectionName);
            String documentKey = isCollectionExists ? "collMod" : "create";
            action = isCollectionExists ? "Update" : "Create";

            Document command = new Document(documentKey, collectionName)
                    .append("validator", validatorDocument)
                    .append("validationLevel", "strict")
                    .append("validationAction", "error");
            if (command != null) {
                mongoTemplate.executeCommand(command);
                log.info("✓ {} collection {} with JSON Schema validator is successful.", action, collectionName);
            }

        } catch (Exception ex) {
            log.error("✗ Failed to {} validator for collection {}", action, collectionName, ex);
        }
    }

    private void applyIndexes(@NonNull String collectionName, @NonNull String indexesPath) {
        try {
            List<Index> indexes = mongoDocLoader.loadIndexes(indexesPath);

            for (Index index : indexes) {
                mongoTemplate.indexOps(collectionName).createIndex(index);
            }
        } catch (Exception ex) {
            log.error("✗ Failed to apply indexes for collection {}", collectionName, ex);
        }
    }
}