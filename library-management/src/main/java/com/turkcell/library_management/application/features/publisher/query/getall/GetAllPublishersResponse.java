package com.turkcell.library_management.application.features.publisher.query.getall;

import java.util.UUID;

public record GetAllPublishersResponse(UUID id, String name, String address, String phone, String email) {}
