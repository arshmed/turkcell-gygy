package com.turkcell.library_management.application.features.user.rule;

import org.springframework.stereotype.Component;

@Component
public class UserBusinessRules {
    public void userWithSameEmailMustNotExist(String email) {
    }
}
