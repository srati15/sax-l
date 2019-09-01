<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:choose>
    <c:when test="${sessionScope.user.achievements.size() > 0}">
        <div class="tab-pane fade" id="achievements">
            <ul class="list-group">
                <p class="text-primary"></p>
                <c:forEach var="achievement" items="${sessionScope.user.achievements}">
                    <li class="list-group-item">
                        <button type="button" class="btn btn-secondary" data-toggle="tooltip" data-html="true"
                                data-placement="top"
                                title="${achievement.achievement.achievementName}, <p class='text-success'>${achievement.achievement.achievementCriteria}</p>">
                            <img src="../../img/core-img/medal.png"
                                 alt="${achievement.achievement.achievementName}">
                        </button>
                    </li>

                </c:forEach>

            </ul>
        </div>
    </c:when>
    <c:otherwise>
        You have no achievements
    </c:otherwise>
</c:choose>

