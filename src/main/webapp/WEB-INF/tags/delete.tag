<%@ attribute name="entityName" required="true" %>
<%@ attribute name="actionServlet" required="true" %>
<%@ attribute name="hiddenParameterName" required="true" %>
<%@ attribute name="hiddenParameterValue" required="true" type="java.lang.Integer" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="target" value="delete${entityName}Modal${hiddenParameterValue}"/>

<!-- Delete Modal -->
<button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
        data-target="#${target}">
    <i class="fa fa-trash"></i> Delete
</button>
<div class="modal fade" id="${target}" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Confirm delete</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete ${entityName}?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <form action="${actionServlet}" method="post">
                    <input type="submit" class="btn btn-primary" value="Yes"/>
                    <input type="text" hidden name="${hiddenParameterName}"
                           value="${hiddenParameterValue}">
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Delete Modal -->