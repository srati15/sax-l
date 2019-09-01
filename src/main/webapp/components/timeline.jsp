<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.user != null}">
    <div class="ui red segment">
        <h2><i class="fas fa-rss"></i> News Feed</h2>
        <div class="row">
            <!-- Timeline -->
            <ul class="timeline">
                <li class="timeline-item bg-white rounded ml-3 p-4 shadow">
                    <div class="timeline-arrow"></div>
                    <h2 class="h5 mb-0">Title of section 1</h2><span class="small text-gray"><i class="fas fa-clock"></i> 21 March, 2019</span>
                    <p class="text-small mt-2 font-weight-light">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque scelerisque diam non nisi semper, et elementum lorem ornare. Maecenas placerat facilisis mollis. Duis sagittis ligula in sodales vehicula....</p>
                </li>
                <li class="timeline-item bg-white rounded ml-3 p-4 shadow">
                    <div class="timeline-arrow"></div>
                    <h2 class="h5 mb-0">Title of section 2</h2><span class="small text-gray"><i class="fas fa-clock"></i> 5 April, 2019</span>
                    <p class="text-small mt-2 font-weight-light">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque scelerisque diam non nisi semper.</p>
                    <p class="text-small mt-2 font-weight-light">Libero expedita explicabo eius fugiat quia aspernatur autem laudantium error architecto recusandae natus sapiente sit nam eaque, consectetur porro molestiae ipsam! Deleniti.</p>
                </li>
                <li class="timeline-item bg-white rounded ml-3 p-4 shadow">
                    <div class="timeline-arrow"></div>
                    <h2 class="h5 mb-0">Title of section 3</h2><span class="small text-gray"><i class="fa fa-clock-o mr-1"></i>18 August, 2019</span>
                    <p class="text-small mt-2 font-weight-light">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque scelerisque diam non nisi semper, et elementum lorem ornare. Maecenas placerat facilisis mollis. Duis sagittis ligula in sodales vehicula....</p>
                </li>
                <li class="timeline-item bg-white rounded ml-3 p-4 shadow">
                    <div class="timeline-arrow"></div>
                    <h2 class="h5 mb-0">Title of section 4</h2><span class="small text-gray"><i class="fa fa-clock-o mr-1"></i>10 October, 2019</span>
                    <p class="text-small mt-2 font-weight-light">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque scelerisque diam non nisi semper, et elementum lorem ornare. Maecenas placerat facilisis mollis. Duis sagittis ligula in sodales vehicula....</p>
                    <p class="text-small mt-2 font-weight-light">Voluptatibus temporibus esse illum eum aspernatur, fugiat suscipit natus! Eum corporis illum nihil officiis tempore. Excepturi illo natus libero sit doloremque, laborum molestias rerum pariatur quam ipsam necessitatibus incidunt, explicabo.</p>
                </li>
            </ul><!-- End -->

        </div>

    </div>

</c:if>