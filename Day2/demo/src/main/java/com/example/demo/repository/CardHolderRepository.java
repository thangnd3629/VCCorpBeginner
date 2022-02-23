package com.example.demo.repository;

import com.example.demo.model.CardHolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardHolderRepository extends JpaRepository<CardHolder, Long> {
}
