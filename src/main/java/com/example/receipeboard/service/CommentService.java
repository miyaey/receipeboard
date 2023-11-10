package com.example.receipeboard.service;

import com.example.receipeboard.config.DataNotFoundException;
import com.example.receipeboard.data.dto.CommentResponse;
import com.example.receipeboard.data.dto.MemberResponse;
import com.example.receipeboard.data.dto.PostResponse;
import com.example.receipeboard.data.entity.Comment;
import com.example.receipeboard.data.entity.Post;
import com.example.receipeboard.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;

    private Comment of(CommentResponse commentResponse) {

        return modelMapper.map(commentResponse, Comment.class);
    }
    private CommentResponse of(Comment comment) {

        return modelMapper.map(comment, CommentResponse.class);
    }


    public CommentResponse create(PostResponse postResponse, String content, MemberResponse author) {
        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setContent(content);
//        commentResponse.setCreatedDate(LocalDateTime.now());
        commentResponse.setPost(postResponse);
        commentResponse.setAuthor(author);
        Comment comment = of(commentResponse);
        comment = this.commentRepository.save(comment);
        commentResponse.setId(comment.getId());
        return commentResponse;
    }

    public CommentResponse getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return  of(comment.get());
        }else {
            throw new DataNotFoundException("해당 게시글이 없습니다");
        }
    }

    public CommentResponse update(CommentResponse commentResponse, String content) {
        commentResponse.setContent(content);
        Comment comment = of(commentResponse);
        this.commentRepository.save(comment);
        return commentResponse;
    }

    public void delete(CommentResponse commentResponse) {
        this.commentRepository.deleteById(commentResponse.getId());
    }

}
