<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="searchHeader.jsp"/>

<div class="feed">
	<c:choose>
		<c:when test="${searchfilter eq 'users'}">
			<tiles:insertTemplate template="users.jsp" />
		</c:when>
		
		<c:when test="${searchfilter eq 'events'}">
			<tiles:insertTemplate template="events.jsp" />
		</c:when>

		<c:when test="${searchfilter eq 'groups'}">
			<tiles:insertTemplate template="groups.jsp" />
		</c:when>

		<c:when test="${searchfilter eq 'myfeed'}">
			<c:if test="${not empty feed}">not empty feeeeed!!</c:if>
			<c:set var="feed" value="${myfeed}" scope="request"/>
			<tiles:insertTemplate template="feedsearch.jsp" />
		</c:when>

		<c:when test="${searchfilter eq 'publicfeed'}">
			<c:set var="feed" value="${myfeed}" scope="request"/>
			<tiles:insertTemplate template="feedsearch.jsp" />
		</c:when>

		<c:when test="${searchfilter eq 'mywall'}">
			<c:set var="feed" value="${myfeed}" scope="request"/>
			<tiles:insertTemplate template="feedsearch.jsp" />
		</c:when>
		
	</c:choose>

</div> 