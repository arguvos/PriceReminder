package com.example.repository;

import com.example.model.Execution;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface ExecutionRepository extends CrudRepository<Execution, Long> {
}
