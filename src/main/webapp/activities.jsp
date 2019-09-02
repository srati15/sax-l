<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>Sax-L Quiz Website</title>
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/sidebar.css">
    <link rel="stylesheet" href="css/timeline.css">
    <link rel="stylesheet" href="css/segment.css">
    <link rel="stylesheet" href="css/label.min.css">
    <link rel="stylesheet" href="css/button.min.css">
    <link rel="stylesheet" type="text/css" href="css/dataTables.bootstrap4.min.css"/>

</head>

<body>

<div class="wrapper">
    <!-- Sidebar Holder -->
    <jsp:include page="components/sidebar.jsp"/>
    <!-- Page Content Holder -->
    <div id="content">
        <jsp:include page="components/topbar.jsp"/>
        <div class="container"  >
            <div class="ui red segment">
                <h2><i class="fas fa-info"></i> All Activities</h2>
                <table id="myTable" class="table table-striped table-bordered table-sm" style="display: inline-table">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>User</th>
                        <th>Action</th>
                        <th>Date</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="i" value="0" scope="page"/>
                    <c:forEach items="${requestScope.activities}" var="activity">
                        <tr>
                            <c:set var="user" value="${requestScope.userDao.findById(activity.userId)}"/>
                            <td>${i+1}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${sessionScope.user.id != activity.userId}">
                                        <a href="user-profile?userid=${activity.userId}">${user.userName}
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="profile">${user.userName}
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                    ${activity.activityName}
                            </td>
                            <td>
                                    ${requestScope.formatter.format(activity.dateTime)}
                            </td>
                        </tr>
                        <c:set var="i" value="${i + 1}" scope="page"/>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <th>#</th>
                        <th>User</th>
                        <th>Action</th>
                        <th>Date</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>

    </div>
</div>

<script src="js/solid.js"></script>
<script src="js/fontawesome.js"></script>

<script src="js/jquery.min.js"></script>
<!-- Popper.JS -->
<script src="js/popper.min.js"></script>
<!-- Bootstrap JS -->
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>

<script src="js/dataTables.bootstrap4.min.js"></script>

<script type="text/javascript">
    $(document).ready(function () {
        $('#sidebarCollapse').on('click', function () {
            $('#sidebar').toggleClass('active');
            $(this).toggleClass('active');
        });
        $('.table').DataTable();
        $('.modal').appendTo("body");
        $('.toast').appendTo("body");
    });
</script>

<script src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.3.26/jquery.form-validator.min.js"></script>

</body>

</html>