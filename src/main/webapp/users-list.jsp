<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.FormField" %>
<%@ page import="datatypes.SelectField" %>
<%@ page import="datatypes.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="enums.FormFields" %>
<%@ page import="enums.InputType" %>
<%@ page import="enums.UserType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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

    <link href="css/datatables.min.css" rel="stylesheet">

    <link href="css/toastr.css" rel="stylesheet">

</head>
<body>

<%
    DaoManager daoManager = (DaoManager) request.getServletContext().getAttribute("manager");
    UserDao userDao = daoManager.getDao(DaoType.User);
    User user = (User) request.getSession().getAttribute("user");
%>
<!-- ***** Preloader Start ***** -->
<div id="preloader">
    <div class="mosh-preloader"></div>
</div>
<!-- ***** Header Area Start ***** -->
<header class="header_area clearfix">
    <jsp:include page="components/header.jsp"/>
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
<%
    List<FormField> formFields = new ArrayList<>();
    formFields.add(new FormField("Username", FormFields.username.getValue(),  InputType.text, true, 4));
    formFields.add(new FormField("Password", FormFields.password.getValue(),  InputType.password, true, 4));
    formFields.add(new FormField("Confirm Password", FormFields.confirmpassword.getValue(),  InputType.password, true, 4));
    formFields.add(new FormField("Mail",FormFields.mail.getValue(),  InputType.email, true, 4));
    formFields.add(new FormField("First Name", FormFields.firstname.getValue(), InputType.text, true, 0));
    formFields.add(new FormField("Last Name", FormFields.lastname.getValue(), InputType.text, true, 0));
    SelectField field = new SelectField("User Type","usertype", Arrays.asList("admin", "user"));
%>

<section class="mosh-aboutUs-area">
    <div class="container">
        <h3 class="mb-30">Users List</h3>
        <h:create entityName="User" selectFields="<%=field%>" actionServlet="CreateUserServlet" formFields="<%=formFields%>" formId="createUserForm"/>

        <table id="myTable" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>#</th>
                <th>Username</th>
                <th>Completed Quizes</th>
                <th>Created Quizes</th>
                <th>Achievements</th>
                <%if (user != null && user.getUserType() == UserType.Admin) {%>
                <th>Action</th>
                <%}%>
            </tr>
            </thead>
            <tbody>
            <%
                List<User> users = userDao.findAll();
                for (int i = 0; i < users.size(); i++) {
                    User currentUser = users.get(i);
            %>
            <tr>
                <td><%=i + 1%>
                </td>
                <td><a href="#"><%=currentUser.getUserName()%>
                </a></td>
                <td>0</td>
                <td>0</td>
                <td>0</td>
                <%if (user != null && user.getUserType() == UserType.Admin) {%>
                <td>
                    <jsp:include page="components/delete-modal.jsp">
                        <jsp:param name="entityName" value="user"/>
                        <jsp:param name="deleteParameterName" value="deleteUserId"/>
                        <jsp:param name="deleteParameterId" value="<%=currentUser.getId()%>"/>
                        <jsp:param name="actionServlet" value="DeleteUserServlet"/>
                    </jsp:include>
                    <!-- ***** update user modal ***** -->
                    <button type="button" class="btn btn-info btn-sm" data-toggle="modal"
                            data-target="#exampleModalCenter-<%=i%>">
                        <i class="fa fa-edit"></i> Update
                    </button>
                    <div class="modal fade" id="exampleModalCenter-<%=i%>" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLongTitle2">Update</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form action="UpdateUserServlet" method="post" id="editForm">

                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label for="Username">Username</label>
                                            <input type="text" disabled class="form-control" name="username"
                                                   id="Username" placeholder="<%=currentUser.getUserName()%>" >
                                        </div>
                                        <div class="form-group">
                                            <label for="Password">Password</label>
                                            <input type="password" class="form-control" name="password" id="Password"
                                                   required minlength="4" >
                                        </div>
                                        <div class="form-group">
                                            <label for="ConfPassword">Confirm Password</label>
                                            <input type="password" class="form-control" name="confirmpassword"
                                                   id="ConfPassword" required minlength="4">
                                        </div>
                                        <div class="form-group">
                                            <label for="Email">Email address</label>
                                            <input type="email" disabled class="form-control" id="Email"
                                                   name="email" placeholder="<%=currentUser.getMail()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="FirstName">First Name</label>
                                            <input type="text" class="form-control" id="FirstName" name="firstname"
                                                   placeholder="<%=currentUser.getFirstName()%>" required>
                                        </div>
                                        <div class="form-group">
                                            <label for="LastName">Last Name</label>
                                            <input type="text" class="form-control" id="LastName" name="lastname"
                                                   placeholder="<%=currentUser.getLastName()%>" required>
                                        </div>
                                        <div class="form-group">
                                            <label>User Type</label>
                                            <select class="form-control" name="usertype" required>
                                                <option value="admin">Admin</option>
                                                <option value="user">User</option>
                                            </select>
                                        </div>
                                        <input type="hidden" hidden name="hiddenId" value="<%=currentUser.getId()%>">
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel
                                        </button>
                                        <input type="submit" class="btn btn-primary" value="Update"/>
                                    </div>
                                </form>
                                <script>
                                    ${"#editForm"}.validate();
                                </script>
                            </div>
                        </div>
                    </div>
                    <!-- ***** update user modal end***** -->

                </td>
                <%}%>
            </tr>
            <%
                }
            %>
            </tbody>
            <tfoot>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>Completed Quizes</th>
                    <th>Created Quizes</th>
                    <th>Achievements</th>
                    <%if (user != null && user.getUserType() == UserType.Admin) {%>
                    <th>Action</th>
                    <%}%>
                </tr>
            </tfoot>
        </table>
    </div>
</section>
<!-- ***** Users list Area End ***** -->
<footer class="footer-area clearfix">
    <jsp:include page="components/footer.jsp"/>
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

<!---table scroll -->
<script type="text/javascript" src="js/datatables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#myTable').DataTable();
        $('.dataTables_length').addClass('bs-select');
    });
</script>

<!-- Sign up validation -->
<script src="js/jquery.validate.js"></script>

<script src="js/toastr.js"></script>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {%>
<script>
    toastr.options.closeButton = true;
    toastr.options.timeOut = 0;
    toastr.options.extendedTimeOut = 0;
    toastr.error("<%=error%>");
</script>
<%request.removeAttribute("error");
}%>
</body>
</html>
