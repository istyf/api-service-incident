package se.sundsvall.incident.integration.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import se.sundsvall.incident.integration.db.entity.CategoryEntity;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@CircuitBreaker(name = "categoryRepository")
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
