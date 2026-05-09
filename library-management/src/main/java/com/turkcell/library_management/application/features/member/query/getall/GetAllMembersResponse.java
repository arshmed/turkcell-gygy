package com.turkcell.library_management.application.features.member.query.getall;

import java.time.LocalDate;
import java.util.UUID;

public record GetAllMembersResponse(
    UUID id, String memberNo, String firstName, String lastName,
    String email, String phone, String department,
    LocalDate registrationDate, String status
) {}
