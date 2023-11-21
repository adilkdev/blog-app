package com.adil.blog.services.impl;

import com.adil.blog.entities.Comment;
import com.adil.blog.entities.Post;
import com.adil.blog.exceptions.ResourceNotFoundException;
import com.adil.blog.payloads.CommentDto;
import com.adil.blog.repositories.CommentRepo;
import com.adil.blog.repositories.PostRepo;
import com.adil.blog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "Post Id", postId)
        );
        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setPost(post);
        Comment savedComment = commentRepo.save(comment);
        return mapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "Comment Id", commentId)
        );
        commentRepo.delete(comment);
    }
}
