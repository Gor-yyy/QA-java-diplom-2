package stellarburgers;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runner.RunWith;
import io.restassured.response.Response;
@RunWith(Parameterized.class)
public class CreateUserWithoutRequiredFieldTest {
UserClient userClient = new UserClient();
    private String email;
    private String password;
    private String name;

    public CreateUserWithoutRequiredFieldTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    @Parameterized.Parameters

    public static Object[][] getData() {
        return new Object[][]{
                {null, "password", "Dany"},
                {"test@yandex.ru", null, "Dany"},
                {"test@yandex.ru", "password", null}

        };
    }

    @Test
    public void createUserWithoutRequiredFieldTest(){
        User user = new User(email, password, name);
        Response response = userClient.createUser(user);
        response.then().statusCode(403).body("success", equalTo(false)).body("message", equalTo("Email, password and name are required fields"));

    }
}
