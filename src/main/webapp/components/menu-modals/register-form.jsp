<!-- Button trigger modal -->
<a href="" data-toggle="modal" data-target="#registerModal">
    <i class="fas fa-user"></i> Register
</a>

<!-- Modal -->
<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="registerModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="registerModalLabel">Register</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="register" method="post">
                <div class="modal-body">

                    <div class="form-group">
                        <label>Username</label>
                        <input type="text" class="form-control" name="username"  minlength="4" placeholder="" required>
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input type="password" class="form-control" name="password"  placeholder=""
                               minlength="4" required>
                    </div>
                    <div class="form-group">
                        <label >Confirm password</label>
                        <input type="password" class="form-control" name="confirmpassword"  placeholder=""
                               minlength="4" required>
                    </div>
                    <div class="form-group">
                        <label >E-mail</label>
                        <input type="email" class="form-control" name="mail" placeholder="" required>
                    </div>
                    <div class="form-group">
                        <label>First Name</label>
                        <input type="text" class="form-control" name="firstname"  placeholder="" required>
                    </div>
                    <div class="form-group">
                        <label>Last Name</label>
                        <input type="text" class="form-control" name="lastname" placeholder="" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Register</button>
                </div>
            </form>
        </div>
    </div>
</div>
