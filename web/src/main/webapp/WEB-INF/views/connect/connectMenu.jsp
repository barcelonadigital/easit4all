<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:url var="connect" value="/connect" />
<c:url var="fbConnect" value="/facebook/search" />

<div class="ym-wrapper">
	<nav class="service-menu" id="service-menu">
		<div class="ym-hlist">
			<ul>
				<li><a href="<c:out value="${connect}"/>" class="connect">Overview</a></li>
				<li><a href="<c:out value="${connect}"/>/facebook" class="facebook connect">Facebook</a></li>
				<li><a href="<c:out value="${connect}"/>/twitter" class="twitter connect">Twitter</a></li>
			</ul>
		</div>
	</nav>
</div>
