package org.easit.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.easit.dao.model.LogOperations;

public interface LogOperationsDao extends GenericDao<LogOperations> {

    List<LogOperations> findTop5OperationsByUserId(long userId, List<String> disProvs, int maxResults);

    List<LogOperations> findTop2OperationsByProviderId(long userId, String providerId, int maxResults);

    void save(long userId, String providerId, String operation, Timestamp time);

    Date getLastDate(long userId, String providerId, String operation);

}
