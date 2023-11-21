package com.adil.blog.services.impl;

import com.adil.blog.entities.Post;
import com.adil.blog.entities.Topic;
import com.adil.blog.entities.User;
import com.adil.blog.exceptions.ResourceNotFoundException;
import com.adil.blog.payloads.PostDto;
import com.adil.blog.payloads.PostResponse;
import com.adil.blog.repositories.PostRepo;
import com.adil.blog.repositories.TopicRepo;
import com.adil.blog.repositories.UserRepo;
import com.adil.blog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TopicRepo topicRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer topicId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "User id", userId)
        );
        Topic topic = topicRepo.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "Topic id", topicId)
        );

        Post post = toPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user);
        post.setTopic(topic);

        return toPostDto(postRepo.save(post));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Post id", postId)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());

        Post updatedPost = postRepo.save(post);
        return toPostDto(updatedPost);
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Post id", postId)
        );
        postRepo.delete(post);
    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort = Sort.by(sortBy);
        sort = sortDir.equalsIgnoreCase("asc") ? sort.ascending() : sort.descending();
        Pageable page = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = postRepo.findAll(page);
        List<Post> allPosts = pagePost.getContent();
        List<PostDto> postDtos = allPosts.stream()
                .map(this::toPostDto)
                .toList();
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Post Id", postId)
        );
        return toPostDto(post);
    }

    @Override
    public List<PostDto> getPostByTopicId(Integer topicId) {
        Topic topic = topicRepo.findById(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic", "Topic Id", topicId)
        );
        List<Post> posts = postRepo.findByTopic(topic);
        return posts.stream().map(this::toPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostByUserId(Integer userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User", "User id", userId)
        );
        List<Post> posts = postRepo.findByUser(user);
        return posts.stream().map(this::toPostDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        return posts.stream().map(this::toPostDto).collect(Collectors.toList());
    }

    public PostDto toPostDto(Post post) {
        return modelMapper.map(post, PostDto.class);
    }

    public Post toPost(PostDto postDto) {
        return modelMapper.map(postDto, Post.class);
    }
}
