package com.bbsk.banchanshop.banchan.repository;

import com.bbsk.banchanshop.banchan.entity.BanchanEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.bbsk.banchanshop.banchan.entity.QBanchanEntity.*;

public class BanchanRepositoryImpl implements BanchanRepositoryCustom{

    private static final int LIMIT = 5;

    private final JPAQueryFactory queryFactory;

    public BanchanRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 반찬리스트 페이징 처리
     * @param page
     * @return
     */
    @Override
    public Page<BanchanEntity> findAllByPaging(Pageable page) {

        List<BanchanEntity> contents = queryFactory
                .selectFrom(banchanEntity)
                .offset(page.getOffset())
                .limit(page.getPageSize())
                .orderBy(banchanEntity.createDate.desc())
                .fetch();


        Long totalCount = queryFactory
                .select(banchanEntity.count())
                .from(banchanEntity)
                .fetchOne();

        return new PageImpl<>(contents, page, totalCount == null ? 0 : totalCount);
    }
}
