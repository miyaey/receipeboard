package com.example.receipeboard.controller;

import com.example.receipeboard.data.dto.MemberRequest;
import com.example.receipeboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/signup")
    public String signup(MemberRequest memberRequest) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid MemberRequest memberRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!memberRequest.getPassword1().equals(memberRequest.getPassword2())) {
            bindingResult.rejectValue("password2", "passIncorrect",
                    "2개의 패스워드가 일치하지 않습니다");
            return "signup_form";
        }

        try {
            memberService.create(memberRequest.getNickname(),
                    memberRequest.getPassword1(),memberRequest.getName());
        }catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 닉네임입니다");
            return "signup_form";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }

        return "redirect:/member/login";
    }
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

}

