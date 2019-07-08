package datatypes.user;

import datatypes.quiz.QuizResult;
import datatypes.messages.TextMessage;
import datatypes.quiz.Quiz;
import enums.UserType;

import java.util.*;

public class User extends Person {
    private String password;
    private UserType userType;
    private final String mail;
    private List<Person> friends;
    private List<Quiz> quizzes = new ArrayList<>();
    private List<Person> pendingFriendRequests = new ArrayList<>();
    private List<QuizResult> quizResults = new ArrayList<>();
    private Map<Person, List<TextMessage>> textMessages = new HashMap<>();
    private List<Achievement> achievements = new ArrayList<>();

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

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
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

    public void setQuizResults(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    public List<QuizResult> getQuizResults() {
        return quizResults;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return password.equals(user.password) &&
                userType == user.userType &&
                mail.equals(user.mail) &&
                friends.equals(user.friends) &&
                quizzes.equals(user.quizzes) &&
                pendingFriendRequests.equals(user.pendingFriendRequests) &&
                quizResults.equals(user.quizResults) &&
                textMessages.equals(user.textMessages) &&
                achievements.equals(user.achievements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, userType, mail, friends, quizzes, pendingFriendRequests, quizResults, textMessages, achievements);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
