package datatypes;

import datatypes.messages.TextMessage;
import enums.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User extends Person {
    private String password;
    private UserType userType;
    private String mail;
    private List<Person> friends;
    private List<Quiz> quizzes = new ArrayList<>();
    private List<Person> pendingFriendRequests = new ArrayList<>();
    private Map<Person, List<TextMessage>> textMessages = new HashMap<>();
    public User(String userName, String password, String firstName, String lastName, String mail) {
        super(userName, firstName, lastName);
        this.password = password;
        this.userType = UserType.User;
        this.mail = mail;
    }

    public User(int id, String userName, String password, String firstName, String lastName, String mail) {
        this(userName, password, firstName, lastName, mail);
        this.id = id;
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

    public List<Person> getFriends() {
        return friends;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public void setFriends(List<Person> people) {
        this.friends = people;
    }

    public void setPendingFriendRequests(List<Person> pendingFriendRequests) {
        this.pendingFriendRequests = pendingFriendRequests;
    }

    public List<Person> getPendingFriendRequests() {
        return pendingFriendRequests;
    }

    public Map<Person, List<TextMessage>> getTextMessages() {
        return textMessages;
    }

    public void setTextMessages(Map<Person, List<TextMessage>> textMessages) {
        this.textMessages = textMessages;
    }

    @Override
    public String toString() {
        return "User{" +
                "password='" + password + '\'' +
                ", userName='" + getUserName()+'\''+
                ", firstname='" + getFirstName()+'\''+
                ", lastName='" + getLastName()+'\''+
                ", userType=" + userType +
                ", mail='" + mail + '\'' +
                ", friends=" + friends +
                ", friendRequests=" + pendingFriendRequests +
                ", id=" + id +
                "} " + super.toString();
    }
}
