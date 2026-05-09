package com.turkcell.library_management.application.features.borrowing.mapper;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.borrowing.command.borrow.BorrowBookCommand;
import com.turkcell.library_management.application.features.borrowing.command.borrow.BorrowedBookResponse;
import com.turkcell.library_management.domain.Book;
import com.turkcell.library_management.domain.Borrowing;
import com.turkcell.library_management.domain.Member;
import com.turkcell.library_management.domain.Staff;
import com.turkcell.library_management.persistence.repository.BookRepository;
import com.turkcell.library_management.persistence.repository.MemberRepository;
import com.turkcell.library_management.persistence.repository.StaffRepository;

@Component
public class BorrowingMapper {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final StaffRepository staffRepository;

    public BorrowingMapper(BookRepository bookRepository, MemberRepository memberRepository,
            StaffRepository staffRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.staffRepository = staffRepository;
    }

    public Borrowing borrowingFromBorrowCommand(BorrowBookCommand command) {
        Borrowing borrowing = new Borrowing();
        Book book = bookRepository.findById(command.bookId())
                .orElseThrow(() -> new RuntimeException("Kitap bulunamadı"));
        Member member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> new RuntimeException("Üye bulunamadı"));
        Staff staff = staffRepository.findById(command.staffId())
                .orElseThrow(() -> new RuntimeException("Görevli bulunamadı"));

        borrowing.setBook(book);
        borrowing.setMember(member);
        borrowing.setStaff(staff);
        borrowing.setDueDate(command.dueDate());

        return borrowing;
    }

    public BorrowedBookResponse borrowedBookResponseFromBorrowing(Borrowing borrowing) {
        return new BorrowedBookResponse(
            borrowing.getId(), borrowing.getBook().getId(), borrowing.getMember().getId(),
            borrowing.getBorrowDate(), borrowing.getDueDate()
        );
    }
}
