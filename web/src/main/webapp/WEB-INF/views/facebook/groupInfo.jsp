<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<div class="miniFeed">

	<c:if test="${not empty group.privacy}">
		<p>
			<c:set var="string" value="${group.privacy}"/>	
			<c:out value="${fn:toLowerCase(string)}"/> group
		</p>
	</c:if>
	<p>
		<c:out value="${group.description}"/>
	</p>
</div>