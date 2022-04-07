package ru.netology;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor

public class UserInfo {
    private final String login;
    private final String password;
    private final String status;
}