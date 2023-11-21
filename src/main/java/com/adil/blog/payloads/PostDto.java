package com.adil.blog.payloads;

import com.adil.blog.entities.Comment;
import com.adil.blog.entities.Topic;
import com.adil.blog.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class PostDto {

    private String postId;

    private String title;

    private String content;

    private String imageName;

    private Date addedDate;

    private TopicDto topic;

    private UserDto user;

    private List<CommentDto> comments = new ArrayList<>();

}
