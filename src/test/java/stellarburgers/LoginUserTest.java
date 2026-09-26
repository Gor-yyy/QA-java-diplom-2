package stellarburgers;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    private String token;
    UserClient userClient = new UserClient();
    @After
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }

    public String randomUserEmail() {
        String mail = UUID.randomUUID() + "@yandex.ru";
        return mail;
    }

    @Test
    public void loginExistingUserTest(){
        String email = randomUserEmail();
        User user = new User(email, "password", "Dany");
        userClient.createUser(user);
        UserCredentials userCredentials = new UserCredentials(email, "password");
        Response response = userClient.loginUser(userCredentials);
        this.token = response.path("accessToken");
        response.then().statusCode(200).body("success", equalTo(true));

    }

    @Test
    public void loginUserWithInvalidCredentialsTest(){
        UserCredentials userCredentials = new UserCredentials("igorsmirnov-yandex.ru", "password888");
        Response response = userClient.loginUser(userCredentials);
        response.then().statusCode(401).body("success", equalTo(false)).body("message", equalTo("email or password are incorrect"));
    }
}
