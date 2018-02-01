package org.dbs.mydoc.repository;

import org.dbs.mydoc.persistence.document.DBPatient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends MongoRepository<DBPatient, Integer> {

}
