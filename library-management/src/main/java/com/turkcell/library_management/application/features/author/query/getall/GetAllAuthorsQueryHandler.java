package com.turkcell.library_management.application.features.author.query.getall;

import java.util.List;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.core.mediator.cqrs.QueryHandler;
import com.turkcell.library_management.persistence.repository.AuthorRepository;

@Component
public class GetAllAuthorsQueryHandler implements QueryHandler<GetAllAuthorsQuery, List<GetAllAuthorsResponse>> {
    private final AuthorRepository authorRepository;

    public GetAllAuthorsQueryHandler(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<GetAllAuthorsResponse> handle(GetAllAuthorsQuery query) {
        return authorRepository.findAll().stream()
                .map(author -> new GetAllAuthorsResponse(author.getId(), author.getFirstName(), author.getLastName(), author.getBiography()))
                .toList();
    }
}
