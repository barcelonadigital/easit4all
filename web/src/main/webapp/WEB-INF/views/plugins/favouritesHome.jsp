<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

 <c:if test="${favouritesLoaded}">
	<c:if test="${not empty favourites}">
		<div class="ym-g100">
			<div class="ym-gbox">
				<div id="topSites">
					<h4><img class ="img-rounded" src="<c:url value="/resources/img/favourite.png"/>" width="36" height="36"/> Your favourite operations </h4>
					<div class="ym-hlist">
						<ul class="menu">
						<tiles:insertTemplate template="favouritesTemplate.jsp"/>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</c:if>
</c:if>