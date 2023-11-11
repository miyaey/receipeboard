package com.example.receipeboard.controller;

import com.example.receipeboard.data.dto.*;
import com.example.receipeboard.service.CommentService;
import com.example.receipeboard.service.MemberService;
import com.example.receipeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id,
                                @Valid CommentRequest commentRequest, BindingResult bindingResult, Principal principal) {
        PostResponse postResponse = this.postService.getPost(id);
        MemberResponse memberResponse = this.memberService.getMember(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postResponse);
            return "post_detail";
        }
        CommentResponse commentResponse = this.commentService.create(postResponse,
                commentRequest.getContent(), memberResponse);
        return String.format("redirect:/post/detail/%s#comment_%s",    //댓글 수정도 리턴을 이렇게 주면 됨
                commentResponse.getPost().getId(), commentResponse.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("update/{id}")
    public String commentUpdate(CommentRequest commentRequest, @PathVariable("id") Integer id, Principal principal) {
        CommentResponse commentResponse = this.commentService.getComment(id);
        if(!commentResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentRequest.setContent(commentResponse.getContent());
        return "comment_update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("update/{id}")
    public String commentUpdate(@Valid CommentRequest commentRequest, BindingResult bindingResult, Principal principal,
                             @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "comment_update";
        }
        CommentResponse commentResponse = this.commentService.getComment(id);
        if (!commentResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.update(commentResponse, commentRequest.getContent());
        return String.format("redirect:/post/detail/%s#comment_%s",
                commentResponse.getPost().getId(), commentResponse.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(Principal principal, @PathVariable("id") Integer id) {
        CommentResponse commentResponse = this.commentService.getComment(id);
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제권한이 없습니다..");
        }
        if (!commentResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(commentResponse);
        return String.format("redirect:/post/detail/%s", id);
    }



}
