<%@ page import="datatypes.User" %>
<%@ page import="enums.UserType" %>
<div class="container-fluid h-100">
    <div class="row h-100">
        <%
            User user = (User) request.getSession().getAttribute("user");
        %>
        <!-- Menu Area Start -->
        <div class="col-12 h-100">
            <div class="menu_area h-100">
                <nav class="navbar h-100 navbar-expand-lg align-items-center">
                    <!-- Logo -->
                    <a class="navbar-brand" href="/"><img src="img/core-img/logo.png" alt="logo"></a>

                    <!-- Menu Area -->
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#mosh-navbar"
                            aria-controls="mosh-navbar" aria-expanded="false" aria-label="Toggle navigation"><span
                            class="navbar-toggler-icon"></span></button>

                    <div class="collapse navbar-collapse justify-content-end" id="mosh-navbar">
                        <ul class="navbar-nav animated" id="nav">
                            <li class="nav-item active"><a class="nav-link" href="/">Home</a></li>
                            <%if (user != null) {%>
                            <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
                            <%}%>
                            <li class="nav-item"><a class="nav-link" href="#">Page</a></li>
                            <li class="nav-item"><a class="nav-link" href="#">Page</a></li>
                            <li class="nav-item"><a class="nav-link" href="#">Page</a></li>
                            <%if (user != null && user.getUserType() == UserType.Admin) {%>
                            <li class="nav-item"><a class="nav-link" href="admin">Administration</a></li>
                            <%}%>
                        </ul>
                        <!-- Search Form Area Start -->
                        <div class="search-form-area animated">
                            <form action="#" method="post">
                                <input type="search" name="search" id="search" placeholder="Type keywords &amp; hit enter">
                                <button type="submit" class="d-none"><img src="img/core-img/search-icon.png" alt="Search"></button>
                            </form>
                        </div>
                        <!-- Search btn -->
                        <div class="search-button">
                            <a href="#" id="search-btn"><img src="img/core-img/search-icon.png" alt="Search"></a>
                        </div>
                        <!-- Login/Register btn -->
                        <%if (user == null) {%>
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
                        <%} else {%>
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
                        <%
                            }

                        %>

                    </div>
                </nav>
            </div>
        </div>
    </div>
</div>
