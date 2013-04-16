<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:choose>
	<c:when test="${not empty event}">
		<tiles:insertTemplate template="eventHeader.jsp" />	
		<tiles:insertTemplate template="eventInfo.jsp" />
	</c:when>
	<c:when test="${not empty group}">
		<tiles:insertTemplate template="groupHeader.jsp" />	
		<tiles:insertTemplate template="groupInfo.jsp" />
	</c:when>
	<c:when test="${searchfilter eq 'general'}">
		<tiles:insertTemplate template="searchHeader.jsp" />
	</c:when>
	<c:otherwise>
		<tiles:insertTemplate template="friendHeader.jsp" />
	</c:otherwise>
</c:choose>

<div class="feed">
	<tiles:insertAttribute name="facebook.content.fe"/>
</div>