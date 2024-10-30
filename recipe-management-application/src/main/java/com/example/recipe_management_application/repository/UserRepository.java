package com.example.recipe_management_application.repository;

import com.example.recipe_management_application.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>  {
    User findByUsername(String username);

    void delete(User user);
}
