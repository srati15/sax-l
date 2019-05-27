package datatypes;

import enums.UserType;

import java.util.Objects;

public class User {
    private int id;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private UserType userType;

    public User(int id, String userName, String password, String firstName, String lastName, UserType userType) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType;
    }
    public User(String userName, String password, String firstName, String lastName, UserType userType) {
        this(0, userName, password, firstName, lastName, userType);
    }
    public User(String userName, String password, String firstName, String lastName) {
        this(0, userName, password, firstName, lastName, UserType.User);
    }
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }
}
