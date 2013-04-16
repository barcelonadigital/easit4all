<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="feed" id="notif">
	<div class="ym-grid">
		<div class="ym-hlist">
			<span class="subtitle"><strong>Friend Requests</strong></span>
			<div id="pagination">
				<span class = "pagination-list">
					<c:choose>
						<c:when test="${pageSize eq 0}">
							0
						</c:when>
						<c:otherwise>
							<c:out value="${offset+1}"/>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${offset + FACEBOOK_LIMIT_RESULT le pageSize}">
							- <c:out value="${offset+FACEBOOK_LIMIT_RESULT}"/> of <c:out value="${pageSize}"/>
						</c:when>	
						<c:otherwise>
							- <c:out value="${pageSize}"/> of <c:out value="${pageSize}"/>
						</c:otherwise>
					</c:choose>	
				</span>
				<c:choose>
					<c:when test="${offset ne 0}">
						<a class="ym-button pagination-previous" href="notifications?offset=${offset-FACEBOOK_LIMIT_RESULT}">&#9668;</a>
					</c:when>
					<c:otherwise>
						<a class="ym-button pagination-previous ym-disabled">&#9668;</a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${offset + FACEBOOK_LIMIT_RESULT lt pageSize}">
						<a class="ym-button pagination-next" href="notifications?offset=${offset+FACEBOOK_LIMIT_RESULT}" >&#9658;</a>	
					</c:when>	
					<c:otherwise>
						<a class="ym-button pagination-next ym-disabled">&#9658;</a>	
					</c:otherwise>
				</c:choose>
			</div>
		</div> 
	</div>
	<ul class="imagedList">
		<c:forEach items="${notif}" var="nots">
			<li class="imagedItem">
				<div class="image">
					<img src="http://graph.facebook.com/<c:out value="${nots.from.id}"/>/picture" align="left"/>
				</div>
				<div class="content">
					<div class="grid">
						<div class="ym-g65 ym-gl">
							<div class="ym-gbox ym-clearfix">
								From: <a href="http://www.facebook.com/${nots.from.id}">${nots.from.name}</a>   <br />
								Created time: ${nots.createdTime}
							</div>
						</div>
						<div class="ym-g35 ym-gr">
							<div class="ym-gbox ym-clearfix">
								<form name ="acceptFriendRequest" id="acceptFriendRequest" action="<c:url value="/facebook/manageRequest/${nots.from.id}"/>" method = "POST">
						 			<input type="hidden" name="uri" value="${uri}"/>
						 			<button type="submit" class="ym-button">Accept</button>
								</form>
							</div>
						</div> 
					</div>
				</div>
	</c:forEach>
	</ul>

</div>
