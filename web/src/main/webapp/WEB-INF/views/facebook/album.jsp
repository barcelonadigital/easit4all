<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script src="<c:url value="/resources/galleria/galleria-1.2.7.min.js" />" type="text/javascript"></script>
<script id="photogal" src="<c:url value="/resources/galleria/themes/classic/galleria.classic.min.js"/>" type="application/javascript"></script>
<c:set value="/facebook/albums" var="albumsUrl"/>
 <c:if test="${not empty fuser}">
 	<c:set value="${fphotos}" var="photos"/>
</c:if>

<div id="littleFriendHeader">
	<span class="subtitle"><strong>
		<c:choose>
			<c:when test="${empty fuser}">
				<a href="<c:url value="${albumsUrl}"/>">Photo albums</a> &#x25B6; <c:out value="${album.name}"/>
				</strong></span>
				<a href="<c:url value="${albumsUrl}/upload?id=${album.id}&name=${album.name}"/>" class="ym-button">Upload a photo</a>
			</c:when>
			<c:otherwise>
				<a href="<c:url value="/facebook/friends/${fuser.id}/albums"/>">Photo albums</a> &#x25B6; 
				<c:out value="${falbum.name}"/>
				</strong></span>
			</c:otherwise>
		</c:choose>
	<c:if test="${empty fuser}">
		
	</c:if>
</div>

<div id="albumGalleria">
	<div id="galleria"> 
			<c:forEach items="${photos}" var="photo" varStatus="albumStatus">	
					<c:set var="tags" value=""/>
					<c:forEach items="${photo.tags}" var="tag">
						<c:set var="tags" value=" ${tags} ${tag.name},"/>
					</c:forEach>
					<a href="${photo.sourceImage.source}"><img src="${photo.tinyImage.source}" data-title="${photo.name}" data-big="${photo.oversizedImage.source}" data-description="${fn:substring(tags, 0, fn:length(tags)-1)}"/></a>
			</c:forEach>
	</div>
	<script type="application/javascript" >
	    if (Galleria) {   Galleria.loadTheme('#photogal'); 
	   					  Galleria.run('#galleria', {
	   						  height: 0.5625,
	   						  
	   					  });
	    				}
	</script>
</div>
<!-- 
TO DO: COMMENTS FOR EACH PHOTO
<div class="feed">
	<c:forEach items="${comments}" var="comment">
		<tiles:insertTemplate template="comments.jsp"/>
	</c:forEach>	
</div> -->