package com.adil.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicDto {

    private Integer topicId;

    @NotBlank
    @Size(min = 3, message = "Minimum topic length is 3")
    private String topicTitle;

    @NotBlank
    @Size(min = 10, message = "Minimum topic description length is 10")
    private String topicDescription;

}
