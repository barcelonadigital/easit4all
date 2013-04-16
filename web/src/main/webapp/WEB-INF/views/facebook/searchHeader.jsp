<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="littleFriendHeader">
	<h4>Search results for "${param.query}" &#x25B6; 
		<c:choose>
			<c:when test="${searchfilter eq 'users'}">Users</c:when>
			<c:when test="${searchfilter eq 'groups'}">Groups</c:when>
			<c:when test="${searchfilter eq 'events'}">Events</c:when>
			<c:when test="${searchfilter eq 'myfeed'}">My feed</c:when>
			<c:when test="${searchfilter eq 'mywall'}">My wall</c:when>
			<c:otherwise>General</c:otherwise>
		</c:choose>
	
		<!-- Inside 'Choose':
			 Errors reading 'comments' on types LinkPost, StatusPost...
			 ...when test="${searchfilter eq 'publicfeed'}">Public feed
				<c:set var="feed" value="${publicfeed}" scope="request"/>
			...c:when...	-->
	</h4>
</div>
