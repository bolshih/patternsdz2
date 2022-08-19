package ru.netology.testmode.test;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {
    }

    private static RegistrationData sendRequest(RegistrationData user) {
        given()
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
        return user;
    }

    public static String getRandomLogin() {
        return faker.name().username();
    }

    public static String getRandomPassword() {
        String pass1 = String.valueOf(faker.number().randomNumber());
        String pass2 = faker.lordOfTheRings().location();
        String password = pass2 + pass1;
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationData getUser(String status) {
//            RegistrationData user = new RegistrationData();
//            user.login = getRandomLogin();
//            user.password = getRandomPassword();
//            user.status = status;
            return new RegistrationData(getRandomLogin(),getRandomPassword(), status);
        }

        public static RegistrationData getRegisteredUser(String status) {
//            sendRequest(getUser(status));
            return sendRequest(getUser(status));
        }
    }

    @Value
    public static class RegistrationData {
        String login;
        String password;
        String status;
    }
}
