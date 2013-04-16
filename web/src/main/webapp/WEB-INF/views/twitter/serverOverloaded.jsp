<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="false" %>

<tiles:insertTemplate template="twitterMenu.jsp" />
	
<div class="formInfo">
	<div class="error">
 		Twitter is overloaded with requests. Try again later.
 	</div>
</div>