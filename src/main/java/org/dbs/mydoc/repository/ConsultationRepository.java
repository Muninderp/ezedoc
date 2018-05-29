package org.dbs.mydoc.repository;

import java.util.List;

import org.dbs.mydoc.persistence.document.DBConsultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ConsultationRepository extends MongoRepository<DBConsultation, Integer> {

	public List<DBConsultation> findByDoctorId(String doctortId);

	public List<DBConsultation> findByHealthWorkerId(String healthWorkerId);

	public DBConsultation findByConsultationId(String consultationId);

}
