<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.formfields.EditFormField" %>
<%@ page import="datatypes.formfields.FormField" %>
<%@ page import="datatypes.formfields.SelectField" %>
<%@ page import="datatypes.user.User" %>
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


    <!-- Title -->
    <title>Sax-L - Quiz Website | Users</title>

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
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
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
    formFields.add(new FormField("Username", FormFields.username.getValue(), InputType.text, true, 4));
    formFields.add(new FormField("Password", FormFields.password.getValue(), InputType.password, true, 4));
    formFields.add(new FormField("Confirm Password", FormFields.confirmpassword.getValue(), InputType.password, true, 4));
    formFields.add(new FormField("Mail", FormFields.mail.getValue(), InputType.email, true, 4));
    formFields.add(new FormField("First Name", FormFields.firstname.getValue(), InputType.text, true, 0));
    formFields.add(new FormField("Last Name", FormFields.lastname.getValue(), InputType.text, true, 0));
    SelectField createSelectField = new SelectField("User Type", "usertype", Arrays.asList("admin", "user"));
%>

<section class="mosh-aboutUs-area">
    <div class="container">
        <div class="ui segment">
            <div class="huge ui black ribbon label">
                Users
            </div>

            <c:if test="${sessionScope.user!=null && sessionScope.user.userType == UserType.Admin}">
                <div style="float:right">
                    <h:create entityName="User" selectFields="<%=createSelectField%>" actionServlet="CreateUserServlet"
                              formFields="<%=formFields%>" formId="createUserForm"/>
                </div>
            </c:if>
            <table id="myTable" class="table table-striped table-bordered table-sm">
                <thead>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>Status</th>
                    <th>Completed Quizes</th>
                    <th>Created Quizes</th>
                    <th>Achievements</th>
                    <c:choose>
                        <c:when test="${sessionScope.user!=null && sessionScope.user.userType==UserType.Admin}">
                            <th>Action</th>
                        </c:when>
                    </c:choose>
                </tr>
                </thead>
                <tbody>
                <c:set var="i" value="0" scope="page"/>
                <c:forEach items="<%=userDao.findAll()%>" var="currentUser">
                    <tr>
                        <td>${i+1}
                        </td>
                        <c:choose>
                            <c:when test="${currentUser.id == sessionScope.user.id}">
                                <td>
                                    <a href="profile">${currentUser.userName}
                                        <c:if test="${applicationScope.onlineUsers.containsValue(currentUser)}">
                                            <span class="badge badge-success">Online</span>
                                        </c:if>
                                    </a>
                                </td>
                            </c:when>
                            <c:when test="${currentUser.id != sessionScope.user.id}">
                                <td>
                                    <a href="user-profile?userid=${currentUser.id}">${currentUser.userName}
                                        <c:if test="${applicationScope.onlineUsers.containsValue(currentUser)}">
                                            <span class="badge badge-success">Online</span>
                                        </c:if>
                                    </a>
                                </td>
                            </c:when>
                        </c:choose>
                        <td>${currentUser.userType}</td>
                        <td>${currentUser.quizResults.size()}</td>
                        <td>${currentUser.quizzes.size()}</td>
                        <td>${currentUser.achievements.size()}</td>
                        <c:choose>
                            <c:when test="${sessionScope.user!=null && sessionScope.user.userType==UserType.Admin}">
                                <td>
                                    <%
                                        User user1 = (User) pageContext.getAttribute("currentUser");
                                        List<EditFormField> editFormFields = new ArrayList<>();
                                        editFormFields.add(new EditFormField("Username", FormFields.username.getValue(), InputType.text, true, 4, user1.getUserName(), true));
                                        editFormFields.add(new EditFormField("Password", FormFields.password.getValue(), InputType.password, true, 4, "", false));
                                        editFormFields.add(new EditFormField("Confirm Password", FormFields.confirmpassword.getValue(), InputType.password, true, 4, "", false));
                                        editFormFields.add(new EditFormField("Mail", FormFields.mail.getValue(), InputType.email, true, 4, user1.getMail(), true));
                                        editFormFields.add(new EditFormField("First Name", FormFields.firstname.getValue(), InputType.text, true, 0, user1.getFirstName(), false));
                                        editFormFields.add(new EditFormField("Last Name", FormFields.lastname.getValue(), InputType.text, true, 0, user1.getLastName(), false));
                                        SelectField editSelectField = new SelectField("User Type", "usertype", Arrays.asList("admin", "user"));
                                    %>
                                    <!-- ***** delete user modal ***** -->
                                    <h:delete entityName="User"
                                              actionServlet="DeleteUserServlet"
                                              hiddenParameterName="deleteUserId"
                                              hiddenParameterValue="${currentUser.id}">
                                    </h:delete>
                                    <!-- ***** update user modal ***** -->
                                    <h:edit entityName="User"
                                            actionServlet="EditUserServletFromAdmin"
                                            hiddenParameterName="hiddenId"
                                            hiddenParameterValue="${currentUser.id}"
                                            formFields="<%=editFormFields%>"
                                            selectFields="<%=editSelectField%>">
                                    </h:edit>
                                    <c:if test="${currentUser.userType==UserType.User}">
                                        <form action="PromoteUserServlet" method="post" style="float:right">
                                            <button type="submit"  class="mini ui green button">
                                                <i class="fa fa-coffee"></i> Promote
                                            </button>
                                            <input type="hidden" name="promotableUserId" value="${currentUser.id}">
                                        </form>
                                    </c:if>
                                </td>
                            </c:when>
                        </c:choose>
                    </tr>
                    <c:set var="i" value="${i + 1}" scope="page"/>
                </c:forEach>

                </tbody>
                <tfoot>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>Status</th>
                    <th>Completed Quizes</th>
                    <th>Created Quizes</th>
                    <th>Achievements</th>
                    <c:choose>
                        <c:when test="${sessionScope.user!=null && sessionScope.user.userType==UserType.Admin}">
                            <th>Action</th>
                        </c:when>
                    </c:choose>
                </tr>
                </tfoot>
            </table>
        </div>
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
<jsp:include page="components/notifications.jsp"/>

</body>
</html>
