<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<c:url var="userUrl" value="/user" />

<div class="ym-wrapper">
	<nav class="service-menu" id="service-menu">
		<div class="ym-hlist">
			<ul>
				<li><a href="<c:out value="${userUrl}"/>/account" title="user account">Account</a></li>
				<li><a href="<c:out value="${userUrl}"/>/profile" title="user profile">Profile</a></li>
				<li><a href="<c:out value="${userUrl}"/>/applicationPreferences" title="application preferences">Application Preferences</a></li>
			<!--<li><a href="<c:out value="${userUrl}"/>/plugins" title="plugins">Plug-ins</a></li>  -->
			</ul>
		</div>
	</nav>
</div>
