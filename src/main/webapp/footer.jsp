<%@ page import="manager.DaoManager" %>
<!-- Top Fotter Area -->
<%
    DaoManager manager = (DaoManager) request.getServletContext().getAttribute("manager");

%>
<div role="alert" aria-live="assertive" aria-atomic="true" class="toast" data-autohide="false">
    <div class="toast-header">
        <svg class=" rounded mr-2" width="20" height="20" xmlns="http://www.w3.org/2000/svg"
             preserveAspectRatio="xMidYMid slice" focusable="false" role="img">
            <rect fill="#007aff" width="100%" height="100%" /></svg>
        <strong class="mr-auto">Bootstrap</strong>
        <small>11 mins ago</small>
        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
            <span aria-hidden="true">&times;</span>
        </button>
    </div>
    <div class="toast-body">
        Hello, world! This is a toast message.
    </div>
</div>
<div class="top-footer-area section_padding_100_0">
    <div class="container">
        <div class="row">
            <div class="col-12 col-sm-6 col-lg-3">
                <div class="single-footer-widget mb-100">
                    <a href="#" class="mb-50 d-block"><img src="img/core-img/logo.png" alt=""></a>
                </div>
            </div>

            <div class="col-12 col-sm-6 col-lg-3">
                <div class="single-footer-widget mb-100">
                    <h5>Contact Info</h5>
                    <div class="footer-single-contact-info d-flex">
                        <div class="contact-icon">
                            <img src="img/core-img/message.png" alt="">
                        </div>
                        <p>sax-l@gmail.com</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Fotter Bottom Area -->
<div class="footer-bottom-area">
    <div class="container h-100">
        <div class="row h-100">
            <div class="col-12 h-100">
                <div class="footer-bottom-content h-100 d-md-flex justify-content-between align-items-center">
                    <div class="footer-social-info">
                        <a href="#"><i class="fa fa-pinterest" aria-hidden="true"></i></a>
                        <a href="#"><i class="fa fa-facebook" aria-hidden="true"></i></a>
                        <a href="#"><i class="fa fa-twitter" aria-hidden="true"></i></a>
                        <a href="#"><i class="fa fa-dribbble" aria-hidden="true"></i></a>
                        <a href="#"><i class="fa fa-behance" aria-hidden="true"></i></a>
                        <a href="#"><i class="fa fa-linkedin" aria-hidden="true"></i></a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>