package org.easit.dao.jpa;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.easit.dao.GenericDao;
import org.easit.dao.model.DomainObject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository(value = "genericDao")
@SuppressWarnings("unchecked")
public class GenericDaoJpa<T extends DomainObject> implements GenericDao<T> {

    private Class<T> type;

    protected EntityManager entityManager;

    public GenericDaoJpa(Class<T> type) {
	super();
	this.type = type;
    }

    public GenericDaoJpa() {

    }

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
	this.entityManager = entityManager;
    }

    public T get(Long id) {
	try {
	    return (T) entityManager.find(type, id);
	} catch (Exception e) {
	    return null;
	}

    }

    public T get(String id) {
	try {
	    return (T) entityManager.find(type, id);
	} catch (Exception e) {
	    return null;
	}
    }

    public List<T> getAll() {
	return entityManager.createQuery("select obj from " + type.getName() + " obj").getResultList();
    }

    @Transactional(readOnly = false)
    public void save(T object) throws DataAccessException {
	entityManager.persist(object);
    }

    @Transactional(readOnly = false)
    public void delete(T object) throws DataAccessException {
	entityManager.remove(object);
    }

    @Transactional(readOnly = false)
    public void delete(Long id) throws DataAccessException {
	entityManager.remove(entityManager.getReference(type, id));
    }

    @Transactional(readOnly = false)
    public void delete(String id) throws DataAccessException {
	entityManager.remove(entityManager.getReference(type, id));
    }

    @Transactional(readOnly = false)
    public void update(T object) {
	entityManager.merge(object);

    }

    public HashMap<String, Object> getPagedList(Query querySelect, Query queryCount, int maxResults, int page) {
	HashMap<String, Object> resultMap = new HashMap<String, Object>();

	querySelect.setMaxResults(maxResults);
	querySelect.setFirstResult(maxResults * (page - 1));

	resultMap.put("rows", querySelect.getResultList());
	resultMap.put("count", queryCount.getSingleResult());

	return resultMap;
    }
}
