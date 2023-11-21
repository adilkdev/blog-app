package com.adil.blog.services;

import com.adil.blog.payloads.TopicDto;

import java.util.List;

public interface TopicService {

    TopicDto createTopic(TopicDto topicDto);

    TopicDto updateTopic(TopicDto topicDto);

    void deleteTopic(Integer topicId);

    TopicDto getTopicById(Integer topicId);

    List<TopicDto> getTopics();
}
