package com.sadhanakaala.config;

import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;

@Configuration
@ConditionalOnProperty(name = "sadhanakaala.db-init", havingValue = "true", matchIfMissing = true)
public class DatabaseInitializer {

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    public void init() {
        createCollectionWithValidator("users", usersValidator());
        createCollectionWithValidator("timers", timersValidator());
        createCollectionWithValidator("timer_sessions", sessionsValidator());

        // Ensure indexes
        mongoTemplate.indexOps("users").ensureIndex(new Index().on("username", org.springframework.data.domain.Sort.Direction.ASC).unique());
        mongoTemplate.indexOps("users").ensureIndex(new Index().on("email", org.springframework.data.domain.Sort.Direction.ASC).unique());

        mongoTemplate.indexOps("timers").ensureIndex(new Index().on("ownerId", org.springframework.data.domain.Sort.Direction.ASC));
        mongoTemplate.indexOps("timers").ensureIndex(new Index().on("lastUsedAt", org.springframework.data.domain.Sort.Direction.DESC));
        mongoTemplate.indexOps("timers").ensureIndex(new Index().on("recurrence.nextRunAt", org.springframework.data.domain.Sort.Direction.ASC));

        mongoTemplate.indexOps("timer_sessions").ensureIndex(new Index().on("ownerId", org.springframework.data.domain.Sort.Direction.ASC));
        mongoTemplate.indexOps("timer_sessions").ensureIndex(new Index().on("startedAt", org.springframework.data.domain.Sort.Direction.DESC));
        mongoTemplate.indexOps("timer_sessions").ensureIndex(new Index().on("timerId", org.springframework.data.domain.Sort.Direction.ASC));
    }

    private void createCollectionWithValidator(@NonNull String collectionName, @NonNull String jsonSchema) {
        try {
            // If collection doesn't exist, create with validator
            if (!mongoTemplate.collectionExists(collectionName)) {
                Document command = Document.parse("{ create: \"" + collectionName + "\", validator: " + jsonSchema + ", validationLevel: \"strict\", validationAction: \"error\" }");
                if (command != null) {
                    mongoTemplate.executeCommand(command);
                    System.out.println("Created collection " + collectionName + " with JSON Schema validator.");
                }
            } else {
                // If exists, apply collMod to add/update validator
                Document collMod = Document.parse("{ collMod: \"" + collectionName + "\", validator: " + jsonSchema + ", validationLevel: \"strict\", validationAction: \"error\" }");
                if (collMod != null) {
                    mongoTemplate.executeCommand(collMod);
                    System.out.println("Updated validator for collection " + collectionName);
                }
            }
        } catch (Exception ex) {
            System.err.println("Failed to create/update validator for " + collectionName + ": " + ex.getMessage());
        }
    }

    // The JSON schemas here should match the validators you already designed.
    // For readability we embed shorter versions — replace with your complete JSON Schema strings if needed.
    @NonNull
    private String usersValidator() {
        return "{ \"$jsonSchema\": { \"bsonType\": \"object\", \"required\": [\"username\",\"email\",\"createdAt\",\"updatedAt\"], " +
                "\"properties\": { \"username\": { \"bsonType\": \"string\", \"minLength\": 3 }, \"email\": { \"bsonType\": \"string\" }, \"createdAt\": { \"bsonType\": \"date\" }, \"updatedAt\": { \"bsonType\": \"date\" } } } }";
    }

    @NonNull
    private String timersValidator() {
        return "{ \"$jsonSchema\": { \"bsonType\": \"object\", \"required\": [\"ownerId\",\"title\",\"durationSeconds\",\"createdAt\",\"updatedAt\"], " +
                "\"properties\": { \"ownerId\": { \"bsonType\":\"objectId\" }, \"title\": { \"bsonType\":\"string\" }, \"durationSeconds\": { \"bsonType\": [\"int\",\"long\"], \"minimum\": 1 }, \"createdAt\": { \"bsonType\": \"date\" }, \"updatedAt\": { \"bsonType\": \"date\" } } } }";
    }

    @NonNull
    private String sessionsValidator() {
        return "{ \"$jsonSchema\": { \"bsonType\": \"object\", \"required\": [\"timerId\",\"ownerId\",\"startedAt\",\"createdAt\"], " +
                "\"properties\": { \"timerId\": { \"bsonType\":\"objectId\" }, \"ownerId\": { \"bsonType\":\"objectId\" }, \"startedAt\": { \"bsonType\":\"date\" }, \"createdAt\": { \"bsonType\": \"date\" } } } }";
    }
}