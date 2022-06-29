package co.fastcampus.command.infrasturcture.repository;

import co.fastcampus.command.domain.model.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends MongoRepository<Phone, Integer> {
}
