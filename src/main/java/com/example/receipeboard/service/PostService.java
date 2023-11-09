package com.example.receipeboard.service;

import com.example.receipeboard.config.DataNotFoundException;
import com.example.receipeboard.data.dto.MemberResponse;
import com.example.receipeboard.data.dto.PostResponse;
import com.example.receipeboard.data.entity.Member;
import com.example.receipeboard.data.entity.Post;
import com.example.receipeboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final Member member;

    private PostResponse of(Post post) {
        return modelMapper.map(post, PostResponse.class);
    }

    private Post of(PostResponse postResponse) {
        return modelMapper.map(postResponse, Post.class);
    }

    public Page<Post> getList(int page, String kw) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("createdDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(orders));

        if (kw != null && !kw.isEmpty()) {
            return postRepository.findBySubjectContaining(kw, pageable);
        } else {
            return this.postRepository.findAll(pageable);
        }

    }

    public PostResponse getPost(Integer id) {
        Optional<Post> post = this.postRepository.findById(id);
        if (post.isPresent()) {
            Post p1 = post.get();
            p1.setReadCnt(p1.getReadCnt()+1);
            this.postRepository.save(p1);
            return of(post.get());
        } else {
            throw new DataNotFoundException("해당 글이 없습니다");
        }
    }

    public Page<Post> getPage(String kw, int page) {
        List<Sort.Order> orders = new ArrayList<>();
        orders.add(Sort.Order.desc("createdDate"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(orders));

        if (kw != null && !kw.isEmpty()) {
            return postRepository.findBySubjectContaining(kw, pageable);
        } else {
            return this.postRepository.findAll(pageable);
        }

    }

    public PostResponse create(String subject, String content, MemberResponse memberResponse) {
        PostResponse postResponse = new PostResponse();
        postResponse.setSubject(subject);
        postResponse.setContent(content);

        Member author = new Member();
        author.setId(memberResponse.getId());
        postResponse.setAuthor(author);

        Post post = of(postResponse);
        this.postRepository.save(post);
        return postResponse;
    }

    public PostResponse update(PostResponse postResponse, String subject, String content) {
        postResponse.setSubject(subject);
        postResponse.setContent(content);
        postResponse.setModifiedDate(LocalDateTime.now());
        Post post = of(postResponse);
        this.postRepository.save(post);
        return postResponse;
    }

    public void delete(PostResponse postResponse) {
        this.postRepository.deleteById(postResponse.getId());
    }

    public PostResponse vote(PostResponse postResponse, MemberResponse memberResponse) {
        postResponse.getVoter().add(memberResponse);
        this.postRepository.save(of(postResponse));
        return postResponse;
    }
}
