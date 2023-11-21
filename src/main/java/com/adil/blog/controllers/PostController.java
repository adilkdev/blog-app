package com.adil.blog.controllers;

import com.adil.blog.config.AppConstants;
import com.adil.blog.payloads.ApiResponse;
import com.adil.blog.payloads.PostDto;
import com.adil.blog.payloads.PostResponse;
import com.adil.blog.services.FileService;
import com.adil.blog.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;

import static com.adil.blog.config.AppConstants.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/topic/{topicId}/posts")
    public ResponseEntity<PostDto> createPost(
            @RequestBody PostDto postDto,
            @PathVariable Integer userId,
            @PathVariable Integer topicId
    ) {
        PostDto createPost = postService.createPost(postDto, userId, topicId);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(
            @PathVariable Integer userId
    ) {
        List<PostDto> posts = postService.getPostByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/topic/{topicId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByTopic(
            @PathVariable Integer topicId
    ) {
        List<PostDto> posts = postService.getPostByTopicId(topicId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = SORT_DIR, required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
        PostDto postDto = postService.getPostById(postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse deletePost(@PathVariable Integer postId) {
        postService.deletePost(postId);
        return new ApiResponse("Post is successfully deleted", true);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
        PostDto updatePost = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(updatePost, HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(
            @PathVariable String keyword
    ) {
        List<PostDto> result = postService.searchPosts(keyword);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {
        PostDto postDto = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        postService.updatePost(postDto, postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable String imageName, HttpServletResponse response
    ) throws IOException
    {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

}
