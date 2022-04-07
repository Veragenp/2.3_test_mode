package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.Registration.*;

public class AuthTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendRequestWithValidValues() {
        UserInfo validUserInfo = DataGenerator.Registration.generateValidUser();
        $("[data-test-id=login] input").setValue(validUserInfo.getLogin());
        $("[data-test-id=password] input").setValue(validUserInfo.getPassword());
        $("button[data-test-id=action-login]").click();
        $(".App_appContainer__3jRx1").shouldBe(visible).shouldHave(text("Личный кабинет"));
    }

    @Test
    void shouldSendRequestWithBlockedUser() {
        UserInfo blockedUserInfo = generateBlockedUser();
        $("[data-test-id=login] input").setValue(blockedUserInfo.getLogin());
        $("[data-test-id=password] input").setValue(blockedUserInfo.getPassword());
        $("button[data-test-id=action-login]").click();
        $(withText("Пользователь заблокирован")).shouldBe(visible);
    }

    @Test
    void shouldSendRequestWithInvalidPassword() {
        UserInfo wrongPasswordUser = generateInvalidPasswordUser("active");
        $("[data-test-id=login] input").setValue(wrongPasswordUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongPasswordUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldSendRequestWithInvalidLogin() {
        UserInfo wrongLoginUser = generateInvalidLoginUser("active");
        $("[data-test-id=login] input").setValue(wrongLoginUser.getLogin());
        $("[data-test-id=password] input").setValue(wrongLoginUser.getPassword());
        $("button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content")
                .shouldHave(text("Неверно указан логин или пароль"));
    }
}
