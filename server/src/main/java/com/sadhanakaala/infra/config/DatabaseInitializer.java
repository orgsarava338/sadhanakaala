package com.sadhanakaala.infra.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.bson.Document;
import org.springframework.lang.NonNull;

import com.sadhanakaala.constants.DbConstants;
import com.sadhanakaala.infra.component.MongoDocLoader;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

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
        try {

            Document validatorDocument = mongoDocLoader.loadValidatorDocument(validatorPath);

            // If collection doesn't exist, create with validator
            if (!mongoTemplate.collectionExists(collectionName)) {
                Document command = new Document("create", collectionName)
                    .append("validator", validatorDocument)
                    .append("validationLevel", "strict")
                    .append("validationAction", "error");
                if (command != null) {
                    mongoTemplate.executeCommand(command);
                    System.out.println("Created collection " + collectionName + " with JSON Schema validator.");
                }
            } else {
                // If exists, apply collMod to add/update validator
                Document collMod = new Document("collMod", collectionName)
                    .append("validator", validatorDocument)
                    .append("validationLevel", "strict")
                    .append("validationAction", "error");
                if (collMod != null) {
                    mongoTemplate.executeCommand(collMod);
                    System.out.println("Updated validator for collection " + collectionName);
                }
            }
        } catch (Exception ex) {
            System.err.println("Failed to create/update validator for " + collectionName + ": " + ex.getMessage());
        }
    }

    private void applyIndexes(@NonNull String collectionName, @NonNull String indexesPath) {
        try {
           List<Index> indexes = mongoDocLoader.loadIndexes(indexesPath);

        for (Index index : indexes) {
            mongoTemplate.indexOps(collectionName).ensureIndex(index);
        }
        } catch (Exception ex) {
            System.err.println("Failed to apply indexes for " + collectionName + ": " + ex.getMessage());
        }
    }
}