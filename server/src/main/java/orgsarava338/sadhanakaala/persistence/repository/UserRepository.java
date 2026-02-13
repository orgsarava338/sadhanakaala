package orgsarava338.sadhanakaala.persistence.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import orgsarava338.sadhanakaala.persistence.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    String getUserTimeZoneById(String userId);

    Optional<UserEntity> findByUid(String uid);
}