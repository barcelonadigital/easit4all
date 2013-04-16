<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:choose>
 	<c:when test="${not empty fuser}">
 		<c:set value="${fvideos}" var="videos"/>
 		<c:set value="/facebook/friends/${fuser.id}/albums" var="albumsUrl" />
 	</c:when>
 	<c:otherwise><c:set value="/facebook/albums" var="albumsUrl" /></c:otherwise>
 </c:choose>

<!-- Watch the videos you are tagged in. -->

<div id="littleFriendHeader">
	<span class="subtitle"><strong>Videos</strong></span>
	<a href="<c:url value="${albumsUrl}"/>" class="ym-button">Back to photo albums</a>
	<c:if test="${empty fuser}">
		<a href="<c:url value="${albumsUrl}/videos/upload"/>" class="ym-button">Upload a video</a>
	</c:if>
</div>

<div class="feed">
	<div id="pagination">
		<div class="ym-grid">
			<div class="ym-hlist">
				<div id="pagination">
					<c:choose>
						<c:when test="${not empty fuser}">
							<c:set var="url" value="friends/${fuser.id}/albums/videos?" scope="request"/>
						</c:when>
						<c:otherwise>
							<c:set var="url" value="albums/videos?" scope="request"/>
						</c:otherwise>
					</c:choose>
					<tiles:insertTemplate template="pagination.jsp"/>
				</div> 
			</div>
		</div>
	</div>
	<ul class="videoImagedList">
		<c:forEach items="${videos}" var="video">
			<li class="videoImagedItem">
				<div class="image">	
					<a href="<c:url value="${albumsUrl}/videos/${video.id}"/>">
						<img src="${video.picture}"/>
					</a>
				</div>
				<div class="content">
					<c:choose>
						<c:when test="${not empty video.name}">
							<strong><a href="<c:url value="${albumsUrl}/videos/${video.id}"/>"> <c:out value="${video.name}"/></a></strong>  <br />
						</c:when>
						<c:otherwise>
							<strong><a href="<c:url value="${albumsUrl}/videos/${video.id}"/>">Video</a></strong> <br />
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty video.description}">
							<c:out value="${video.description}"/>  <br />
						</c:when>
						<c:otherwise>
							No description  <br />
						</c:otherwise>
					</c:choose>
					<span class="postTime"><c:out value="${video.createdTime}"/></span>  <br />
					<c:if test="${not empty video.tags}">
						<div class="tagContent">
							<c:forEach items="${video.tags}" var="tag"><a href="<c:url value="/facebook/friends/${tag.id}/wall"/>"><c:out value="${tag.name}"/></a>&nbsp; &nbsp;</c:forEach>
						</div>
						</c:if>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>

