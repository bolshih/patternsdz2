package ru.netology.testmode.test;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class BankLoginTest {

    @BeforeEach
    public void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
    }

    @Test
    void souldTestLoginActivUser() {
        var user = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $x("//*[contains(text(), 'Личный кабинет')]").shouldBe(Condition.visible);

    }

    @Test
    void souldTestLoginBlockedUser() {
        var user = DataGenerator.Registration.getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Пользователь заблокирован")).shouldBe(Condition.visible);

    }

    @Test
    void souldTestLoginInvalidLogin() {
        var user = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);

    }

    @Test
    void souldTestLoginInvalidPassword() {
        var user = DataGenerator.Registration.getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);

    }

    @Test
    void shouldTestLoginUserNotRegistred() {
        $("[data-test-id='login'] input").setValue(DataGenerator.getRandomLogin());
        $("[data-test-id='password'] input").setValue(DataGenerator.getRandomPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content").shouldHave(Condition.text("Неверно указан логин или пароль")).shouldBe(Condition.visible);

    }
}


