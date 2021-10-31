package com.nigagara.hawaii.repository;

import com.nigagara.hawaii.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_Repository extends JpaRepository<User, Long> {

    Page<User> findByUserNameContainingOrEmailContaining(String username, String email, Pageable pageable);
}
