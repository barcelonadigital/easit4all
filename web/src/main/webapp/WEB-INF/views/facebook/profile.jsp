<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<c:if test="${not empty fuser}">
	<c:set var="profile" value="${fuser}" />
</c:if>
<dl>
	<dt>Name:</dt>
	<dd><c:out value="${profile.name}"/></dd>
	<dt>Gender:</dt>
	<dd><c:out value="${profile.gender}"/></dd>
	<dt>Location:</dt>
	<dd><c:out value="${profile.location}"/></dd>
	<dt>Email:</dt>
	<dd><c:out value="${profile.email}"/></dd>
	<dt>Website:</dt>
	<dd><c:out value="${profile.website}"/></dd>
	
	
</dl>

<!-- <c:url value="/connect/facebook" var="disconnectUrl"/>
<form id="disconnect" action="${disconnectUrl}" method="post">
	<button type="submit">Disconnect from Facebook</button>	
	<input type="hidden" name="_method" value="delete" />
</form> -->