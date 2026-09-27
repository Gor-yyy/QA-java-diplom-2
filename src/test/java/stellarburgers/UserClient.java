package stellarburgers;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.RestAssured;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class UserClient {

    static {
        RestAssured.baseURI = "https://stellarburgers.education-services.ru";
    }

    public String randomUserEmail() {
        String mail = UUID.randomUUID() + "@yandex.ru";
        return mail;
    }

    @Step("Создание пользователя")
    public Response createUser(User user) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("api/auth/register");
        return response;
    }

    @Step("Авторизация пользователя")
    public Response loginUser(UserCredentials userCredentials) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(userCredentials)
                .when()
                .post("api/auth/login");
        return response;
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String token) {
        Response response = given()
                .header("Authorization", token)
                .delete("api/auth/user");
        return response;
    }

    @Step("Получение ингредиентов")
    public Response getIngredients() {
        Response response = when()
                .get("api/ingredients");
        return response;
    }

    @Step("Создание заказа с авторизацией")
    public Response createOrder(String token, Order order) {
        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(order)
                .post("api/orders");
        return response;
    }

    @Step("Создание заказа без авторизации")
    public Response createOrderWithoutAuthorization(Order order) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(order)
                .post("https://stellarburgers.education-services.ru/api/orders");
        return response;
    }
}