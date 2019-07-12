<%@ attribute name="entityName" required="true" %>
<%@ attribute name="actionServlet" required="true" %>
<%@ attribute name="hiddenParameterName" required="true" %>
<%@ attribute name="hiddenParameterValue" required="true" type="java.lang.Integer" %>
<%@ attribute name="formFields" required="true" type="java.util.List<datatypes.formfields.EditFormField>" %>
<%@ attribute name="selectFields" required="false" type="datatypes.formfields.SelectField" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="target" value="edit${entityName}Modal${hiddenParameterValue}"/>

<button class="mini ui blue button" data-toggle="modal"
        data-target="#${target}">
    <i class="fa fa-edit"></i> Edit
</button>
<div class="modal fade" id="${target}" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle2">Edit ${entityName}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="${actionServlet}" method="post">

                <div class="modal-body">
                    <c:forEach items="${formFields}" var="formField">
                        <div class="form-group">
                            <label for="${formField.name}">${formField.displayName}</label>
                            <input type="${formField.inputType.value}" class="form-control" name="${formField.name}"
                                   id="${formField.name}" value="${formField.defaultValue}" ${formField.required} ${formField.disabled} minlength="${formField.minLength}">
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
                        <button type="submit" class="ui positive button">Update</button>
                    </div>
                    <input type="text" hidden name="${hiddenParameterName}"
                           value="${hiddenParameterValue}">
                </div>
            </form>

        </div>
    </div>
</div>