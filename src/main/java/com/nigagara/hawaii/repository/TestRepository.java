package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TestRepository {

    private final EntityManager em;

    public List<TestEntity> findAll() {
        return em.createQuery("select te from TestEntity te", TestEntity.class).
                getResultList();
    }
}
