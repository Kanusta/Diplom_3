package clients;

import io.qameta.allure.Step;

import io.restassured.response.ValidatableResponse;
import user.User;
import user.UserLogin;

import static io.restassured.RestAssured.given;


    public class UserClient extends Client {

        private static final String REGISTER = "auth/register";
        private static final String LOGIN = "auth/login";
        private static final String USER = "auth/user";

        @Step("Создание пользователя")
        public ValidatableResponse createUser(User user) {
            return given()
                    .spec(getSpec())
                    .body(user).log().all()
                    .when()
                    .post(REGISTER)
                    .then().log().all();
        }

        @Step("Авторизация")
        public ValidatableResponse authUser(UserLogin userLogin) {
            return given()
                    .spec(getSpec())
                    .body(userLogin).log().all()
                    .when()
                    .post(LOGIN)
                    .then().log().all();
        }

        @Step("Получение данных пользователя")
        public ValidatableResponse getUserData(String token) {
            return given()
                    .spec(getSpec())
                    .header("authorization", token).log().all()
                    .when()
                    .get(USER)
                    .then().log().all();
        }

        @Step("Редактирование пользователя с авторизацией")
        public ValidatableResponse editUser(User user, String token) {
            return given()
                    .spec(getSpec())
                    .header("authorization", token)
                    .body(user).log().all()
                    .when()
                    .patch(USER)
                    .then().log().all();
        }

        @Step("Редактирование пользователя без авторизации")
        public ValidatableResponse editUserWithoutAuth(User user) {
            return given()
                    .spec(getSpec())
                    .body(user).log().all()
                    .when()
                    .patch(USER)
                    .then().log().all();
        }

        @Step("Удаление пользователя")
        public ValidatableResponse deleteUser(String token) {
            return given()
                    .spec(getSpec())
                    .header("authorization", token).log().all()
                    .when()
                    .delete(USER)
                    .then().log().all();
        }
    }