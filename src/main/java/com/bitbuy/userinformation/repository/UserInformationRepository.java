package com.bitbuy.userinformation.repository;

import com.bitbuy.userinformation.domain.UserInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserInformationRepository extends CrudRepository<UserInformation, Long> {

    @Query("SELECT n FROM UserInformation n, User m WHERE m.id= ?1")
    UserInformation findOneByUuid(UUID nodeUuid);

}
