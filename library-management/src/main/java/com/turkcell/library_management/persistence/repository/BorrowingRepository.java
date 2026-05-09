package com.turkcell.library_management.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.library_management.domain.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, UUID> {
}
