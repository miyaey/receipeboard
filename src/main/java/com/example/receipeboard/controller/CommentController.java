package com.example.receipeboard.controller;

import com.example.receipeboard.data.dto.CommentRequest;
import com.example.receipeboard.data.dto.CommentResponse;
import com.example.receipeboard.data.dto.MemberResponse;
import com.example.receipeboard.data.dto.PostResponse;
import com.example.receipeboard.service.CommentService;
import com.example.receipeboard.service.MemberService;
import com.example.receipeboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class CommentController {

    private final PostService postService;
    private final CommentService commentService;
    private final MemberService memberService;

    //    현재 로그인한 사용자에 대한 정보를 알기 위해서는 스프링 시큐리티가 제공하는 Principal 객체를 사용해야 한다.
//    메서드에 Principal 객체를 매개변수로 지정하면 된다.
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(Model model, @PathVariable("id") Integer id,
                                @Valid CommentRequest commentRequest, BindingResult bindingResult, Principal principal) {
        PostResponse postResponse = this.postService.getPost(id);
        MemberResponse memberResponse = this.memberService.getMember(principal.getName());   //현재 로그인한 사용자의 사용자명(nickname)
        if (bindingResult.hasErrors()) {
            model.addAttribute("post", postResponse);
            return "post_detail";
        }
        CommentResponse commentResponse = this.commentService.create(postResponse,
                commentRequest.getContent(), memberResponse);
        return String.format("redirect:/post/detail/%s#comment_%s",
                commentResponse.getPost().getId(), commentResponse.getId());
    }


}
