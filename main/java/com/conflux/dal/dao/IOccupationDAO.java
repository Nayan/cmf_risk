package com.conflux.dal.dao;import java.util.List;import com.conflux.dal.bo.Occupation;public interface IOccupationDAO {	public abstract void persist(Occupation occupation);	public abstract void merge(Occupation occupation);	public abstract void delete(Occupation occupation);	public abstract List<Occupation> findAll();	public abstract Occupation findById(int occupationId);}