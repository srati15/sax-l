<button type="button" class="btn btn-success" data-toggle="modal"
        data-target="#createUserModal">
    <i class="fa fa-plus"></i> Create
</button>
<div class="modal fade" id="createUserModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle2">Create new user</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="CreateUserServlet" method="post" id="signUpForm">

                <div class="modal-body">
                    <div class="form-group">
                        <label for="Username">Username</label>
                        <input type="text" class="form-control" name="username"
                               id="Username" placeholder="Username" required minlength="4">
                    </div>
                    <div class="form-group">
                        <label for="Password">Password</label>
                        <input type="password" class="form-control" name="password" id="Password"
                               placeholder="Password" required minlength="4">
                    </div>
                    <div class="form-group">
                        <label for="ConfPassword">Confirm Password</label>
                        <input type="password" class="form-control" name="confirmpassword"
                               id="ConfPassword" placeholder="Confirm Password" required minlength="4">
                    </div>
                    <div class="form-group">
                        <label for="Email">Email address</label>
                        <input type="email" class="form-control" id="Email"
                               name="mail" placeholder="E-mail" required>
                    </div>
                    <div class="form-group">
                        <label for="FirstName">First Name</label>
                        <input type="text" class="form-control" id="FirstName" name="firstname"
                               placeholder="First Name" required>
                    </div>
                    <div class="form-group">
                        <label for="LastName">Last Name</label>
                        <input type="text" class="form-control" id="LastName" name="lastname"
                               placeholder="Last Name" required>
                    </div>
                    <div class="form-group">
                        <label>User Type</label>
                        <select class="form-control" name="usertype" required>
                            <option value="admin">Admin</option>
                            <option value="user">User</option>
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
                ${"#signUpForm"}.validate();
            </script>
        </div>
    </div>
</div>
