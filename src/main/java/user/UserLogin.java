package user;public class UserLogin {


private String email;
private String password;

public UserLogin(String email, String password) {
    this.email = email;
    this.password = password;
}

public static UserLogin withCorrectData(User user) {
    return new UserLogin(user.getEmail(), user.getPassword());
}

public static UserLogin withErrorEmail(User user) {
    return new UserLogin(user.getEmail() + "errorData", user.getPassword());
}

public static UserLogin withErrorPassword(User user) {
    return new UserLogin(user.getEmail(), user.getPassword() + "errorData");
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

public void setPassword() {
    this.password = password;
}

}