package controller;

import dto.HashtagRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import response.Response;
import service.HashtagService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class HashtagController {

    private final HashtagService hashtagService;

    @ApiOperation(value = "해시태그로 게시물 검색", notes = "해시태그가 달려있는 게시물을 검색합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/posts")
    public Response searchPostByHashtag(@RequestParam @Valid String hashtag){
        return Response.success(hashtagService.searchPostByHashtag(hashtag));
    }

    @ApiOperation(value = "해시태그로 게시물 검색", notes = "해시태그가 달려있는 게시물을 검색합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search/comments")
    public Response searchCommentByHashtag(@RequestParam @Valid String hashtag){
        return Response.success(hashtagService.searchCommentbyHashtag(hashtag));
    }

    @ApiOperation(value = "해시태그 조회", notes = "게시물의 해시태그 목록을 가져옵니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hashtag/of")
    public Response getHashtagsfromPost(@RequestParam @Valid Long postId){
        return Response.success(hashtagService.getHashtagsFromPost(postId));
    }

    @ApiOperation(value = "해시태그 조회", notes = "댓글의 해시태그 목록을 가져옵니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/hashtag/of")
    public Response getHashtagsfromComment(@RequestParam @Valid Long commentId){
        return Response.success(hashtagService.getHashtagsFromComment(commentId));
    }

    @ApiOperation(value = "해시태그 등록", notes = "게시물에 해시태그를 등록합니다. ")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hashtag/create")
    public Response addHashtagOfPost(@RequestBody @Valid HashtagRequestDto hashtagRequestDto, @RequestParam @Valid Long postId){
        return Response.success(hashtagService.addHashtagOfPost(hashtagRequestDto, postId));
    }

    @ApiOperation(value = "해시태그 등록", notes = "댓글에 해시태그를 등록합니다. ")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hashtag/create")
    public Response addHashtagOfComment(@RequestBody @Valid HashtagRequestDto hashtagRequestDto, @RequestParam @Valid Long commentId){
        return Response.success(hashtagService.addHashtagOfComment(hashtagRequestDto, commentId));
    }

    @ApiOperation(value = "해시태그 삭제", notes = "해당 해시태그를 삭제합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/hashtag/delete")
    public Response deleteHashtag(@RequestParam @Valid Long id){
        return Response.success(hashtagService.deleteHashtag(id));
    }
}
