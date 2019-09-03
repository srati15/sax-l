<!-- Button trigger modal -->
<a href="" data-toggle="modal" data-target="#editProfileModal">
    <i class="fas fa-edit"></i> Edit Profile
</a>

<!-- Modal -->
<div class="modal fade" id="editProfileModal" tabindex="-1" role="dialog" aria-labelledby="editProfileModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editProfileModalLabel">Edit Profile</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="UpdateUserServlet" method="post">
                <div class="modal-body">

                    <div class="form-group">
                        <label for="username">Username</label>
                        <input type="text" class="form-control" name="username" id="username" disabled minlength="4" placeholder="${sessionScope.user.userName}" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" name="password" id="password" placeholder=""
                               minlength="4" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Confirm password</label>
                        <input type="password" class="form-control" name="confirmpassword" id="confirmpassword" placeholder=""
                               minlength="4" required>
                    </div>
                    <div class="form-group">
                        <label for="mail">E-mail</label>
                        <input type="email" class="form-control" disabled name="mail" id="mail" placeholder="${sessionScope.user.mail}" required>
                    </div>
                    <div class="form-group">
                        <label for="username">First Name</label>
                        <input type="text" class="form-control" name="firstname" id="firstname" value="${sessionScope.user.firstName}" required>
                    </div>
                    <div class="form-group">
                        <label for="username">Last Name</label>
                        <input type="text" class="form-control" name="lastname" id="lastname" value="${sessionScope.user.lastName}" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>
