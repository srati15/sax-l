<%@ page import="datatypes.User" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.UserDao" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="enums.UserType" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title -->
    <title>Sax-L - Quiz Website | Login</title>

    <!-- Favicon -->
    <link rel="icon" href="img/core-img/favicon.ico">

    <!-- Core Stylesheet -->
    <link href="style.css" rel="stylesheet">
    <!-- Responsive CSS -->
    <link href="css/responsive.css" rel="stylesheet">

</head>
<body>

<%
    DaoManager daoManager = (DaoManager) request.getServletContext().getAttribute("manager");
    UserDao userDao = daoManager.getUserDao();
    User user = (User) request.getSession().getAttribute("user");
    %>
<!-- ***** Preloader Start ***** -->
<div id="preloader">
    <div class="mosh-preloader"></div>
</div>
<!-- ***** Header Area Start ***** -->
<header class="header_area clearfix">
    <jsp:include page="header.jsp"/>
</header>
<!-- ***** Header Area End ***** -->
<!-- ***** Breadcumb Area Start ***** -->
<div class="mosh-breadcumb-area" style="background-image: url(img/core-img/breadcumb.png);">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="bradcumbContent">
                    <h2>Users List</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Users</li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ***** Breadcumb Area End ***** -->
<!-- ***** Users list Area Start ***** -->
<section class="mosh-aboutUs-area section_padding_100_0">
        <div class="container">
            <h3 class="mb-30">Users List</h3>
            <div class="row col-md-12 col-md-offset-2 user-list-table">
                <table class="table table-striped user-list-table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Username</th>
                        <th>Completed Quizes</th>
                        <th>Created Quizes</th>
                        <th>Achievements</th>
                        <%if (user != null && user.getUserType()==UserType.Admin){%>
                            <th>Action</th>
                        <%}%>
                    </tr>
                    </thead>
                    <%
                        List<User> users = userDao.findAll();
                        for (int i = 0; i < users.size(); i++) {%>
                    <tr>
                        <td><%=i+1%></td>
                        <td> <a href="#"><%=users.get(i).getUserName()%></a></td>
                        <td>0</td>
                        <td>0</td>
                        <td>0</td>
                        <%if (user != null && user.getUserType()==UserType.Admin ){%>
                        <td>
                            <button type="button" class="btn btn-danger" data-toggle="modal"
                                    data-target="#exampleModalCenter<%=i%>">
                                Delete User
                            </button>
                            <div class="modal fade" id="exampleModalCenter<%=i%>" tabindex="-1" role="dialog"
                                 aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLongTitle">Confirm delete</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Are you sure you want to delete user?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                                        <form action="DeleteUserServlet" method="post">
                                            <input type="submit" class="btn btn-primary" value="Yes"/>
                                            <input type="text" hidden name="deleteUserId" value="<%=users.get(i).getId()%>">
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div></td>
                        <%}%>
                    </tr>
                    <%}
                    %>
                </table>
            </div>
        </div>
</section>
<!-- ***** Users list Area End ***** -->
<footer class="footer-area clearfix">
    <jsp:include page="footer.jsp"/>
</footer>

<!-- jQuery-2.2.4 js -->
<script src="js/jquery-2.2.4.min.js"></script>
<!-- Popper js -->
<script src="js/popper.min.js"></script>
<!-- Bootstrap js -->
<script src="js/bootstrap.min.js"></script>
<!-- All Plugins js -->
<script src="js/plugins.js"></script>
<!-- Active js -->
<script src="js/active.js"></script>
</body>
</html>
