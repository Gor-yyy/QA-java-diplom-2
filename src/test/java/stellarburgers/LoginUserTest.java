package stellarburgers;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {

    private String token;
    UserCredentials userCredentials = new UserCredentials("aaaa.yandex.ru", "passwprd");
    UserClient userClient = new UserClient();
    User user;
    String email = userCredentials.randomUserEmail();
    @Before
    public void prepareUser(){
         user = new User(email, "password", "Dany");
        userCredentials = new UserCredentials(email, "password");
    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }


    @Test
    public void loginExistingUserTest(){
        userClient.createUser(user);
        Response response = userClient.loginUser(userCredentials);
        this.token = response.path("accessToken");
        response.then().statusCode(200).body("success", equalTo(true));

    }

}
