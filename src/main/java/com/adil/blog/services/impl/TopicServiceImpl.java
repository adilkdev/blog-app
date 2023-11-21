package com.adil.blog.services.impl;

import com.adil.blog.entities.Topic;
import com.adil.blog.exceptions.ResourceNotFoundException;
import com.adil.blog.payloads.TopicDto;
import com.adil.blog.repositories.TopicRepo;
import com.adil.blog.services.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public TopicDto createTopic(TopicDto topicDto) {
        Topic topic = topicRepo.save(toTopic(topicDto));
        return toTopicDto(topic);
    }

    @Override
    public TopicDto updateTopic(TopicDto topicDto) {
        Topic topic = topicRepo.findById(topicDto.getTopicId())
                .orElseThrow(
                        ()-> new ResourceNotFoundException("Topic", "topic Id", topicDto.getTopicId())
                );
        topic.setTopicTitle(topicDto.getTopicTitle());
        topic.setTopicDescription(topicDto.getTopicDescription());
        Topic updatedTopic = topicRepo.save(topic);
        return toTopicDto(updatedTopic);
    }

    @Override
    public void deleteTopic(Integer topicId) {
        Topic topic = topicRepo.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "Topic Id", topicId)
        );
        topicRepo.delete(topic);
    }

    @Override
    public TopicDto getTopicById(Integer topicId) {
        Topic topic = topicRepo.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "Topic Id", topicId)
        );
        return toTopicDto(topic);
    }

    @Override
    public List<TopicDto> getTopics() {
        List<Topic> topics = topicRepo.findAll();
        return topics.stream()
                .map(this::toTopicDto)
                .collect(Collectors.toList());
    }

    private TopicDto toTopicDto(Topic topic) {
        return mapper.map(topic, TopicDto.class);
    }

    private Topic toTopic(TopicDto topicDto) {
        return mapper.map(topicDto, Topic.class);
    }
}
