package com.turkcell.library_management.application.features.author.command.create;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.author.mapper.AuthorMapper;
import com.turkcell.library_management.core.mediator.cqrs.CommandHandler;
import com.turkcell.library_management.domain.Author;
import com.turkcell.library_management.persistence.repository.AuthorRepository;

@Component
public class CreateAuthorCommandHandler implements CommandHandler<CreateAuthorCommand, CreatedAuthorResponse> {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public CreateAuthorCommandHandler(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    public CreatedAuthorResponse handle(CreateAuthorCommand command) {
        Author author = authorMapper.authorFromCreateCommand(command);
        authorRepository.save(author);
        return authorMapper.createdAuthorResponseFromAuthor(author);
    }
}
