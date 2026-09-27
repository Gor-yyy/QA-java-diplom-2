package stellarburgers;
import java.util.ArrayList;
import java.util.UUID;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import io.restassured.http.ContentType;
import java.util.List;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.util.List;
public class CreateUserTest {
    private User user;
    private String token;
UserClient userClient = new UserClient();

@Before
public void createUser(){
    user = new User(randomUserEmail(), "password", "Dany");
}
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
    public void createUniqueUserTest(){

Response response = userClient.createUser(user);

response.then().statusCode(200).body("success", equalTo(true));

this.token = response.path("accessToken");

    }
@Test

    public void createExistingUserTest(){

        Response creaeteResponse = userClient.createUser(user);
    this.token = creaeteResponse.path("accessToken");
       Response response =  userClient.createUser(user);

       response.then().statusCode(403 ).body( "success", equalTo(false)).body("message", equalTo("User already exists"));

}

}
