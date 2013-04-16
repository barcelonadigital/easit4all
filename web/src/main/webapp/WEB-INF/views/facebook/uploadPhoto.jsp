<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:set value="/facebook/albums" var="albumsUrl" />
<c:choose>
	<c:when test="${album eq null}">
		<c:set value="/facebook/albums/upload/${album}" var="upload" />
	</c:when>
	<c:otherwise>
		<c:set value="/facebook/albums/upload" var="upload" />
	</c:otherwise>
</c:choose>

<div id="littleFriendHeader">
	<span class="subtitle"><strong>
		<a href="<c:url value="${albumsUrl}"/>">Photo albums</a> 
		<c:if test="${not empty album}">
			&#x25B6; <a href="<c:url value="${albumsUrl}/${album}"/>">${albumName}</a> 
		</c:if>
			&#x25B6; Upload photo
	</strong></span>
	<c:if test="${empty album}">
		<a href="<c:url value="${albumsUrl}/videos"/>" class="ym-button">See videos</a>
	</c:if>
</div>

<tiles:insertTemplate template="PhotoUploadForm.jsp" />