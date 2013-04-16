<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

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
		<a class="ym-button pagination-previous" href="${url}offset=${offset-FACEBOOK_LIMIT_RESULT}&size=${pageSize}&ro=${offset-FACEBOOK_LIMIT_RESULT}">&#9668;</a>
	</c:when>
	<c:otherwise>
		<a class="ym-button pagination-previous ym-disabled">&#9668;</a>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${offset + FACEBOOK_LIMIT_RESULT le pageSize}">
		<a class="ym-button pagination-next" href="${url}offset=${offset+FACEBOOK_LIMIT_RESULT}&size=${pageSize}&ro=${realOffset}" >&#9658;</a>	
	</c:when>	
	<c:otherwise>
		<a class="ym-button pagination-next ym-disabled">&#9658;</a>	
	</c:otherwise>
</c:choose>