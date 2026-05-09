package com.turkcell.library_management.web.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.library_management.application.features.member.command.create.CreateMemberCommand;
import com.turkcell.library_management.application.features.member.command.create.CreatedMemberResponse;
import com.turkcell.library_management.application.features.member.query.getall.GetAllMembersQuery;
import com.turkcell.library_management.application.features.member.query.getall.GetAllMembersResponse;
import com.turkcell.library_management.core.mediator.Mediator;

import jakarta.validation.Valid;

@RequestMapping("/api/members")
@RestController
public class MembersController {
    private final Mediator mediator;

    public MembersController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public CreatedMemberResponse create(@RequestBody @Valid CreateMemberCommand command) {
        return mediator.send(command);
    }

    @GetMapping
    public Page<GetAllMembersResponse> getAll(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize) {
        return mediator.send(new GetAllMembersQuery(pageNumber, pageSize));
    }
}
