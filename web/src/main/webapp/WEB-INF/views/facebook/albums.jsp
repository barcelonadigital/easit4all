<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<c:choose>
 <c:when test="${not empty fuser}">
 	<c:set value="${falbums}" var="albums"/>
 	<c:set value="/facebook/friends/${fuser.id}/albums" var="albumsUrl" />
 	</c:when>
 	<c:otherwise><c:set value="/facebook/albums" var="albumsUrl" /></c:otherwise>
 </c:choose>
 
<div id="littleFriendHeader">
	<span class="subtitle"><strong>Photo albums</strong></span>
	<a href="<c:url value="${albumsUrl}/videos"/>" class="ym-button">See videos</a>
		<c:if test="${empty fuser}">
		<a href="<c:url value="${albumsUrl}/upload"/>" class="ym-button">Upload a photo</a>
	</c:if>
</div>

 <div class="feed">
 	<div id="pagination">
		<div class="ym-grid">
			<div class="ym-hlist">
				<div id="pagination">
					<c:choose>
						<c:when test="${not empty fuser}">
							<c:set var="url" value="friends/${fuser.id}/albums?" scope="request"/>
						</c:when>
						<c:otherwise>
							<c:set var="url" value="albums?" scope="request"/>
						</c:otherwise>
					</c:choose>
					<tiles:insertTemplate template="pagination.jsp"/>
				</div> 
			</div>
		</div>
	</div>
	<ul class="imagedList">
		<c:forEach items="${albums}" var="album">
			<li class="imagedItem">
				<div class="image">	
					<a href="<c:url value="${albumsUrl}/${album.id}"/>">
						<img src="https://graph.facebook.com/${album.coverPhotoId}/picture?type=album" align="left"/>
					</a>
				</div>
				<div class="content">
					<c:choose>
						<c:when test="${not empty album.name}">
							<strong><a href="<c:url value="${albumsUrl}/${album.id}"/>"><c:out value="${album.name}"/></a></strong> <br />
						</c:when>
						<c:otherwise>
							<strong><a href="<c:url value="${albumsUrl}/${album.id}"/>">Photo album</a></strong> <br />
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${not empty album.description}">
							<c:out value="${album.description}"/>  <br />
						</c:when>
						<c:otherwise>
							No description  <br />
						</c:otherwise>
					</c:choose>
					<span class="postTime"><c:out value="${album.updatedTime}"/></span>  <br />
				</div>
			</li>
		</c:forEach>
	</ul>
</div>

