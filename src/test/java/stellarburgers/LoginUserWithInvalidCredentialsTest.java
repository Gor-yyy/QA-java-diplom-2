package stellarburgers;

import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class LoginUserWithInvalidCredentialsTest {

    private final String email;
    private final String password;

    private final UserClient userClient = new UserClient();

    public LoginUserWithInvalidCredentialsTest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][]{
                {"invalid-email@yandex.ru", "password"},
                {"test@yandex.ru", "invalid-password"}
        };
    }

    @Test
    public void loginUserWithInvalidCredentialsTest() {
        UserCredentials userCredentials =
                new UserCredentials(email, password);

        Response response = userClient.loginUser(userCredentials);

        response.then()
                .statusCode(401)
                .body("success", equalTo(false))
                .body("message", equalTo("email or password are incorrect"));
    }
}