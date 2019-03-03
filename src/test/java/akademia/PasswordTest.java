package akademia;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordTest {


    @Test
    public void getPassword() {
        PasswordEncoder pass = new BCryptPasswordEncoder();

        System.out.println(pass.encode("admin"));
    }


}
