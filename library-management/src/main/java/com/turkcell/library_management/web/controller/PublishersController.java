package com.turkcell.library_management.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.library_management.application.features.publisher.command.create.CreatePublisherCommand;
import com.turkcell.library_management.application.features.publisher.command.create.CreatedPublisherResponse;
import com.turkcell.library_management.application.features.publisher.query.getall.GetAllPublishersQuery;
import com.turkcell.library_management.application.features.publisher.query.getall.GetAllPublishersResponse;
import com.turkcell.library_management.core.mediator.Mediator;

import jakarta.validation.Valid;

@RequestMapping("/api/publishers")
@RestController
public class PublishersController {
    private final Mediator mediator;

    public PublishersController(Mediator mediator) {
        this.mediator = mediator;
    }

    @PostMapping
    public CreatedPublisherResponse create(@RequestBody @Valid CreatePublisherCommand command) {
        return mediator.send(command);
    }

    @GetMapping
    public List<GetAllPublishersResponse> getAll() {
        return mediator.send(new GetAllPublishersQuery());
    }
}
