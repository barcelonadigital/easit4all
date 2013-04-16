<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<!-- search results -->
<c:choose>
	<c:when test="${not empty searchfilter}">
		<c:set var="url" value="groups?query=${param.query}&" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="url" value="groups?" scope="request"/>
	</c:otherwise>
</c:choose>
<!-- endof search results -->


<div class="feed">
	<div class="ym-grid">
		<div class="ym-hlist">
			<div id="pagination">
				<tiles:insertTemplate template="pagination.jsp"/>
			</div> 
		</div>
	</div>
	<tiles:insertTemplate template="groupsList.jsp"/>
</div>
