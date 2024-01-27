package org.example.userbookmanagement.backend.dao;

import org.example.userbookmanagement.backend.bean.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
