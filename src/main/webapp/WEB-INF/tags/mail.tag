<%@ attribute name="message" required="true" type="datatypes.messages.AdminMessage" %>

<button type="button" class="mini positive ui  button" data-toggle="modal" data-target="#viewMail">
    <i class="fas fa-reply"></i> Open
</button>

<!-- Modal -->
<div class="modal fade" id="viewMail" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="name">Sender: ${message.name}</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <h5>${message.messageText}</h5>
                <hr>
                <form action="ReplyMessageServlet" method="post">
                    <div class="form-group">
                        <label for="replyText">Reply</label>
                        <textarea class="form-control" id="replyText" name="replyText" rows="7"></textarea>
                    </div>
                    <input hidden name="messageId" value="${message.id}">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Send</button>
                </form>
            </div>
            <div class="modal-footer">

            </div>
        </div>
    </div>
</div>