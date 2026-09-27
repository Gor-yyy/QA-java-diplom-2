package stellarburgers;

import java.util.UUID;

public class UserCredentials {
    private String email;
    private String password;

    public UserCredentials(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public String randomUserEmail() {
        String mail = UUID.randomUUID() + "@yandex.ru";
        return mail;
    }

}
