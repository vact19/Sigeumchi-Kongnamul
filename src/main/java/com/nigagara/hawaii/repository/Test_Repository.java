package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Test_Repository extends JpaRepository<TestEntity, Long> { // Entity, PK

    Page<TestEntity> findByTestNameContainingOrTestTypeContaining(String testName, String testType, Pageable pageable);
}
