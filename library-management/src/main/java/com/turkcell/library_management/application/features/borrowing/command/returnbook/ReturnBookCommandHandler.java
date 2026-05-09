package com.turkcell.library_management.application.features.borrowing.command.returnbook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.borrowing.rule.BorrowingBusinessRules;
import com.turkcell.library_management.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_management.domain.Borrowing;
import com.turkcell.library_management.domain.BorrowingStatus;
import com.turkcell.library_management.domain.Penalty;
import com.turkcell.library_management.domain.PenaltyType;
import com.turkcell.library_management.persistence.repository.BorrowingRepository;
import com.turkcell.library_management.persistence.repository.PenaltyRepository;

@Component
public class ReturnBookCommandHandler implements CommandHandler<ReturnBookCommand, ReturnedBookResponse> {
    private final BorrowingRepository borrowingRepository;
    private final PenaltyRepository penaltyRepository;
    private final BorrowingBusinessRules borrowingBusinessRules;

    public ReturnBookCommandHandler(BorrowingRepository borrowingRepository,
            PenaltyRepository penaltyRepository, BorrowingBusinessRules borrowingBusinessRules) {
        this.borrowingRepository = borrowingRepository;
        this.penaltyRepository = penaltyRepository;
        this.borrowingBusinessRules = borrowingBusinessRules;
    }

    @Override
    public ReturnedBookResponse handle(ReturnBookCommand command) {
        Borrowing borrowing = borrowingBusinessRules.borrowingMustExist(command.borrowingId());

        borrowing.setReturnDate(LocalDate.now());
        borrowing.setStatus(BorrowingStatus.IADE_EDILDI);
        borrowing.getBook().setAvailableCopies(borrowing.getBook().getAvailableCopies() + 1);

        if (borrowing.getReturnDate().isAfter(borrowing.getDueDate())) {
            borrowing.setStatus(BorrowingStatus.GECIKTI);

            long daysLate = ChronoUnit.DAYS.between(borrowing.getDueDate(), borrowing.getReturnDate());
            Penalty penalty = new Penalty();
            penalty.setBorrowing(borrowing);
            penalty.setPenaltyType(PenaltyType.GECIKME);
            penalty.setAmount(BigDecimal.valueOf(daysLate * 5));
            penaltyRepository.save(penalty);
        }

        borrowingRepository.save(borrowing);

        return new ReturnedBookResponse(
            borrowing.getId(), borrowing.getBook().getId(), borrowing.getMember().getId(),
            borrowing.getReturnDate(), borrowing.getStatus().name()
        );
    }
}
