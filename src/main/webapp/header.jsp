<div class="container-fluid h-100">
    <div class="row h-100">
        <!-- Menu Area Start -->
        <div class="col-12 h-100">
            <div class="menu_area h-100">
                <nav class="navbar h-100 navbar-expand-lg align-items-center">
                    <!-- Logo -->
                    <a class="navbar-brand" href="/"><img src="img/core-img/logo.png" alt="logo"></a>

                    <!-- Menu Area -->
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#mosh-navbar" aria-controls="mosh-navbar" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>

                    <div class="collapse navbar-collapse justify-content-end" id="mosh-navbar">
                        <ul class="navbar-nav animated" id="nav">
                            <li class="nav-item active"><a class="nav-link" href="/">Home</a></li>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="moshDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Pages</a>
                                <div class="dropdown-menu" aria-labelledby="moshDropdown">
                                    <a class="dropdown-item" href="#">Page</a>
                                    <a class="dropdown-item" href="#">Page</a>
                                    <a class="dropdown-item" href="#">Page</a>
                                    <a class="dropdown-item" href="#">Page</a>
                                    <a class="dropdown-item" href="#">Page</a>
                                    <a class="dropdown-item" href="#">Page</a>
                                    <div class="dropdown-divider"></div>
                                    <a class="dropdown-item" href="#">Page</a>
                                </div>
                            </li>
                            <%if (request.getSession().getAttribute("user")!=null) {%>
                            <li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
                            <%}%>
                            <li class="nav-item"><a class="nav-link" href="#">Page</a></li>
                            <li class="nav-item"><a class="nav-link" href="#">Page</a></li>
                            <li class="nav-item"><a class="nav-link" href="#">Page</a></li>
                            <li class="nav-item"><a class="nav-link" href="#">Page</a></li>
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
                        <div class="login-register-btn">
                            <a href="login">Login </a>
                            <a href="register">/ Register</a>
                        </div>
                    </div>
                </nav>
            </div>
        </div>
    </div>
</div>
