<%@ attribute name="entityName" required="true" %>
<%@ attribute name="actionServlet" required="true" type="java.lang.String" %>
<%@ attribute name="formFields" required="true" type="java.util.List<datatypes.formfields.FormField>" %>
<%@ attribute name="selectFields" required="false" type="datatypes.formfields.SelectField" %>
<%@ attribute name="formId" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<button type="button" class="btn btn-success" data-toggle="modal"
        data-target="#createModal">
    <i class="fa fa-plus"></i> Create
</button>
<div class="modal fade" id="createModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle2">Create new <%=entityName%></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="<%=actionServlet%>" method="post" id="createForm">

                <div class="modal-body">
                    <c:forEach items="${formFields}" var="formField">
                        <div class="form-group">
                            <label for="${formField.name}">${formField.displayName}</label>
                            <input type="${formField.inputType.value}" class="form-control" name="${formField.name}"
                                   id="${formField.name}" placeholder="${formField.displayName}" ${formField.required} minlength="${formField.minLength}">
                        </div>
                    </c:forEach>

                    <div class="form-group">
                        <label>${selectFields.displayName}</label>
                        <select class="form-control" name="${selectFields.name}" required>
                            <c:forEach items="${selectFields.selectStrings}" var="selectValue">
                                <option value="${selectValue}">${selectValue}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <div class="ui buttons">
                        <button class="ui button" data-dismiss="modal">Cancel</button>
                        <div class="or"></div>
                        <button type="submit" class="ui positive button">Create</button>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>