package com.turkcell.library_management.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.library_management.application.features.borrowing.command.borrow.BorrowBookCommand;
import com.turkcell.library_management.application.features.borrowing.command.borrow.BorrowedBookResponse;
import com.turkcell.library_management.application.features.borrowing.command.returnbook.ReturnBookCommand;
import com.turkcell.library_management.application.features.borrowing.command.returnbook.ReturnedBookResponse;
import com.turkcell.library_management.application.features.borrowing.query.getall.GetAllBorrowingsQuery;
import com.turkcell.library_management.application.features.borrowing.query.getall.GetAllBorrowingsResponse;
import com.turkcell.library_management.core.mediator.Mediator;

import jakarta.validation.Valid;

@RequestMapping("/api/borrowings")
@RestController
public class BorrowingsController {
    private final Mediator mediator;

    public BorrowingsController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping("/borrow")
    public BorrowedBookResponse borrow(@RequestBody @Valid BorrowBookCommand command) {
        return mediator.send(command);
    }

    @PostMapping("/return")
    public ReturnedBookResponse returnBook(@RequestBody @Valid ReturnBookCommand command) {
        return mediator.send(command);
    }

    @GetMapping
    public Page<GetAllBorrowingsResponse> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return mediator.send(new GetAllBorrowingsQuery(pageNumber, pageSize));
    }
}
