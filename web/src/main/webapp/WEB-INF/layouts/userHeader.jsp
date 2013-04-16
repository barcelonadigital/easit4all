<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>

<c:if test="${connectedToAny}">
	<p align="right">Welcome to ASIT services, <c:out value="${user.firstName}"/>!</p>
</c:if>
