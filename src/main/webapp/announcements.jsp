<%@ page import="dao.AnnouncementDao" %>
<%@ page import="datatypes.*" %>
<%@ page import="enums.DaoType" %>
<%@ page import="enums.FormFields" %>
<%@ page import="enums.InputType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.formfields.EditFormField" %>
<%@ page import="datatypes.formfields.SelectField" %>
<%@ page import="datatypes.formfields.FormField" %>
<%@ page import="datatypes.announcement.Announcement" %>
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


</head>
<body>

<%
    DaoManager daoManager = (DaoManager) request.getServletContext().getAttribute("manager");
    AnnouncementDao announcementDao = daoManager.getDao(DaoType.Announcement);
%>
<!-- ***** Preloader Start ***** -->
<div id="preloader">
    <div class="saxl-preloader"></div>
</div>
<!-- ***** Header Area Start ***** -->
<header class="header_area clearfix">
    <jsp:include page="components/header.jsp"/>
</header>
<!-- ***** Header Area End ***** -->
<!-- ***** Breadcumb Area Start ***** -->
<div class="saxl-breadcumb-area" style="background-image: url(img/core-img/breadcumb.png);">
    <div class="container h-100">
        <div class="row h-100 align-items-center">
            <div class="col-12">
                <div class="bradcumbContent">
                    <h2>Announcements List</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page">Announcements</li>
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
    SelectField field = new SelectField("Status",FormFields.activeOrNot.getValue(), Arrays.asList("Active", "Inactive"));
    List<FormField> formFields = new ArrayList<>();
    formFields.add(new FormField("Announcement Text", FormFields.announcementText.getValue(),  InputType.text, true, 1));
    formFields.add(new FormField("Hyperlink", FormFields.hyperlink.getValue(),  InputType.text, true, 1));
%>
<section class="saxl-aboutUs-area">
    <div class="container">
        <h3 class="mb-30">All Announcements</h3>
        <!-- ***** create announcement modal ***** -->
        <h:create entityName="Announcement" selectFields="<%=field%>" actionServlet="CreateAnnouncementServlet" formFields="<%=formFields%>" formId="createAnnouncementForm"/>

        <table id="myTable" class="table table-striped table-bordered table-sm">
            <thead>
            <tr>
                <th>#</th>
                <th>Text</th>
                <th>Active</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="i" value="0" scope="page"/>
            <c:forEach items="<%=announcementDao.findAll()%>" var="announcement">
                <tr>
                    <td>${i+1}
                    </td>
                    <td>
                        <a href="${announcement.hyperLink}">${announcement.announcementText}
                        </a>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${announcement.active}">
                                <i class="fa fa-check"> Active</i>
                            </c:when>
                            <c:when test="${!announcement.active}">
                                <i class="fa fa-close"> Inactive</i>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                        <h:delete entityName="Announcement" actionServlet="DeleteAnnouncementServlet" hiddenParameterName="announcementId" hiddenParameterValue="${announcement.id}"/>
                        <%
                            SelectField editSelectField = new SelectField("Status",FormFields.activeOrNot.getValue(), Arrays.asList("Active", "Inactive"));
                            List<EditFormField> editFormFields = new ArrayList<>();
                            editFormFields.add(new EditFormField("AnnouncementText", FormFields.announcementText.getValue(),  InputType.text, true, 0, ((Announcement) pageContext.getAttribute("announcement")).getAnnouncementText(), false));
                            editFormFields.add(new EditFormField("Hyperlink", FormFields.hyperlink.getValue(),  InputType.text, true, 0, ((Announcement) pageContext.getAttribute("announcement")).getHyperLink(), false));
                        %>
                        <!-- Update  Modal -->
                        <h:edit entityName="Announcement" actionServlet="EditAnnouncementServlet" hiddenParameterName="editAnnouncementId" hiddenParameterValue="${announcement.id}" formFields="<%=editFormFields%>" selectFields="<%=editSelectField%>"/>
                    </td>
                </tr>
                <c:set var="i" value="${i + 1}" scope="page"/>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th>#</th>
                <th>Text</th>
                <th>Active</th>
                <th>Action</th>
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
<script src="js/toastr.js"></script>

<c:if test="${requestScope.error !=null}">
    <script>
        toastr.options.closeButton = true;
        toastr.options.timeOut = 0;
        toastr.options.extendedTimeOut = 0;
        toastr.error("${requestScope.error}");
    </script>
    ${requestScope.remove("error")}
</c:if>
</body>
</html>
