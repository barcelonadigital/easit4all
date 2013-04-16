package org.easit.dao;

import java.util.HashMap;
import java.util.List;

import javax.persistence.Query;

import org.easit.dao.model.DomainObject;

/**
 * Base Interface for all DAOs, with basic CRUD operations.
 * 
 * @param <T>: entity persisted/manipulted by this DAO
 */
public interface GenericDao<T extends DomainObject> {

    public T get(Long id);

    public T get(String id);

    public List<T> getAll();

    public void save(T object);

    public void delete(T object);

    public void delete(Long id);

    public void delete(String id);

    public void update(T object);

    public HashMap<String, Object> getPagedList(Query querySelect, Query queryCount, int maxResults, int page);

}
