package org.example.userbookmanagement.backend.dao;

import org.example.userbookmanagement.backend.bean.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDao extends JpaRepository<Transaction, Long> {
}
