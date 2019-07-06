<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dao.FriendRequestDao" %>
<%@ page import="dao.UserDao" %>
<%@ page import="datatypes.User" %>
<%@ page import="enums.DaoType" %>
<%@ page import="enums.RequestStatus" %>
<%@ page import="manager.DaoManager" %>
<%@ page import="dao.TextMessageDao" %>
<%@ page import="java.util.List" %>
<%@ page import="datatypes.messages.TextMessage" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- The above 4 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Title -->
    <title>Sax-L - Quiz Website | Home</title>

    <!-- Favicon -->
    <link rel="icon" href="img/core-img/favicon.ico">

    <!-- Core Stylesheet -->
    <link href="style.css" rel="stylesheet">

    <!-- Responsive CSS -->
    <link href="css/responsive.css" rel="stylesheet">

</head>

<body>

<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");
    FriendRequestDao friendRequestDao = manager.getDao(DaoType.FriendRequest);
    UserDao userDao = manager.getDao(DaoType.User);
    User user = (User) request.getSession().getAttribute("user");
    int id = Integer.parseInt(request.getParameter("userid"));
    User profileUser = userDao.findById(id);
    TextMessageDao textMessageDao = manager.getDao(DaoType.TextMessage);
    List<TextMessage>messages = textMessageDao.getTextMessagesOfGivenUsers(user.getId(), id);
    pageContext.setAttribute("request1",friendRequestDao.findBySenderReceiverId(user.getId(), id));
    pageContext.setAttribute("request2",friendRequestDao.findBySenderReceiverId(id, user.getId()));
    pageContext.setAttribute("mess", messages);
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
                    <h2><%=profileUser.getUserName()%>'s Profile</h2>
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
                            <li class="breadcrumb-item active" aria-current="page"><%=profileUser.getUserName()%>'s
                                profile
                            </li>
                            <c:choose>
                                <c:when test="${request1 == null && request2 == null}">
                                    <div style="margin: -5px 2px 0 10px">
                                        <form action="FriendRequestSenderServlet" method="post">
                                            <input type="submit" class="btn btn-success btn-sm"
                                                   value=  "Send Friend Request"/>
                                            <input type="text" hidden name="receiverId" value="<%=id%>"/>
                                        </form>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${request1!=null && request1.status == RequestStatus.Pending}">
                                             <div style="margin: -5px 2px 0 10px">
                                                <form action="FriendRequestDeleteServlet" method="post">
                                                    <input type="submit" class="btn btn-warning btn-sm"
                                                           value="Cancel Friend Request"/>
                                                    <input type="text" hidden name="receiverId" value="<%=id%>"/>
                                                </form>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${(request1!=null && request1.status == RequestStatus.Accepted) || (request2 !=null && request2.status==RequestStatus.Accepted)}">
                                                    <div style="margin: -5px 2px 0 10px">
                                                        <form action="FriendRequestDeleteServlet" method="post">
                                                            <input type="submit" class="btn btn-warning btn-sm"
                                                                   value="Remove Friend"/>
                                                            <input type="text" hidden name="receiverId" value="<%=id%>"/>
                                                        </form>
                                                    </div>
                                                </c:when>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                            <div style="margin: -5px 2px 0 0">
                                <button class="btn btn-success btn-sm" onclick="openForm()"><i class="fa fa-paper-plane"></i> Send Message</button>
                            </div>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ***** Breadcumb Area End ***** -->
<div class="chat-popup" id="myForm">
    <form action="TextMessageServlet" method="post" class="form-container">
        <div style="overflow-y: scroll; max-height:300px;">
            <%for(int i = 0; i < messages.size(); i++){
                TextMessage curr = messages.get(i);
                if(curr.getSenderId() == user.getId()){%>
            <div class="my-user darker">
                <p><%=curr.getTextMessage()%></p>
            </div>
            <%}else{%>
            <div class="my-user">
                <p><%=curr.getTextMessage()%></p>
            </div>
            <%}
            }%>
        </div>
        <textarea placeholder="Type message.." name="msg" required></textarea>
        <input type="text" hidden name="receiverId" value="<%=id%>"/>
        <input type="text" hidden name="callerPage" value="user-profile"/>
        <button type="submit" class="btn btn-success btn-sm"><i class="fa fa-paper-plane"></i> Send</button>
        <button type="button" class="btn btn-warning btn-sm" onclick="closeForm()">Close</button>
    </form>
</div>

<!-- ***** Footer Area Start ***** -->
<footer class="footer-area clearfix">
    <jsp:include page="components/footer.jsp"/>
</footer>
<!-- ***** Footer Area End ***** -->

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

<script>
    function openForm() {
        document.getElementById("myForm").style.display = "block";
    }

    function closeForm() {
        document.getElementById("myForm").style.display = "none";
    }
</script>

<style>
    /* The popup chat - hidden by default */
    .chat-popup {
        display: none;
        position: fixed;
        bottom: 0;
        right: 15px;
        max-width: 400px;
        border: 3px solid #f1f1f1;
        z-index: 9;
    }

    /* Add styles to the form container */
    .form-container {
        min-width: 300px;
        padding: 10px;
        background-color: #78c4ff;
    }

    /* Full-width textarea */
    .form-container textarea {
        width: 100%;
        padding: 15px;
        margin: 5px 0 22px 0;
        border: none;
        background: #f1f1f1;
        resize: none;
        max-height: 200px;
    }

    /* When the textarea gets focus, do something */
    .form-container textarea:focus {
        background-color: #ddd;
        outline: none;
    }

    .my-user {
        border: 2px solid #dee0e0;
        background-color: #f1f1f1;
        padding: 10px;
        margin: 5px 0;
        height: auto;
    }

    .darker {
        border-color: #ccc;
        background-color: #c8cee1;
        text-align: right;
    }

    .darker p{
        color: #383f83;
    }
    p{
        word-wrap: break-word;
    }
    .my-user::after{
        content: "";
        clear: both;
        display: table;
    }
</style>
</body>

</html>