<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<tiles:insertTemplate template="twitterMenu.jsp" />
<div class="feed">
	<div class="ym-grid">
		<div class="ym-hlist">
			<div id="pagination">
				<span class = "pagination-list">
					<c:choose>
						<c:when test="${pageSize eq 0}">
							0
						</c:when>
						<c:otherwise>
							<c:out value="${(offset)*TWITTER_LIMIT_RESULT+1}"/>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${offset + TWITTER_LIMIT_RESULT le pageSize}">
							- <c:out value="${(offset+1)*TWITTER_LIMIT_RESULT}"/> of <c:out value="${pageSize}"/>
						</c:when>
						<c:otherwise>
							- <c:out value="${pageSize}"/> of <c:out value="${pageSize}"/>
						</c:otherwise>
					</c:choose>
				</span>
				<c:choose>
					<c:when test="${offset ne 0}">
						<a class="ym-button pagination-previous" href="?offset=${offset-TWITTER_LIMIT_RESULT}">&#9668;</a>
					</c:when>
					<c:otherwise>
						<a class="ym-button pagination-previous ym-disabled">&#9668;</a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${offset + TWITTER_LIMIT_RESULT le pageSize}">
						<a class="ym-button pagination-next" href="?offset=${offset+TWITTER_LIMIT_RESULT}" >&#9658;</a>	
					</c:when>	
					<c:otherwise>
						<a class="ym-button pagination-next ym-disabled">&#9658;</a>	
					</c:otherwise>
				</c:choose>
			</div> 
		</div>
	</div>
	<div class="ym-vlist">
		<ul>
			<c:forEach items="${trends.trends}" var="trend">
				<li>
					<s:url value="/twitter/search" var="searchUrl">
						<s:param name="query"><c:out value="${trend.query}" escapeXml="false"/></s:param>
					</s:url>
					<a href="${searchUrl}"><c:out value="${trend.query}" /></a>
				</li>
			</c:forEach>
		</ul>
	</div>
</div>
 
