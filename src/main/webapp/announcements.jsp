<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h" %>
<%@ page import="datatypes.announcement.Announcement" %>
<%@ page import="datatypes.formfields.EditFormField" %>
<%@ page import="datatypes.formfields.FormField" %>
<%@ page import="datatypes.formfields.SelectField" %>
<%@ page import="enums.FormFields" %>
<%@ page import="enums.InputType" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.List" %>

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
        <%
            SelectField field = new SelectField("Status",FormFields.activeOrNot.getValue(), Arrays.asList("Active", "Inactive"));
            List<FormField> formFields = new ArrayList<>();
            formFields.add(new FormField("Announcement Text", FormFields.announcementText.getValue(),  InputType.text, true, 1));
            formFields.add(new FormField("Hyperlink", FormFields.hyperlink.getValue(),  InputType.text, true, 1));
        %>
        <section class="mosh-aboutUs-area">
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
                    <c:forEach items="${requestScope.allAnnouncements}" var="announcement">
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
                                        <i class="fas fa-check"></i> Active
                                    </c:when>
                                    <c:when test="${!announcement.active}">
                                        <i class="fas fa-ban"></i> Inactive</i>
                                    </c:when>
                                </c:choose>
                            </td>
                            <td>
                                <%
                                    SelectField editSelectField = new SelectField("Status",FormFields.activeOrNot.getValue(), Arrays.asList("Active", "Inactive"));
                                    List<EditFormField> editFormFields = new ArrayList<>();
                                    editFormFields.add(new EditFormField("AnnouncementText", FormFields.announcementText.getValue(),  InputType.text, true, 0, ((Announcement) pageContext.getAttribute("announcement")).getAnnouncementText(), false));
                                    editFormFields.add(new EditFormField("Hyperlink", FormFields.hyperlink.getValue(),  InputType.text, true, 0, ((Announcement) pageContext.getAttribute("announcement")).getHyperLink(), false));
                                %>
                                <div class="ui mini buttons">
                                    <h:delete entityName="Announcement" actionServlet="DeleteAnnouncementServlet" hiddenParameterName="announcementId" hiddenParameterValue="${announcement.id}"/>
                                    <div class="or" data-text="or"></div>
                                    <h:edit entityName="Announcement" actionServlet="EditAnnouncementServlet" hiddenParameterName="editAnnouncementId" hiddenParameterValue="${announcement.id}" formFields="<%=editFormFields%>" selectFields="<%=editSelectField%>"/>
                                </div>
                                <!-- Update  Modal -->
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