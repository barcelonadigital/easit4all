<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:set value="/facebook/albums" var="albumsUrl" />

<c:set value="/facebook/albums/video/upload" var="upload" />

<div id="littleFriendHeader">
	<span class="subtitle"><strong>
		<a href="<c:url value="${albumsUrl}/videos"/>">Videos</a> 
		&#x25B6; Upload video
	</strong></span>
</div>

<tiles:insertTemplate template="VideoUploadForm.jsp" />