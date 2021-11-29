package com.fullstackproject.repositories;

import com.fullstackproject.models.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<Joke, String> {

    @Query("select j from  Joke  as j where  j.user.username = :username")
    List<Joke> findAllByUsername(String username);

    @Query("select j from  Joke  as j order by j.createdDate asc")
    List<Joke> findLastThree();
}
