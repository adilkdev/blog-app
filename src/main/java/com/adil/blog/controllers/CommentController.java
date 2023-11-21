package com.adil.blog.controllers;

import com.adil.blog.entities.Comment;
import com.adil.blog.payloads.ApiResponse;
import com.adil.blog.payloads.CommentDto;
import com.adil.blog.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId) {
        CommentDto createdCommentDto = commentService.createComment(commentDto, postId);
        return new ResponseEntity<>(createdCommentDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        commentService.deleteComment(commentId);
        return new ResponseEntity<>(new ApiResponse("Comment successfully deleted!", true), HttpStatus.OK);
    }

}
