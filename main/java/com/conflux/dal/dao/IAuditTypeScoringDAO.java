package com.conflux.dal.dao;import java.util.List;import com.conflux.dal.bo.AuditTypeScoring;public interface IAuditTypeScoringDAO {	public abstract void persist(AuditTypeScoring auditTypeScoring);	public abstract void merge(AuditTypeScoring auditTypeScoring);	public abstract void delete(AuditTypeScoring auditTypeScoring);	public abstract List<AuditTypeScoring> findAll();	public abstract AuditTypeScoring findById(int auditTypeScoringId);}