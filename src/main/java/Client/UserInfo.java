package Client;

import java.io.Serializable;
import java.time.LocalDate;

public class UserInfo implements Serializable {
    private String firstName;
    private String lastname;
    private LocalDate birthDate;

    public UserInfo(String firstName, String lastname, LocalDate birthDate) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.birthDate = birthDate;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "\n\t\tfirstName = '" + firstName + '\'' +
                ",\n\t\tlastname = '" + lastname + '\'' +
                ",\n\t\tbirthDate = " + birthDate +
                "\n\t}";
    }
}
