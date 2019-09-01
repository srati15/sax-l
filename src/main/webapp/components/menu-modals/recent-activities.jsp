    <a href="" data-toggle="modal" data-target="#activitiesModal">
        <i class="fas fa-clipboard-list"></i> Recent Activities
        </a>

        <!-- Modal -->
        <div class="modal fade" id="activitiesModal" tabindex="-1" role="dialog" aria-labelledby="activitiesModalLabel"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
        <div class="modal-content">
        <div class="modal-header">
        <h5 class="modal-title" id="activitiesModalLabel">Recent Activities</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">&times;</span>
        </button>
        </div>
        <div class="modal-body">
        <jsp:include page="../profile/my-activities-table.jsp"/>
        </div>
        <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        </div>
        </div>
        </div>
        </div>
