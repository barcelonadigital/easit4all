<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:set value="/facebook/albums" var="albumsUrl"/>
 <c:if test="${not empty fuser}">
 	<c:set value="${fvideo}" var="video"/>
</c:if>

<script src="<c:url value="/resources/galleria/galleria-1.2.7.min.js" />" type="text/javascript"></script>
<script id="photogal" src="<c:url value="/resources/galleria/themes/classic/galleria.classic.min.js"/>" type="application/javascript"></script>

<div id="littleFriendHeader">
	<span class="subtitle"><strong>
		<c:choose>
			<c:when test="${empty fuser}">
				<a href="<c:url value="${albumsUrl}/videos/"/>">Videos</a> &#x25B6;
			</c:when>
			<c:otherwise>
				<a href="<c:url value="/facebook/friends/${fuser.id}/albums/videos"/>">Videos</a> &#x25B6;
			</c:otherwise>
		</c:choose>
			
		<c:choose>
			<c:when test="${not empty video.name}"><c:out value="${video.name}"/></c:when>
			<c:otherwise>Video</c:otherwise>
		</c:choose>
	</strong></span> 
</h4>
</div>


<!-- USING EMBED HTML FROM FB -->
<!--  <br /> From <c:out value="${video.from.name}"/> <br/>  -->
<!-- <div id="albumGalleria" style="margin-left: 100px; width: 600px;"></div> -->

<div class="feed">
	${video.embedHtml}
	<!--<c:if test="${not empty comments}">
		<c:forEach items="${comments}" var="comment">
  			<tiles:insertTemplate template="comments.jsp"/>
  		</c:forEach>
  	</c:if>-->
</div>
 <!-- USING HTML5 -->
<!--  <video id="audioVideo" width="400" height="224" controls="controls" volume="">
 	<source src="${video.source}" type="application/x-shockwave-flash"></source>
 	
</video>
  -->