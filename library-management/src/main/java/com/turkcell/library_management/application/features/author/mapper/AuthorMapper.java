package com.turkcell.library_management.application.features.author.mapper;

import org.springframework.stereotype.Component;

import com.turkcell.library_management.application.features.author.command.create.CreateAuthorCommand;
import com.turkcell.library_management.application.features.author.command.create.CreatedAuthorResponse;
import com.turkcell.library_management.domain.Author;

@Component
public class AuthorMapper {
    public Author authorFromCreateCommand(CreateAuthorCommand command) {
        Author author = new Author();
        author.setFirstName(command.firstName());
        author.setLastName(command.lastName());
        author.setBiography(command.biography());
        return author;
    }

    public CreatedAuthorResponse createdAuthorResponseFromAuthor(Author author) {
        return new CreatedAuthorResponse(author.getId(), author.getFirstName(), author.getLastName());
    }
}
