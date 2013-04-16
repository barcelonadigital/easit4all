<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<ul class="imagedList">
	<c:forEach items="${users}" var="userf" varStatus="status">
			<li class="imagedItem">
				<div class="image">
					<img src="http://graph.facebook.com/<c:out value="${userf.id}"/>/picture" align="left"/>
				</div>
				<div class="icons">
					<c:choose>
					<c:when test="${areFriends[status.index] eq false}">
						<form name ="addFriend" action="<c:url value="/facebook/manageRequest/${userf.id}"/>" method = "POST">
						 	<input type="hidden" name="uri" value="${uri}"/>
						 	<a href="#" onclick="parentNode.submit();" alt="Add friend" title="Add friend">
						 		<img alt="" src="<c:url value="/resources/img/facebook-add-friend.png"/>" width="18" height="18"  style="appearance: icon;">
						 	</a>
					 	</form>   
				 	</c:when>
				 	<c:otherwise>&#10003;</c:otherwise>
				 </c:choose>
				</div>
				<div class="content">
					<strong><a href="<c:url value="/facebook/friends/${userf.id}/wall"/>"><c:out value="${userf.name}"/></a></strong><br/>
				</div>
			</li>
	</c:forEach>
</ul>