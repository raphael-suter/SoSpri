package ch.bbw.pr.sospri.member;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Member Form Data
 *
 * @author Raphael Suter
 * @version 21.05.2021
 */
public class MemberFormData {
    @NotEmpty(message = "Bitte gib deinen Vornamen ein.")
    @Size(min = 2, max = 25, message = "Der Vorname muss 2 bis 25 Zeichen enthalten.")
    private String prename;

    @NotEmpty(message = "Bitte gib deinen Nachnamen ein.")
    @Size(min = 2, max = 25, message = "Der Nachname muss 2 bis 25 Zeichen enthalten.")
    private String lastname;

    @NotEmpty(message = "Bitte gib ein Passwort ein.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$", message = "Dein Passwort muss mindestens 8 Zeichen enthalten: Kleinbuchstaben, Grossbuchstaben, Zahlen und Sonderzeichen.")
    private String password;

    @NotEmpty(message = "Bitte wiederhole das eingegebene Passwort.")
    private String confirmation;

    private String message;

    public String getPrename() {
        return prename;
    }

    public void setPrename(String prename) {
        this.prename = prename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmation() {
        return confirmation;
    }

    public void setConfirmation(String confirmation) {
        this.confirmation = confirmation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "MemberFormData{" +
                "prename='" + prename + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", confirmation='" + confirmation + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
