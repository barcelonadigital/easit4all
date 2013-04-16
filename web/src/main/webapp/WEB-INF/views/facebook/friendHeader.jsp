<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="littleFriendHeader">
	<h4 style="float: left; display:inline;">${fuser.name} &#x25B6; 
		<c:choose>
			<c:when test="${not empty feed}">Wall</c:when>
			<c:when test="${not empty falbums}">Photo albums</c:when>
			<c:when test="${not empty fvideos}">Videos</c:when>
		</c:choose>
	</h4>
</div>
<div id="rightNav">
	<div class="image">
		<img src="http://graph.facebook.com/<c:out value="${fuser.id}"/>/picture?type=large"/>
	</div>
	<div class="ym-vlist">
		<ul>
			<li>
				<a href="<c:url value="/facebook/friends/${fuser.id}/wall"/>">Wall</a>
				<a href="<c:url value="/facebook/friends/${fuser.id}/albums"/>">Albums</a>
			</li>
		</ul>  
	</div>
</div>
