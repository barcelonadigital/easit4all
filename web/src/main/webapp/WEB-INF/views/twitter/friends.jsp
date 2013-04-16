<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script src="http://platform.twitter.com/anywhere.js?id=7yWLgCOuQhIpPyffm0o2Vg&v=1" type="text/javascript"></script>
<script type="text/javascript">
  twttr.anywhere(function (T) {
    T(".feed").linkifyUsers();
  });    
</script>

<tiles:insertTemplate template="twitterMenu.jsp" />

<br  />

<div class="feed" id="twFriends">
	<div class="ym-grid">
		<div class="ym-hlist">
			<div id="pagination">
				<span class="pagination-list">
					<c:choose>
						<c:when test="${pageSize eq 0}">
							0
						</c:when>
						<c:otherwise>
							<c:out value="${(offset)*TWITTER_LIMIT_RESULT+1}"/>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${offset + TWITTER_LIMIT_RESULT le length}">
							- <c:out value="${(offset+1)*TWITTER_LIMIT_RESULT}"/> of <c:out value="${pageSize}"/>
						</c:when>
						<c:otherwise>
							- <c:out value="${pageSize}"/> of <c:out value="${pageSize}"/>
						</c:otherwise>
					</c:choose>
				</span>
				<c:choose>
					<c:when test="${offset ne 0}">
						<a class="pagination-previous ym-button" href="?offset=${offset-TWITTER_LIMIT_RESULT}" >&#9668;</a>
					</c:when>
					<c:otherwise>
						<a class="pagination-previous ym-button ym-disabled">&#9668;</a>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${offset + TWITTER_LIMIT_RESULT le length}">
						<a class="pagination-next ym-button" href="?offset=${offset+TWITTER_LIMIT_RESULT}">&#9658;</a>	
					</c:when>
					<c:otherwise>
						<a class="pagination-next ym-button ym-disabled">&#9658;</a>	
					</c:otherwise>
				</c:choose>	
			</div>
		</div>
	</div>
	<ul class="imagedList">
		<c:forEach items="${profiles}" var="profile" varStatus="status">
			<li class="imagedItem">
				<div class="image">
					<c:if test="${not empty profile.profileImageUrl}"><img src="<c:out value="${profile.profileImageUrl}"/>" width="48" height="48" align="left"/></c:if>
				</div>
				<div class="content">
					<div class="grid">
						<div class="ym-g65 ym-gl">
							<div class="ym-gbox ym-clearfix">
								<p><a href="http://twitter.com/<c:out value="${profile.screenName}" />"><c:out value="${profile.screenName}"/></a></p>
							</div>
						</div>
						<div class="ym-g35 ym-gr">
							<div class="ym-gbox ym-clearfix">
								<form name ="follow" action="<c:url value="/twitter/follow/${profile.screenName}"/>" method = "POST">
									<c:choose>
										<c:when test="${friendship[status.index]}">
						 					<button type="submit" class="ym-button tw-unfollow">Unfollow</button>
						 				</c:when>
						 				<c:otherwise>
						 					<button type="submit" class="ym-button tw-follow">Follow</button>
						 				</c:otherwise>
									</c:choose>
								</form>
							</div>
						</div>
					</div>
				</div>
			</li>
		</c:forEach>
	</ul>
</div>
<c:if test="${empty profiles}">
	<c:choose>
		<c:when test="${fn:contains(currentSection,'friends')}">
			You are not following any user.
		</c:when>	
		<c:otherwise>
			Any user is following you.
		</c:otherwise>
	</c:choose>
</c:if>
	</c:forEach>
	</ul> -->