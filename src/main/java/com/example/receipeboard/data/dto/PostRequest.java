package com.example.receipeboard.data.dto;

import com.example.receipeboard.data.entity.Post;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequest {

    @NotEmpty(message = "제목은 필수 항목입니다")
    @Size(max=200)
    private String subject;

    @NotEmpty(message = "내용은 필수 항목입니다")
    private String content;

    public PostRequest(Post entity) {

        this.subject = entity.getSubject();
        this.content = entity.getContent();
    }
}
