package com.fullstackproject.repositories;

import com.fullstackproject.models.entities.Joke;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JokeRepository extends JpaRepository<Joke, String> {

    @Query("select j from  Joke  as j where  j.user.username = :username")
    List<Joke> findAllByUsername(String username);


    @Query("select j from  Joke  as j order by j.createdDate desc")
    List<Joke> findLastThree();

    @Query("select j from Joke as j  where j.keyword like %:keyword%")
//    @Query(nativeQuery = true,
//            value = "select * from jokes as j where (j.keyword REGEXP :keyword)")
    List<Joke> findAllByKeyword(@Param("keyword") String keyword);


    @Query("select j from Joke as j order by  j.likes.size desc ")
    List<Joke> findJokeWithMostLikes();
}
