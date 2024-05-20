package ru.expanse.repository.utils;

import ru.expanse.entity.User;

public class ObjectFactory {
    public static User getDefaultUser() {
        return User.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .phoneNumber("+79001234567")
                .build();
    }
}
