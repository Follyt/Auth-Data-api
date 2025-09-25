package com.auth.aService.Repository;

import com.auth.aService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

//Аннотация не нужна потому что JpaRepo наследуется от Repository через кучу других наследников
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

}
