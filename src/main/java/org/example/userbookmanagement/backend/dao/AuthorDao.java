package org.example.userbookmanagement.backend.dao;

import org.example.userbookmanagement.backend.bean.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorDao extends JpaRepository<Author, Long> {
}
