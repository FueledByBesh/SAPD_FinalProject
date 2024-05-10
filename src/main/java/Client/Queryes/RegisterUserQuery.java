package Client.Queryes;

import java.io.Serializable;
import java.time.LocalDate;

public class RegisterUserQuery implements Serializable {
    private String phone;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String password;

    public RegisterUserQuery(String phone, String firstName, String lastName, LocalDate birthDate, String email, String password) {
        this.phone = phone;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterUserQuery{" +
                "\n\tphone='" + phone + '\'' +
                ", \n\tfirstName='" + firstName + '\'' +
                ", \n\tlastName='" + lastName + '\'' +
                ", \n\tbirthDate=" + birthDate +
                ", \n\temail='" + email + '\'' +
                ", \n\tpassword='" + password + '\'' +
                '}';
    }
}
