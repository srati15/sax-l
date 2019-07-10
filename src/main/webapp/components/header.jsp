<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="enums.UserType" %>
<div class="container-fluid h-100">
    <div class="row h-100">
        <!-- Menu Area Start -->
        <div class="col-12 h-100">
            <div class="menu_area h-100">
                <nav class="navbar h-100 navbar-expand-lg align-items-center">
                    <!-- Logo -->
                    <a class="navbar-brand" href="${pageContext.request.contextPath}/"><img src="../img/core-img/logo.png" alt="logo"></a>

                    <div class="collapse navbar-collapse justify-content-end" id="mosh-navbar">
                        <ul class="navbar-nav animated" id="nav">
                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/" id="home-ref">Home</a></li>
                            <c:if test="${sessionScope.user !=null}">
                                <li class="nav-item"><a class="nav-link" href="profile" id="profile-ref">Profile</a></li>
                                <li class="nav-item"><a class="nav-link" href="users-list" id="users-list-ref">Users</a></li>
                                <li class="nav-item"><a class="nav-link" href="quiz" id="quiz-ref">Quizzes</a></li>
                            </c:if>
                            <c:if test="${sessionScope.user !=null && sessionScope.user.userType == UserType.Admin}">
                                <li class="nav-item"><a class="nav-link" href="admin">Administration</a></li>
                            </c:if>
                        </ul>
                        <c:choose>
                            <c:when test="${sessionScope.user ==null}">
                                <div class="login-register-btn">
                                    <a href="login">
                                        <button type="submit" class="btn btn-info btn-sm">
                                            <i class="fa fa-sign-in"></i> Login
                                        </button>
                                    </a>
                                    <a href="register">
                                        <button type="submit" class="btn btn-info btn-sm">
                                            <i class="fa fa-user-circle-o"></i> Register
                                        </button>
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <form action="LogoutServlet" method="post">
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="fa fa-sign-out"></i> Sign out
                                    </button>
                                </form>
                                <a href="profile">
                                    <button type="submit" class="btn btn-info btn-sm">
                                        <i class="fa fa-user"></i> Profile
                                    </button>
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</div>
