<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">

        <button type="button" id="sidebarCollapse" class="navbar-btn">
            <span></span>
            <span></span>
            <span></span>
        </button>

        <button class="btn btn-dark d-inline-block d-lg-none ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <i class="fas fa-align-justify"></i>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
                </li>

                <li class="nav-item">
                    <a class="nav-link" href="contact">Contact</a>
                </li>
                <c:if test="${sessionScope.user !=null}">
                    <li class="nav-item">
                        <a class="nav-link" href="users-list">Users</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <jsp:include page="dropdown-notifications.jsp"/>
                </li>


            </ul>
        </div>
    </div>
</nav>
<jsp:include page="notifications.jsp"/>
