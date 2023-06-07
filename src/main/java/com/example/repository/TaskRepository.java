package com.example.repository;

import com.example.model.Task;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    Collection<Task> findAllByUserId(@NotNull Long userId);
}
