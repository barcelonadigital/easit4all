package org.easit.dao;

import java.util.List;

import org.easit.dao.jpa.UsernameAlreadyInUseException;
import org.easit.dao.model.EasitAccount;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface EasitAccountDao extends GenericDao<EasitAccount> {

    void createAccount(EasitAccount user, PasswordEncoder passwordEncoder) throws UsernameAlreadyInUseException;

    List<EasitAccount> getAccounts();

    EasitAccount findAccountByUsername(String username);

    int changeAccountSettings(EasitAccount user, PasswordEncoder passwordEncoder);

    EasitAccount findAccountByUserToken(String userToken);

}
