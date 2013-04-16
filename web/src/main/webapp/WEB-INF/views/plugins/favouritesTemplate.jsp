<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:forEach items="${favourites}" var="fav" >
	<c:choose>
		<c:when test="${empty fav.operation}"><c:set var="operation" value="" /></c:when>
		<c:otherwise><c:set var="operation" value="/${fav.operation}" /></c:otherwise>
	</c:choose>
	<li>
	<a href="<c:url value="/${fav.providerId}${operation}"/>">
	<c:choose>
		<c:when test="${fav.providerId eq 'twitter'}">
			<img src="<c:url value="/resources/social/twitter/t_logo-a.png"/>" width="18" height="18" style="appearance: icon;" />
		</c:when>
		<c:when test="${fav.providerId eq 'facebook'}">
			<img src="<c:url value="/resources/social/facebook/f_logo.jpg"/>" width="18" height="18"  style="appearance: icon;"/>
		</c:when>
	<c:otherwise/>
	</c:choose>
	<c:choose>
		<c:when test="${empty fav.operation}">user profile</c:when>
		<c:otherwise>${fav.operation}</c:otherwise>
	</c:choose>
	</a>
	</li>
</c:forEach>