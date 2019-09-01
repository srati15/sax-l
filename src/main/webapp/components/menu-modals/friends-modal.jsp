    <a href="" data-toggle="modal" data-target="#friendsModal">
        <i class="fas fa-user-friends"></i> Friends
        </a>
        <!-- Modal -->
        <div class="modal fade"  id="friendsModal" tabindex="-1" role="dialog"
        aria-labelledby="friendsModalLabel" aria-hidden="true">
        <div class="modal-dialog mw-100 w-100" role="document">
        <div class="modal-content">
        <div class="modal-header">
        <h5 class="modal-title" id="friendsModalLabel">Friends</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
        </div>
        <div class="modal-body">
        <jsp:include page="../profile/friends-table.jsp"/>
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        </div>
        </div>
        </div>