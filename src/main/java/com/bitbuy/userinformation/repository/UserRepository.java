package com.bitbuy.userinformation.repository;

import com.bitbuy.userinformation.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT n FROM User n WHERE n.username = ?1")
    User findByUsername(String userName);

}
