<%@ attribute name="entityName" required="true" %>
<%@ attribute name="actionServlet" required="true" %>
<%@ attribute name="hiddenParameterName" required="true" %>
<%@ attribute name="hiddenParameterValue" required="true" type="java.lang.Integer" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="target" value="delete${entityName}Modal${hiddenParameterValue}"/>

<!-- Delete Modal -->
<button class="mini negative ui button" data-toggle="modal"
        data-target="#${target}">     <i class="fa fa-trash"></i> Delete
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
            <form action="${actionServlet}" method="post">

                <div class="modal-footer">
                    <div class="ui buttons">
                        <button class="ui button" data-dismiss="modal">No</button>
                        <div class="or"></div>
                        <button type="submit" class="ui negative button">Yes</button>
                    </div>
                    <input type="text" hidden name="${hiddenParameterName}"
                           value="${hiddenParameterValue}">
                </div>
            </form>

        </div>
    </div>
</div>
<!-- Delete Modal -->