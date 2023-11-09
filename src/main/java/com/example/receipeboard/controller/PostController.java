package com.example.receipeboard.controller;

import com.example.receipeboard.data.dto.CommentRequest;
import com.example.receipeboard.data.dto.MemberResponse;
import com.example.receipeboard.data.dto.PostRequest;
import com.example.receipeboard.data.dto.PostResponse;
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

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {
    private final PostService postService;
    private final PostResponse postResponse;
    private final MemberService memberService;

    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, CommentRequest commentRequest) {
        PostResponse post = this.postService.getPost(id);
        model.addAttribute("post", post);
        return "post_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String postCreate(PostRequest postRequest) {

        return "post_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@Valid PostRequest postRequest,
                             BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "post_form";
        }
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "등록 권한이 없습니다.");
        }
        MemberResponse memberResponse = this.memberService.getMember(principal.getName());
        this.postService.create(postRequest.getSubject(), postRequest.getContent(), memberResponse);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("update/{id}")
    public String postUpdate(PostRequest postRequest, @PathVariable("id") Integer id, Principal principal) {
        PostResponse postResponse = this.postService.getPost(id);
        if(!postResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        postRequest.setSubject(postResponse.getSubject());
        postRequest.setContent(postResponse.getContent());
        return "post_update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("update/{id}")
    public String postUpdate(@Valid PostRequest postRequest, BindingResult bindingResult, Principal principal,
                             @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "post_update";
        }
        PostResponse postResponse = this.postService.getPost(id);
        if (!postResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.postService.update(postResponse, postRequest.getSubject(), postRequest.getContent());
        return String.format("redirect:/post/detail/%s", id);   //  /post/detail/{id}로 리디렉션
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String postdelete(Principal principal, @PathVariable("id") Integer id) {
        PostResponse postResponse = this.postService.getPost(id);
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제권한이 없습니다..");
        }
        if (!postResponse.getAuthor().getNickname().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.postService.delete(postResponse);
        return "redirect:/post/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String postVote(Principal principal, @PathVariable("id") Integer id) {
        PostResponse postResponse = this.postService.getPost(id);
        MemberResponse memberResponse = this.memberService.getMember(principal.getName());
        this.postService.vote(postResponse, memberResponse);
        return String.format("redirect:/post/detail/%s", id);
    }
}