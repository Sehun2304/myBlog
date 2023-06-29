package com.sparta.myblog.service;

import com.sparta.myblog.dto.PostRequestDto;
import com.sparta.myblog.dto.PostResponseDto;
import com.sparta.myblog.entity.Post;
import com.sparta.myblog.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service //빈으로 등록 + Service 클래스임을 드러내기 위해 사용
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Deprecated
    public List<PostResponseDto> getPostListV1() {
        // 방법1. 리스트 반복하며 넣어주기
        List<Post> postList = postRepository.findAllByOrderByCreateAtDesc();
        //findAll 데이터 값을 가져온다, by 어떤 값을 가져올 것인가 Orderby 순서를 지정
        //CreateAt 생성일시를 기준으로 조회 Desc 내림차순
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }
        return postResponseDtoList;
    }

    public List<PostResponseDto> getPostListV2() {
        // 방법2. Stream 형태로 변환해서 리스트로 바로 만들어주기
        return postRepository.findAllByOrderByCreateAtDesc().stream() // DB 에서 조회한 List 를 Stream 으로 변환
            .map(PostResponseDto::new)  // Stream 처리를 통해 Post 를 PostResponseDto 로 변환
            .toList(); // Stream 을 List 로 다시 변환
    }

    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }


    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        // 게시글 생성
        Post post = new Post(postRequestDto);
        // 게시글 저장
        Post savePost = postRepository.save(post);
        return new PostResponseDto(savePost);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = findPost(id);
        // 비밀번호 체크
        post.checkPassword(postRequestDto.getPassword());

        // 필드 업데이트
        post.setTitle(postRequestDto.getTitle());
        post.setName(postRequestDto.getName());
        post.setContent(postRequestDto.getContent());

        return new PostResponseDto(post);
    }

    public void deletePost(Long id, String password) {
        Post post = findPost(id);
        // 비밀번호 체크
        post.checkPassword(password);

        // 삭제 처리
        postRepository.delete(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> //값이 없을시 예외처리 던져준다
            new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
    }

}
