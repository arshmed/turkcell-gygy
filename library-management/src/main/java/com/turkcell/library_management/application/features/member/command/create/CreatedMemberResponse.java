package com.turkcell.library_management.application.features.member.command.create;

import java.util.UUID;

public record CreatedMemberResponse(UUID id, String memberNo, String firstName, String lastName) {}
