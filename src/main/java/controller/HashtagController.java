package controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class HashtagController {

    @ApiOperation(value = "해시태그로 게시물 검색", notes = "해시태그가 달려있는 게시물을 검색합니다. ")

}
