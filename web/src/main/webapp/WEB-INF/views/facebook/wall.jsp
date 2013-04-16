<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>



<tiles:insertTemplate template="postToWall.jsp"/>
<div class="feed">
	<div class="ym-grid">
		<div class="ym-hlist">
			<div id="pagination">
				<c:set var="url" value="wall?" scope="request"/>
				<tiles:insertTemplate template="pagination.jsp"/>
			</div> 
		</div>
	</div>
	 <tiles:insertTemplate template="feed.jsp"/> 
</div>