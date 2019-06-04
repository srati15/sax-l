<%@ page import="datatypes.FormField" %>
<%@ page import="java.util.List" %>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="enums.InputType" %>
<%@ page import="datatypes.SelectField" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.mysql.cj.util.StringUtils" %>
<button type="button" class="btn btn-success" data-toggle="modal"
        data-target="#createModal">
    <i class="fa fa-plus"></i> Create
</button>
<%
    String entity = request.getParameter("entityName");
    String servlet = request.getParameter("actionServlet");
    String formId = request.getParameter("formId");
    String validateUrl = "${\"#"+formId+"\"}.validate();\n";
    JSONArray jsonArray = new JSONArray(request.getParameter("fieldFormJson"));
    JSONObject selects = new JSONObject(request.getParameter("selects"));
    String name = selects.getString("name");
    String selectDisplayName = request.getParameter("selectDisplayName");
    JSONArray selectValues = selects.getJSONArray("selectStrings");
%>
<%

%>
<div class="modal fade" id="createModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle2">Create new <%=entity%></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="<%=servlet%>" method="post" id="<%=formId%>">
                <div class="modal-body">
                <%
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String capitalized = object.getString("name").substring(0,1).toUpperCase()+object.getString("name").substring(1);
                %>
                    <div class="form-group">
                        <label for="<%=capitalized%>"><%=capitalized%></label>
                        <input type="<%=object.getString("inputType")%>" class="form-control" name="<%=object.getString("name")%>"
                               id="<%=capitalized%>" <%=object.getBoolean("required")? "required":""%> minlength="<%=object.getInt("minLength")%>">
                    </div>
                <%
                    }%>
                    <div class="form-group">
                        <label><%=selectDisplayName%></label>
                        <select class="form-control" name="<%=name%>" required>
                            <%
                                for (int i =0; i < selectValues.length(); i++) {%>
                            <option value="<%=selectValues.get(i)%>"><%=selectValues.get(i)%></option>
                            <%
                                }
                            %>
                        </select>
                    </div>

                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel
                    </button>
                    <input type="submit" class="btn btn-primary" value="Create"/>
                </div>
            </form>
            <script>
                <%=validateUrl%>
            </script>
        </div>
    </div>
</div>
