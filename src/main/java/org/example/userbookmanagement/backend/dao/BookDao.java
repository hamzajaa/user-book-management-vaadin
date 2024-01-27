package org.example.userbookmanagement.backend.dao;

import org.example.userbookmanagement.backend.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDao extends JpaRepository<Book, Long> {

}
