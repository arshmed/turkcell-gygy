package com.turkcell.library_management.application.features.author.query.getall;

import java.util.List;

import com.turkcell.library_management.core.mediator.cqrs.Query;

public record GetAllAuthorsQuery() implements Query<List<GetAllAuthorsResponse>> {}
