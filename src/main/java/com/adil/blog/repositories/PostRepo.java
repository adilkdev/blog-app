package com.adil.blog.repositories;

import com.adil.blog.entities.Post;
import com.adil.blog.entities.Topic;
import com.adil.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByTopic(Topic topic);
    List<Post> findByUser(User user);
    List<Post> findByTitleContaining(String title);

}
