package com.adil.blog.services;

import com.adil.blog.payloads.PostDto;
import com.adil.blog.payloads.PostResponse;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Integer userId, Integer topicId);

    PostDto updatePost(PostDto postDto, Integer postId);

    void deletePost(Integer postId);

    PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

    PostDto getPostById(Integer postId);

    List<PostDto> getPostByTopicId(Integer topicId);

    List<PostDto> getPostByUserId(Integer userId);

    List<PostDto> searchPosts(String keyword);

}
