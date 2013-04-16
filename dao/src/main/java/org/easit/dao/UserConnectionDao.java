package org.easit.dao;

import java.util.List;

import org.easit.dao.model.UserConnection;

public interface UserConnectionDao extends GenericDao<UserConnection> {

    public List<UserConnection> getUserConnectionsByProvider(String username, String providerId);

    public UserConnection getMainConnection(String username, String providerId);

    public void updateRefreshToken(String name, String refreshToken, String provider);

}
