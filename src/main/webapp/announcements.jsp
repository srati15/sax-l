<%@ page import="dao.AnnouncementDao" %>
<%@ page import="datatypes.Announcement" %>
<%@ page import="datatypes.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="enums.UserType" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="java.util.List" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="datatypes.FormField" %>
<%@ page import="enums.FormFields" %>
<%@ page import="enums.InputType" %>
<%@ page import="datatypes.SelectField" %>
<%@ page import="java.util.Arrays" %>
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
    User user = (User) request.getSession().getAttribute("user");
    if (user == null) response.sendError(404);
    if (user.getUserType() != UserType.Admin) response.sendError(404);
    AnnouncementDao announcementDao = daoManager.getDao(DaoType.Announcement);
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
                    <h2>Announcements List</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
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
    JSONArray array = new JSONArray();
    array.put(new JSONObject(new FormField(FormFields.announcementText.getValue(),  InputType.text, true, 4)));
    array.put(new JSONObject(new FormField(FormFields.hyperlink.getValue(),  InputType.text, true, 0)));
    JSONObject select = new JSONObject(new SelectField(FormFields.activeOrNot.getValue(), Arrays.asList("Active", "Inactive")));
%>
<section class="mosh-aboutUs-area">
    <div class="container">
        <h3 class="mb-30">All Announcements</h3>
        <!-- ***** create announcement modal ***** -->
        <jsp:include page="components/create-modal.jsp">
            <jsp:param name="entityName" value="announcement"/>
            <jsp:param name="actionServlet" value="CreateAnnouncementServlet"/>
            <jsp:param name="formId" value="createAnnouncementForm"/>
            <jsp:param name="fieldFormJson" value="<%=array.toString()%>"/>
            <jsp:param name="selects" value="<%=select.toString()%>"/>
            <jsp:param name="selectDisplayName" value="Active"/>
        </jsp:include>        <!-- ***** update user modal end***** -->

        <table id="myTable" class="table table-striped table-bordered table-sm" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>#</th>
                <th>Text</th>
                <th>Active</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Announcement> announcements = announcementDao.findAll();
                for (int i = 0; i < announcements.size(); i++) {%>
            <tr>
                <td><%=i + 1%>
                </td>
                <td>
                    <a href="<%=announcements.get(i).getHyperLink()%>"><%=announcements.get(i).getAnnouncementText()%>
                    </a>
                </td>
                <td>
                    <% if (announcements.get(i).isActive()) {%>
                    <i class="fa fa-check"> Active</i>
                    <%} else {%>
                    <i class="fa fa-close"> Inactive</i>
                    <%}%>
                </td>
                <td>
                    <jsp:include page="components/delete-modal.jsp">
                       <jsp:param name="entityName" value="announcement"/>
                       <jsp:param name="deleteParameterName" value="announcementId"/>
                       <jsp:param name="deleteParameterId" value="<%=announcements.get(i).getId()%>"/>
                       <jsp:param name="actionServlet" value="DeleteAnnouncementServlet"/>
                   </jsp:include>
                    <!-- Update  Modal -->
                    <button type="button" class="btn btn-info btn-sm" data-toggle="modal"
                            data-target="#editAnnouncementModal<%=i%>">
                        <i class="fa fa-edit"></i> Edit
                    </button>
                    <div class="modal fade" id="editAnnouncementModal<%=i%>" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLongTitle2">Edit announcement</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <form action="EditAnnouncementServlet" method="post">

                                    <div class="modal-body">
                                        <div class="form-group">
                                            <label for="AnnouncementText">Announcement Text</label>
                                            <input type="text" class="form-control" name="announcementText"
                                                   id="AnnouncementText"
                                                   value="<%=announcements.get(i).getAnnouncementText()%>">
                                        </div>
                                        <div class="form-group">
                                            <label for="HyperLink">Hyperlink</label>
                                            <input type="text" class="form-control" id="HyperLink" name="hyperlink"
                                                   value="<%=announcements.get(i).getHyperLink()%>">
                                        </div>
                                        <div class="form-group">
                                            <label>Active</label>
                                            <select class="form-control" name="activeOrNot">
                                                <option value="active">Yes</option>
                                                <option value="inactive">No</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel
                                        </button>
                                        <input type="submit" class="btn btn-primary" value="Update"/>
                                        <input type="text" hidden name="editAnnouncementId"
                                               value="<%=announcements.get(i).getId()%>">
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <%
                }
            %>
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
