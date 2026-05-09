package com.turkcell.library_management.application.features.borrowing.query.getall;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.turkcell.library_management.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_management.domain.Borrowing;
import com.turkcell.library_management.persistence.repository.BorrowingRepository;

@Component
public class GetAllBorrowingsQueryHandler implements QueryHandler<GetAllBorrowingsQuery, Page<GetAllBorrowingsResponse>> {
    private final BorrowingRepository borrowingRepository;

    public GetAllBorrowingsQueryHandler(BorrowingRepository borrowingRepository) {
        this.borrowingRepository = borrowingRepository;
    }

    @Override
    public Page<GetAllBorrowingsResponse> handle(GetAllBorrowingsQuery query) {
        Pageable pageable = PageRequest.of(query.pageNumber(), query.pageSize());
        Page<Borrowing> borrowings = borrowingRepository.findAll(pageable);
        return borrowings.map(borrowing -> new GetAllBorrowingsResponse(
            borrowing.getId(),
            borrowing.getBook().getName(),
            borrowing.getMember().getFirstName() + " " + borrowing.getMember().getLastName(),
            borrowing.getStaff().getFirstName() + " " + borrowing.getStaff().getLastName(),
            borrowing.getBorrowDate(),
            borrowing.getDueDate(),
            borrowing.getReturnDate(),
            borrowing.getStatus().name()
        ));
    }
}
