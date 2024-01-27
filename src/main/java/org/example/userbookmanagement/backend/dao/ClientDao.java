package org.example.userbookmanagement.backend.dao;

import org.example.userbookmanagement.backend.bean.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDao extends JpaRepository<Client,Long> {
}
