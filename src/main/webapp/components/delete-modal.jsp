
<%
    String entity = request.getParameter("entityName");
    String parameterId = request.getParameter("deleteParameterId");
    String servlet = request.getParameter("actionServlet");
    String parameterName = request.getParameter("deleteParameterName");
    String target = entity+"Modal"+parameterId;
%>
<!-- Delete Modal -->
<button type="button" class="btn btn-danger btn-sm" data-toggle="modal"
        data-target="#<%=target%>">
    <i class="fa fa-trash"></i> Delete
</button>
<div class="modal fade" id="<%=target%>" tabindex="-1" role="dialog"
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
                Are you sure you want to delete <%=entity%>?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
                <form action="<%=servlet%>" method="post">
                    <input type="submit" class="btn btn-primary" value="Yes"/>
                    <input type="text" hidden name="<%=parameterName%>"
                           value="<%=parameterId%>">
                </form>
            </div>
        </div>
    </div>
</div>
<!-- Delete Modal -->