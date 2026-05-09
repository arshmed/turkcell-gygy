package com.turkcell.library_management.application.features.publisher.query.getall;

import java.util.List;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_management.persistence.repository.PublisherRepository;

@Component
public class GetAllPublishersQueryHandler implements QueryHandler<GetAllPublishersQuery, List<GetAllPublishersResponse>> {
    private final PublisherRepository publisherRepository;

    public GetAllPublishersQueryHandler(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public List<GetAllPublishersResponse> handle(GetAllPublishersQuery query) {
        return publisherRepository.findAll().stream()
                .map(p -> new GetAllPublishersResponse(p.getId(), p.getName(), p.getAddress(), p.getPhone(), p.getEmail()))
                .toList();
    }
}
