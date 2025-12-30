package com.hexalyte.sf_user_management_application.repository;

import com.hexalyte.sf_user_management_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}