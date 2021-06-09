package ch.bbw.pr.sospri.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Member Form Data
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
public class PasswordChangeFormData {
    @NotEmpty(message = "Bitte gib ein Passwort ein.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$", message = "Dein Passwort muss mindestens 8 Zeichen enthalten: Kleinbuchstaben, Grossbuchstaben, Zahlen und Sonderzeichen.")
    private String password;

    @NotEmpty(message = "Bitte wiederhole das eingegebene Passwort.")
    private String reppassword;

    private String message;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReppassword() {
        return reppassword;
    }

    public void setReppassword(String reppassword) {
        this.reppassword = reppassword;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
