<%@ attribute name="imageSource" required="true" type="java.lang.String" %>
<%@ attribute name="heading" required="true" type="java.lang.String" %>
<%@ attribute name="reference" required="true" type="java.lang.String" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-12 col-sm-6 col-md-4">
    <div class="single-feature-area d-flex mb-100">
        <div class="feature-icon mr-30">
            <img src="${imageSource}" alt="">
        </div>
        <div class="feature-content">
            <h4>${heading}</h4>
            <p><a href="${reference}">Manage ${heading}</a></p>
        </div>
    </div>
</div>
