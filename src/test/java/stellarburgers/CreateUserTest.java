package stellarburgers;
import java.util.ArrayList;
import java.util.UUID;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.equalTo;
import org.junit.Test;
import org.junit.After;
import io.restassured.http.ContentType;
import java.util.List;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.util.List;
public class CreateUserTest {

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
    public void createUniqueUserTest(){
User user = new User(randomUserEmail(), "password", "Dany");
Response response = userClient.createUser(user);

response.then().statusCode(200).body("success", equalTo(true));

this.token = response.path("accessToken");

    }
@Test

    public void createExistingUserTest(){
        User user = new User(randomUserEmail(), "password", "Dany");
        Response creaeteResponse = userClient.createUser(user);
    this.token = creaeteResponse.path("accessToken");
       Response response =  userClient.createUser(user);

       response.then().statusCode(403 ).body( "success", equalTo(false)).body("message", equalTo("User already exists"));

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

@Test
    public void createOrderWithAuthorizationTest(){
User user = new User(randomUserEmail(), "password", "Dany");
Response response = userClient.createUser(user);
this.token = response.path("accessToken");
Response response1 = userClient.getIngredients();
List<String> ingredients = response1.path("data._id");
Order order = new Order(ingredients);

userClient.createOrder(token,order).then().statusCode(200). body("success", equalTo(true));
}

@Test

    public void createOrderWithoutAuthorizationTest(){
        Response response1 = userClient.getIngredients();
    List<String> ingredients = response1.path("data._id");
    Order order = new Order(ingredients);
    userClient.createOrderWithoutAuthorization(order).then().statusCode(200). body("success", equalTo(true));
}
@Test
    public void createOrderWithoutIngredientsTest(){
        User user = new User(randomUserEmail(), "password", "Dany");
        Response response = userClient.createUser(user);
    this.token = response.path("accessToken");
    List<String> ingridient = new ArrayList<>();
    Order order = new Order(ingridient);
Response response1 = userClient.createOrder(token,order);

response1.then().statusCode(400).body("success", equalTo(false)).body("message", equalTo("Ingredient ids must be provided"));

}
@Test
public void createOrderWithInvalidIngredientHashTest(){
    User user = new User(randomUserEmail(), "password", "Dany");
    Response response = userClient.createUser(user);
    this.token = response.path("accessToken");
    List<String> ingridient = new ArrayList<>();
    ingridient.add("jbnun62115491knjbyebdnibuemcieu");
    ingridient.add("hubwpdmcnbuecin260396266");
    Order order = new Order(ingridient);
    Response response1 = userClient.createOrder(token,order);
    response1.then().statusCode(500);
}

}
