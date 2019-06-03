<button type="button" class="btn btn-success" data-toggle="modal"
        data-target="#createAnnouncementModal">
    <i class="fa fa-plus"></i> Create
</button>
<div class="modal fade" id="createAnnouncementModal" tabindex="-1" role="dialog"
     aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle2">Create new announcement</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="CreateAnnouncementServlet" method="post">

                <div class="modal-body">
                    <div class="form-group">
                        <label for="AnnouncementText">Announcement Text</label>
                        <input type="text" class="form-control" name="announcementText"
                               id="AnnouncementText" placeholder="Your text here">
                    </div>
                    <div class="form-group">
                        <label for="HyperLink">Hyperlink</label>
                        <input type="text" class="form-control" id="HyperLink" name="hyperlink"
                               placeholder="Hyperlink here">
                    </div>
                    <div class="form-group">
                        <label>Active</label>
                        <select class="form-control" name="activeOrNot">
                            <option value="active">Yes</option>
                            <option value="inactive">No</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel
                    </button>
                    <input type="submit" class="btn btn-primary" value="Create"/>
                </div>
            </form>

        </div>
    </div>
</div>