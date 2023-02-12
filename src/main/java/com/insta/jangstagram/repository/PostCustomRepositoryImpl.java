package com.insta.jangstagram.repository;
import com.insta.jangstagram.domain.QPost;
import com.insta.jangstagram.dto.PostResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponseDto> getPage(Pageable pageable) {
        return null;
    }
}
