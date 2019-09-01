<!-- Button trigger modal -->
<a href="" data-toggle="modal" data-target="#achievementsModal">
    <i class="fas fa-medal"></i> Achievements
</a>

<!-- Modal -->
<div class="modal fade" id="achievementsModal" tabindex="-1" role="dialog" aria-labelledby="achievementsModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="achievementsModalLabel">Achievements</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
                <div class="modal-body">
                    <jsp:include page="../profile/achievements-view.jsp"/>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                </div>
        </div>
    </div>
</div>
