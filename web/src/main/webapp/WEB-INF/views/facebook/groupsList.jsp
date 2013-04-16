<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="fbGroups">
	<ul class="imagedList">
	<c:forEach items="${groups}" var="group" varStatus="status">
			<li class="imagedItem">
				<div class="image">
					<img src="http://graph.facebook.com/<c:out value="${group.id}"/>/picture" align="left"/>
				</div>
				<c:if test="${empty searchfilter}">
					<div class="icons">
					<!-- INVALID PERMISSION "MANAGE_GROUPS" -->
							<form name ="deleteGroup" action="<c:url value="/facebook/${group.id}/members/${userId}"/>" method = "POST">
				 				<input type="hidden" name="uri" value="${uri}"/>
				 				<a href="#" onclick="parentNode.submit();" alt="Delete" title="Delete">&#10005</a>
				 			</form>
				 	<!-- ENDOF -->
					</div>
				</c:if>
				<div class="content">
					<strong><a href="<c:url value="/facebook/groups/${group.id}/wall"/>"><c:out value="${group.name}"/></a></strong><br/>
					<c:if test="${not empty group.privacy}">
							<c:set var="string" value="${group.privacy}"/>	
							<c:out value="${fn:toLowerCase(string)}"/> group  <br />
							<c:if test="${group.privacy eq 'OPEN'}"> 
								<c:out value="${nMembers[status.index]}"/> members  <br />
							</c:if>
					</c:if> 
				</div>
			</li>
	</c:forEach>
	</ul>


<c:if test="${not empty searchfilter}">
	<c:if test="${empty groups}">No results found for your search criteria</c:if>
</c:if>
</div>