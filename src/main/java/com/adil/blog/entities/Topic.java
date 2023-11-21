package com.adil.blog.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicId;

    @Column(name = "title", length = 100, nullable = false)
    private String topicTitle;

    @Column(name = "description")
    private String topicDescription;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

}
