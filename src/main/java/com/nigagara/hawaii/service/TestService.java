package com.nigagara.hawaii.service;

import com.nigagara.hawaii.entity.TestEntity;
import com.nigagara.hawaii.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;

    public List<TestEntity> sortTests() {
        List<TestEntity> tests = testRepository.findAll();

        List<TestEntity> collect = tests.stream().sorted(Comparator.comparing(
                TestEntity::getCount).reversed())
                .collect(Collectors.toList()); // Count 기준 내림차순 정렬

        return collect;
    }

}



