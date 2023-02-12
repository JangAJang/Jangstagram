package com.insta.jangstagram.repository;
import com.insta.jangstagram.domain.QPost;
import com.insta.jangstagram.dto.PostResponseDto;
import com.insta.jangstagram.dto.QPostResponseDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class PostCustomRepositoryImpl implements PostCustomRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostResponseDto> getPage(Pageable pageable) {
        return new PageImpl<PostResponseDto>(getData(pageable), pageable, getSize());
    }

    private List<PostResponseDto> getData(Pageable pageable){
        return queryFactory
                .select(new QPostResponseDto(QPost.post.id, QPost.post.title, QPost.post.content))
                .from(QPost.post)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }

    private Long getSize(){
        return queryFactory
                .select(QPost.post.count())
                .from(QPost.post)
                .fetchOne();
    }
}
