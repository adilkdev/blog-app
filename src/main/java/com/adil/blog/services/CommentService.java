package com.adil.blog.services;

import com.adil.blog.payloads.CommentDto;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);

}
