package controller;

import dto.CommentRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import response.Response;
import service.CommentService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 조회", notes = "게시글의 댓글을 조회합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/from_product")
    public Response getComments(@RequestParam Long postId){
        return Response.success(commentService.getComments(postId));
    }

    @ApiOperation(value = "댓글 작성", notes = "댓글을 작성합니다. ")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts/write/on_product")
    public Response writeComment(@RequestBody @Valid CommentRequestDto commentRequestDto, @RequestParam Long postId){
        return Response.success(commentService.writeComment(postId, commentRequestDto));
    }

    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/post/update")
    public Response updateComment(@RequestParam Long id, @RequestBody @Valid CommentRequestDto commentRequestDto){
        return Response.success(commentService.updateComment(id, commentRequestDto));
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제합니다. ")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/posts/delete")
    public Response deleteComment(@RequestParam Long id){
        return Response.success(commentService.deleteComment(id));
    }
}
