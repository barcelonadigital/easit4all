<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div id="littleFriendHeader">
	<h4>${group.name}</h4>
</div>
<div id="rightNav">
	<div class="ym-vlist">
		<ul>
			<li>
				<a href="<c:url value="/facebook/groups/${group.id}/wall"/>">Wall</a>
				<a href="<c:url value="/facebook/groups/${group.id}/members"/>">Members</a>
			</li>
		</ul>  
	</div>
</div>

