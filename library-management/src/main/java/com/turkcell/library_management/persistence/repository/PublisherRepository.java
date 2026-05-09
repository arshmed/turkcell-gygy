package com.turkcell.library_management.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.library_management.domain.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, UUID> {
}
