package org.dbs.mydoc.repository;

import java.util.List;

import org.dbs.mydoc.persistence.document.DBConsultation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends MongoRepository<DBConsultation, Integer> {

	public List<DBConsultation> findByDoctorIdAndStatus(String doctortId, String status);

	public List<DBConsultation> findByHealthWorkerIdAndStatus(String healthWorkerId, String status);

	public DBConsultation findByConsultationId(String consultationId);

}
