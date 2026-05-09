package com.turkcell.library_management.application.features.borrowing.command.borrow;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.borrowing.mapper.BorrowingMapper;
import com.turkcell.library_management.application.features.borrowing.rule.BorrowingBusinessRules;
import com.turkcell.library_management.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_management.domain.Borrowing;
import com.turkcell.library_management.persistence.repository.BorrowingRepository;

@Component
public class BorrowBookCommandHandler implements CommandHandler<BorrowBookCommand, BorrowedBookResponse> {
    private final BorrowingRepository borrowingRepository;
    private final BorrowingBusinessRules borrowingBusinessRules;
    private final BorrowingMapper borrowingMapper;

    public BorrowBookCommandHandler(BorrowingRepository borrowingRepository,
            BorrowingBusinessRules borrowingBusinessRules, BorrowingMapper borrowingMapper) {
        this.borrowingRepository = borrowingRepository;
        this.borrowingBusinessRules = borrowingBusinessRules;
        this.borrowingMapper = borrowingMapper;
    }

    @Override
    public BorrowedBookResponse handle(BorrowBookCommand command) {
        borrowingBusinessRules.bookMustBeAvailable(command.bookId());
        borrowingBusinessRules.memberMustBeActive(command.memberId());

        Borrowing borrowing = borrowingMapper.borrowingFromBorrowCommand(command);

        borrowing.getBook().setAvailableCopies(borrowing.getBook().getAvailableCopies() - 1);

        borrowingRepository.save(borrowing);

        return borrowingMapper.borrowedBookResponseFromBorrowing(borrowing);
    }
}
