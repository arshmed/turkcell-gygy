package com.turkcell.library_management.application.features.publisher.command.create;

import org.hibernate.validator.constraints.Length;

import com.turkcell.library_management.core.mediator.cqrs.Command;

import jakarta.validation.constraints.NotBlank;

public record CreatePublisherCommand(
    @NotBlank @Length(min = 1, max = 200) String name,
    String address,
    String phone,
    String email
) implements Command<CreatedPublisherResponse> {}
