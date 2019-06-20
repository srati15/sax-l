package datatypes;

import anotations.Column;
import anotations.Entity;
import enums.UserType;

@Entity(table = "users")
public class User extends Domain<Integer>{
    @Column("user_name")
    private String userName;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    @Column("pass")
    private String password;
    @Column("user_type")
    private UserType userType;
    @Column("mail")
    private String mail;
    public User(String userName, String password, String firstName, String lastName, String mail) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = UserType.User;
        this.mail = mail;
    }

    public User(int id, String userName, String password, String firstName, String lastName, String mail) {
        this(userName, password, firstName, lastName, mail);
        this.id = id;
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

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getMail() {
        return mail;
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
                ", mail='" + mail + '\'' +
                '}';
    }
}
