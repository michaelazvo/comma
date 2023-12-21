package sk.comma;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class Hashovanie {

    public static String hashovanie(String heslo, String sol) {
        return BCrypt.hashpw(heslo, sol);
    }
}
