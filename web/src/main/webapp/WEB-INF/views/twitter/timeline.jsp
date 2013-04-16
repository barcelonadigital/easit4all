<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script src="http://platform.twitter.com/anywhere.js?id=7yWLgCOuQhIpPyffm0o2Vg&v=1" type="text/javascript"></script>
<script type="text/javascript">
  twttr.anywhere(function (T) {
    T(".feed").linkifyUsers();
  });   
  
</script>

<c:url var="tweetUrl" value="/twitter/tweet" />
<c:url var="timelineBaseUrl" value="/twitter/timeline" />

<tiles:insertTemplate template="twitterMenu.jsp" />      
      
	<div id="postToTt">
		<div class="grid">
			<div class="ym-g75 ym-gl">
				<div class="ym-gbox-left ym-clearfix">
					<form:form method="POST" class="ym-form" action="${tweetUrl}" enctype="multipart/form-data" modelAttribute="uploadItem">
					<div class="ym-g80 ym-gl">
						<div class="ym-gbox">
							<div class="ym-fbox-text"> 
									<form:label for="caption" path="caption">Post a tweet (140 characters max)</form:label>
									<form:textarea  path="caption" maxlength="140" onkeyup="textAreaAdjust(this)" style="overflow:hidden; resize: none;" placeholder="Write something..."></form:textarea>
							</div>
							<div class="ym-fbox-text">
									<form:label for="fileData" path="fileData" >Add a photo (optional)</form:label>
	       	 						<form:input path="fileData" type="file" />
							</div>
						</div>
					</div>
					<div class="ym-g20 ym-gr">
						<div class="ym-gbox">
							<input class="ym-button" type="submit" value="Post"/>
						</div>
					</div>	
					</form:form>
				</div>
			</div>
			<div class="ym-g25 ym-gr">
				<!-- <div class="ym-gbox ym-clearfix">
					<a href="<c:url value="/twitter/timeline" />" class="ym-button">Reload timeline</a> 
				</div>  -->
				<div class="ym-gbox-right ym-clearfix">
					<div class="ym-vlist submenu">
						<ul>
							<li>
								<a href="<c:out value="${timelineBaseUrl}"/>/Home">Home Timeline</a>
								<a href="<c:out value="${timelineBaseUrl}"/>/User">User Timeline</a>
								<!-- <a href="<c:out value="${timelineBaseUrl}"/>/Public">Public Timeline</a>  -->
								<a href="<c:out value="${timelineBaseUrl}"/>/Mentions">Mentions</a>
								<a href="<c:out value="${timelineBaseUrl}"/>/Favorites">Favorites</a>
							</li>
						</ul>
					</div>
					
				</div>
			</div>
		</div>
	</div>      
	
	<div class="feed" id="feed">
		<div class="ym-grid">
		<div id="content-section-service">
			<div class="ym-hlist">
				<span class="subtitle"><strong><c:out value="${timelineName}"/> Timeline</strong></span>
				<div id="pagination">
					<span class = "pagination-list">
						<c:choose>
							<c:when test="${fn:contains(currentSection,'timeline')}">
								<c:choose>
									<c:when test="${pageSize eq 0}">
										0
									</c:when>
									<c:otherwise>
										<c:out value="${(offset-1)*TWITTER_LIMIT_RESULT+1}"/>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${pageSize + TWITTER_LIMIT_RESULT lt offset*TWITTER_LIMIT_RESULT}">
											- <c:out value="${offset*TWITTER_LIMIT_RESULT}"/> of <c:out value="${pageSize}"/>
									</c:when>
									<c:otherwise>
											- <c:out value="${pageSize}"/> of <c:out value="${pageSize}"/>
									</c:otherwise>
							</c:choose>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${pageSize eq 0}">
										0
									</c:when>
									<c:otherwise>
										<c:out value="${offset+1}"/>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${offset + TWITTER_LIMIT_RESULT le pageSize}">
										- <c:out value="${offset+TWITTER_LIMIT_RESULT}"/> of <c:out value="${pageSize}"/>
									</c:when>
									<c:otherwise>
										- <c:out value="${pageSize}"/> of <c:out value="${pageSize}"/>
									</c:otherwise>
								</c:choose>
							</c:otherwise>
						</c:choose>
					</span>
					<c:choose>
						<c:when test="${fn:contains(currentSection,'timeline')}">
							<c:choose>
								<c:when test="${offset ne 1}">
									<a class="ym-button pagination-previous" href="<c:out value="${timelineBaseUrl}"/>/${timelineName}?offset=${offset - 1}" >&#9668;</a>
								</c:when>
								<c:otherwise>
									<a class="ym-button ym-disabled pagination-previous">&#9668;</a>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${pageSize + TWITTER_LIMIT_RESULT lt offset*TWITTER_LIMIT_RESULT}">
									<a class="ym-button pagination-next" href="<c:out value="${timelineBaseUrl}"/>/${timelineName}?offset=${offset + 1}" >&#9658;</a>
								</c:when>
								<c:otherwise>
									<a class="ym-button ym-disabled pagination-next">&#9658;</a>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${offset ne 0}">
									<a class="ym-button pagination-previous" href="search?query=${param.query}&offset=${offset-TWITTER_LIMIT_RESULT}">&#9668;</a>
								</c:when>
								<c:otherwise>
									<a class="ym-button ym-disabled pagination-previous">&#9668;</a>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${offset + TWITTER_LIMIT_RESULT le pageSize}">
									<a class="ym-button pagination-next" href="search?query=${param.query}&offset=${offset+TWITTER_LIMIT_RESULT}">&#9658;</a>	
								</c:when>
								<c:otherwise>
									<a class="ym-button ym-disabled pagination-next">&#9658;</a>	
								</c:otherwise>	
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
		<ul class="imagedList">
			<c:forEach items="${timeline}" var="tweet">
				<li class="imagedItem">
					<div class="image">
						<c:if test="${not empty tweet.profileImageUrl}"><img src="<c:out value="${tweet.profileImageUrl}"/>" align="left"/></c:if>
					</div>
					<div class="content">
					<strong><a href="http://twitter.com/<c:out value="${tweet.fromUser}" />"><c:out value="${tweet.fromUser}" /></a></strong><br/>
					<c:out value="${tweet.text}" /><br/>
					<span class="postTime"><c:out value="${tweet.createdAt}"/></span>
					</div>
				</li>
			</c:forEach>
		</ul>
		
		<c:if test="${empty timeline}">
			No tweets found in ${timelineName} timeline.
		</c:if>
				</div>
	</div>
</div>