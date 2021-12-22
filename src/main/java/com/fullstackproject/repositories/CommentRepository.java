package com.fullstackproject.repositories;

import com.fullstackproject.models.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {

    @Query("select c.joke.id from  Comment as c where  c.ownerOfComment = :username")
    List<String> findJokeIdsByCommentsByUsername(@Param("username") String username);
}
