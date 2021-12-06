package com.fullstackproject.repositories;

import com.fullstackproject.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Query(value =
            "select u.username from users as u where u.username like %:username%",
            nativeQuery = true)
    List<String> findAllBySimilarUsername(@Param("username") String username);
}
