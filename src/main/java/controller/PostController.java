package controller;

import dto.PostRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import response.Response;
import service.PostService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @ApiOperation(value = "게시글 조회", notes = "모든 게시글을 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/")
    public Response viewPosts(){
        return Response.success(postService.getAllPosts());
    }

    @ApiOperation(value = "내 게시글 조회", notes = "내 모든 게시글을 조회합니다.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/my")
    public Response viewMyPosts(){
        return Response.success(postService.getAllMyPosts());
    }

    @ApiOperation(value = "게시글 작성", notes = "내 게시글을 작성합니다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/write")
    public Response writePost(@RequestBody @Valid PostRequestDto postRequestDto){
        return Response.success(postService.writePost(postRequestDto));
    }

    @ApiOperation(value = "해당 게시글 조회", notes = "해당 게시글을 조회합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public Response readPost(@RequestParam Long id){
        return Response.success(postService.readPost(id));
    }

    @ApiOperation(value = "게시글 수정", notes = "해당 게시글을 수정합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/posts/update")
    public Response updatePost(@RequestParam Long id,@RequestBody @Valid PostRequestDto postRequestDto){
        return Response.success(postService.updatePost(id, postRequestDto));
    }

    @ApiOperation(value = "게시글 삭제", notes = "해당 게시글을 삭제합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/posts/delete")
    public Response deletePost(@RequestParam Long id){
        return Response.success(postService.deletePost(id));
    }

}
