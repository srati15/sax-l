package datatypes.user;

import datatypes.messages.QuizChallenge;
import datatypes.messages.TextMessage;
import datatypes.quiz.Quiz;
import datatypes.quiz.QuizResult;
import enums.UserType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class User extends Person {
    private String password;
    private UserType userType;
    private final String mail;
    private List<Person> friends = new ArrayList<>();
    private List<Quiz> quizzes = new ArrayList<>();
    private List<Person> pendingFriendRequests = new ArrayList<>();
    private List<QuizResult> quizResults = new ArrayList<>();
    private Map<String, List<TextMessage>> textMessages = new HashMap<>();
    private List<UserAchievement> achievements = new ArrayList<>();
    private List<QuizChallenge> quizChallenges = new ArrayList<>();
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
    
    public void setQuizChallenges(List<QuizChallenge> quizChallenges) {
        this.quizChallenges = quizChallenges;
    }

    public List<QuizChallenge> getQuizChallenges() {
        return quizChallenges;
    }

    public List<UserAchievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<UserAchievement> achievements) {
        this.achievements = achievements;
    }

    public List<Person> getPendingFriendRequests() {
        return pendingFriendRequests;
    }

    public Map<String, List<TextMessage>> getTextMessages() {
        return textMessages;
    }

    public void setTextMessages(Map<String, List<TextMessage>> textMessages) {
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
        List<String> currentFriends = friends.stream().map(Person::getUserName).collect(Collectors.toList());
        List<String> requests = pendingFriendRequests.stream().map(Person::getUserName).collect(Collectors.toList());
        return "User{" +
                ", userType=" + userType +
                ", mail='" + mail + '\'' +
                ", friends=" + currentFriends +
                ", quizzes=" + quizzes +
                ", pendingFriendRequests=" + requests +
                ", quizResults=" + quizResults +
                ", textMessages=" + textMessages +
                ", achievements=" + achievements +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return password.equals(user.password) &&
                userType == user.userType &&
                mail.equals(user.mail) &&
                quizzes.equals(user.quizzes) &&
                quizResults.equals(user.quizResults) &&
                textMessages.equals(user.textMessages) &&
                achievements.equals(user.achievements);
    }


    public void setPassword(String password) {
        this.password = password;
    }
}
