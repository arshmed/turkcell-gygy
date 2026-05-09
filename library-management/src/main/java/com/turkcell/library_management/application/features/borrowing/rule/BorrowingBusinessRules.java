package com.turkcell.library_management.application.features.borrowing.rule;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.domain.Book;
import com.turkcell.library_management.domain.Borrowing;
import com.turkcell.library_management.domain.Member;
import com.turkcell.library_management.domain.MemberStatus;
import com.turkcell.library_management.persistence.repository.BookRepository;
import com.turkcell.library_management.persistence.repository.BorrowingRepository;
import com.turkcell.library_management.persistence.repository.MemberRepository;

@Component
public class BorrowingBusinessRules {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BorrowingRepository borrowingRepository;

    public BorrowingBusinessRules(BookRepository bookRepository, MemberRepository memberRepository,
            BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.borrowingRepository = borrowingRepository;
    }

    public void bookMustBeAvailable(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
        if (book.getAvailableCopies() <= 0)
            throw new RuntimeException("Kitabın kopyası mevcut değil");
    }

    public void memberMustBeActive(UUID memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Üye bulunamadı"));
        if (member.getStatus() != MemberStatus.AKTIF)
            throw new RuntimeException("Üye aktif değil");
    }

    public Borrowing borrowingMustExist(UUID borrowingId) {
        return borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Ödünç kaydı bulunamadı"));
    }
}
