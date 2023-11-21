package com.adil.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.adil.blog.entities.Topic;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends JpaRepository<Topic, Integer> {
}
