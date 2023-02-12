package com.insta.jangstagram.repository;

import com.insta.jangstagram.dto.PostResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostCustomRepository {

    Page<PostResponseDto> getPage(Pageable pageable);
}
