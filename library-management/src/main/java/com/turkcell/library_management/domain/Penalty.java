package com.turkcell.library_management.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "penalties")
public class Penalty {
    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "borrowing_id", nullable = false)
    private Borrowing borrowing;

    @Enumerated(EnumType.STRING)
    @Column(name = "penalty_type", nullable = false, length = 20)
    private PenaltyType penaltyType;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid = false;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate = LocalDate.now();

    @Column(name = "paid_date")
    private LocalDate paidDate;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Borrowing getBorrowing() { return borrowing; }
    public void setBorrowing(Borrowing borrowing) { this.borrowing = borrowing; }
    public PenaltyType getPenaltyType() { return penaltyType; }
    public void setPenaltyType(PenaltyType penaltyType) { this.penaltyType = penaltyType; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public boolean isPaid() { return isPaid; }
    public void setPaid(boolean isPaid) { this.isPaid = isPaid; }
    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }
}
