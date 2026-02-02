package orgsarava338.sadhanakaala.infra.component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import org.bson.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.lang.NonNull;

@Component
@RequiredArgsConstructor
public class MongoDocLoader {

    private final ObjectMapper objectMapper;

    @NonNull
    public Document loadValidatorDocument(@NonNull String path) {
        try (
                InputStream is = new ClassPathResource(path).getInputStream()) {

            Map<String, Object> map = objectMapper.readValue(is, new TypeReference<>() {
            });
            // if (map == null || map.isEmpty())
            // throw new IllegalStateException("Loaded file is null or empty: " + path);

            return new Document(map);

        } catch (IOException e) {
            throw new IllegalStateException("Failed to load file: " + path, e);
        }
    }

    @NonNull
    public List<Index> loadIndexes(@NonNull String path) {
        try (
                InputStream is = new ClassPathResource(path).getInputStream()) {
            List<Map<String, Object>> rawIndexes = objectMapper.readValue(is, new TypeReference<>() {
            });

            List<Index> indexes = new ArrayList<>();

            for (Map<String, Object> raw : rawIndexes) {
                Index index = new Index();

                if (raw.containsKey("name"))
                    index.named((String) raw.get("name"));

                // fields
                Map<String, Integer> fields = (Map<String, Integer>) raw.get("fields");
                fields.forEach((field, dir) -> index.on(field, dir == 1 ? Sort.Direction.ASC : Sort.Direction.DESC));

                // unique
                if (Boolean.TRUE.equals(raw.get("unique")))
                    index.unique();

                indexes.add(index);
            }

            return indexes;

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load Mongo indexes: " + path, e);
        }
    }
}
