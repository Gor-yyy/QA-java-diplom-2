package stellarburgers;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest {
    private String token;
    UserClient userClient = new UserClient();
    User user;
    @Before
    public void createUser(){
        user = new User(userClient.randomUserEmail(), "password", "Dany");
    }

    @After
    public void tearDown() {
        if (token != null) {
            userClient.deleteUser(token);
        }
    }
    @Test
    public void createOrderWithAuthorizationTest(){
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
        Response response = userClient.createUser(user);
        this.token = response.path("accessToken");
        List<String> ingridient = new ArrayList<>();
        Order order = new Order(ingridient);
        Response response1 = userClient.createOrder(token,order);

        response1.then().statusCode(400).body("success", equalTo(false)).body("message", equalTo("Ingredient ids must be provided"));

    }
    @Test
    public void createOrderWithInvalidIngredientHashTest(){
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
