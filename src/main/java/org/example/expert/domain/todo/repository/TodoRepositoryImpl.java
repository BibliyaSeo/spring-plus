package org.example.expert.domain.todo.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.comment.entity.QComment.comment;
import static org.example.expert.domain.manager.entity.QManager.manager;
import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo result = queryFactory
                .selectFrom(todo)
                .leftJoin(todo.managers, manager).fetchJoin()
                .leftJoin(manager.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Page<SearchTodoResponse> searchTodos(
            String title,
            LocalDate startDate,
            LocalDate endDate,
            String nickname,
            Pageable pageable) {

        LocalDateTime start = null;
        if (startDate != null) {
            start = startDate.atStartOfDay();
        }

        LocalDateTime end = null;
        if (endDate != null) {
            end = endDate.plusDays(1).atStartOfDay();
        }

        // 동적 쿼리
        BooleanBuilder builder = new BooleanBuilder();

        if (title != null && !title.isEmpty()) {
            builder.and(todo.title.containsIgnoreCase(title));
        }

        if (startDate != null) {
            builder.and(todo.createdAt.goe(start));
        }
        if (endDate != null) {
            builder.and(todo.createdAt.lt(end));
        }

        if (nickname != null && !nickname.isEmpty()) {
            builder.and(user.nickname.containsIgnoreCase(nickname));
        }

        List<SearchTodoResponse> content = queryFactory
                .select(Projections.constructor(SearchTodoResponse.class,
                        todo.title,
                        manager.countDistinct(),
                        comment.countDistinct()
                ))
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .leftJoin(todo.comments, comment)
                .where(builder)
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = queryFactory
                .select(todo.countDistinct())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(builder)
                .fetchOne();

        long finalTotalCount = totalCount != null ? totalCount : 0L;

        return new PageImpl<>(content, pageable, finalTotalCount);
    }
}
