package com.turkcell.library_management.application.features.publisher.command.create;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_management.domain.Publisher;
import com.turkcell.library_management.persistence.repository.PublisherRepository;

@Component
public class CreatePublisherCommandHandler implements CommandHandler<CreatePublisherCommand, CreatedPublisherResponse> {
    private final PublisherRepository publisherRepository;

    public CreatePublisherCommandHandler(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public CreatedPublisherResponse handle(CreatePublisherCommand command) {
        Publisher publisher = new Publisher();
        publisher.setName(command.name());
        publisher.setAddress(command.address());
        publisher.setPhone(command.phone());
        publisher.setEmail(command.email());

        publisherRepository.save(publisher);

        return new CreatedPublisherResponse(publisher.getId(), publisher.getName());
    }
}
