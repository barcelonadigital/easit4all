<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

 <c:if test="${favouritesLoaded}">
	<c:if test="${not empty favourites}">
		<li><span>Favourites</span>
			<ul>
				<tiles:insertTemplate template="favouritesTemplate.jsp"/>
			</ul>
		</li>
	</c:if>
</c:if>