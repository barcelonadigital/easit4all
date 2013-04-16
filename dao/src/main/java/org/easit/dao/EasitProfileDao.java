package org.easit.dao;

import org.easit.dao.model.EasitProfile;

public interface EasitProfileDao extends GenericDao<EasitProfile> {

    public void updateProfile(EasitProfile profile);

    public EasitProfile getProfileByUserId(long userId);
}
