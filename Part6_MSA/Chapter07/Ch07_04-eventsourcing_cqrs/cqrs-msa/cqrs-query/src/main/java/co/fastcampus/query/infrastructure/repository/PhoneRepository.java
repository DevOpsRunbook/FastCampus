package co.fastcampus.query.infrastructure.repository;

import co.fastcampus.query.domain.model.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PhoneRepository extends MongoRepository<Phone, Integer> {

    Optional<Phone> findByName(String name);
}
