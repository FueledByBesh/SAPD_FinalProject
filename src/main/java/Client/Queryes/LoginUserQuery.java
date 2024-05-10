package Client.Queryes;

import java.io.Serializable;
public class LoginUserQuery implements Serializable {
    String phone;
    String password;

    public LoginUserQuery(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginUserQuery{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
