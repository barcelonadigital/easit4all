<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="false" %>

<tiles:insertTemplate template="connectMenu.jsp"/>
<div id="serviceConnect">
	<c:forEach var="providerId" items="${providerIds}">
		<c:set var="connections" value="${connectionMap[providerId]}" />
		<s:message code="${providerId}.displayName" var="providerDisplayName" />
		<!-- test in order to not display google or gmail status in C4 interface -->
		<c:if test="${providerDisplayName ne 'Google'}">
			<c:if test="${providerDisplayName ne 'Gmail'}">
				<div class="accountConnection">
					<s:message code="${providerId}.icon" var="iconUrl"/>
					<h4><img src="<c:url value="${iconUrl}" />" width="36" height="36" /> ${providerDisplayName}</h4>
					
					<p>
					<c:if test="${not empty connections}">
						You are connected to ${providerDisplayName} as ${connections[0].displayName}.
					</c:if>
					<c:if test="${empty connections}">
						You are not yet connected to ${providerDisplayName}.
					</c:if>
					Click <a href="<c:url value="/connect/${providerId}" />">here</a> to manage your ${providerDisplayName} connection.
					</p>
				</div>
			</c:if>
		</c:if>
	</c:forEach>
</div>