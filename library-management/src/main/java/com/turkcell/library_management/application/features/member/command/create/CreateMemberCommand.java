package com.turkcell.library_management.application.features.member.command.create;

import org.hibernate.validator.constraints.Length;

import com.turkcell.library_management.core.mediator.cqrs.Command;

import jakarta.validation.constraints.NotBlank;

public record CreateMemberCommand(
    @NotBlank @Length(min = 1, max = 20) String memberNo,
    @NotBlank @Length(min = 1, max = 100) String firstName,
    @NotBlank @Length(min = 1, max = 100) String lastName,
    String email,
    String phone,
    String department
) implements Command<CreatedMemberResponse> {}
