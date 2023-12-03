package com.bbsk.banchanshop.cart.repository;

import com.bbsk.banchanshop.banchan.entity.QBanchanEntity;
import com.bbsk.banchanshop.cart.entity.CartEntity;
import com.bbsk.banchanshop.cart.entity.CartItemEntity;
import com.bbsk.banchanshop.cart.entity.QCartEntity;
import com.bbsk.banchanshop.cart.entity.QCartItemEntity;
import com.bbsk.banchanshop.common.repository.PagingRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.bbsk.banchanshop.banchan.entity.QBanchanEntity.*;
import static com.bbsk.banchanshop.cart.entity.QCartEntity.*;
import static com.bbsk.banchanshop.cart.entity.QCartItemEntity.*;

public class CartRepositoryImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CartRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CartItemEntity> findAllByPaging(String userId, Pageable pageable) {

        List<CartItemEntity> cartItems = queryFactory
                .selectFrom(cartItemEntity)
                .join(cartItemEntity.banchan, banchanEntity)
                .fetchJoin()
                .where(cartItemEntity.cart.cartId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(cartItemEntity.cartItemId.desc())
                .fetch();

        Long totalCount = queryFactory
                .select(cartItemEntity.count())
                .from(cartItemEntity)
                .where(cartItemEntity.cart.cartId.eq(userId))
                .fetchOne();

        return new PageImpl<>(cartItems, pageable, totalCount == null ? 0 : totalCount);
    }
}
