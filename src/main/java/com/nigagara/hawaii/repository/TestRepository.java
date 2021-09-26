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

    public List<TestEntity> searchByName(String byName){
        return em.createQuery("select  t from TestEntity t where " +
                "t.testName like CONCAT('%',:name,'%') ", TestEntity.class)
                .setParameter("name", byName)
                .getResultList();
    }
    public List<TestEntity> searchByType(String byType){
        return em.createQuery("select  t from TestEntity t where " +
                "t.testType like CONCAT('%',:type,'%') ", TestEntity.class)
                .setParameter("type", byType)
                .getResultList();
    }

}
