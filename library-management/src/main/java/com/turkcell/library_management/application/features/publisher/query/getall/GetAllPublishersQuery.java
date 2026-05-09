package com.turkcell.library_management.application.features.publisher.query.getall;

import java.util.List;

import com.turkcell.library_management.core.mediator.cqrs.Query;

public record GetAllPublishersQuery() implements Query<List<GetAllPublishersResponse>> {}
