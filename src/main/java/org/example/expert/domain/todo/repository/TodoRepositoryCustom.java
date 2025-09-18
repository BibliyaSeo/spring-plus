package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.dto.response.SearchTodoResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

public interface TodoRepositoryCustom {
    Optional<Todo> findByIdWithUser(Long todoId);

    Page<SearchTodoResponse> searchTodos(
            String title,
            LocalDate startDate,
            LocalDate endDate,
            String nickname,
            Pageable pageable
    );
}
