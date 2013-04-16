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

<tiles:insertTemplate template="twitterMenu.jsp" />

<c:url var="messageBaseUrl" value="/twitter/messages" />

<div id="messageToTt">
	<div class="grid">
		<div class="ym-g75 ym-gl">
			<div class="ym-gbox-left ym-clearfix">
				<form:form method="POST" class="ym-form" action="${messageBaseUrl}" modelAttribute="messageForm">
					<div class="ym-gbox">
						<div class="ym-fbox-text"> 
								<form:label for="to" path="to">To</form:label> 
								<form:input path="to" placeholder="Username"/>
						</div>
						<div class="ym-fbox-text">
								<form:label for="text" path="text" >Message (140 characters max)</form:label>
       	 						<form:textarea path="text" type="text" maxlength="140" onkeyup="textAreaAdjust(this)" style="overflow:hidden; resize: none;" placeholder="Write something..."/>
						</div>
					</div>
					<div class="ym-gbox">
						<div class="ym-fbox-button">
							<input class="ym-button" type="submit" value="Send Message"/>
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
				<br  /> 
				<div class="ym-vlist submenu">
					<ul>
						<li>
							<a href="<c:out value="${messageBaseUrl}"/>">Inbox</a>
							<a href="<c:out value="${messageBaseUrl}"/>/sent">Sent</a>
						</li>
					</ul>
				</div>
				
			</div>
		</div>
	</div>
</div>  

<div class="feed" id="feed">
	<div class="ym-grid">
		<div class="ym-hlist">
			<span class="subtitle"><strong><c:out value="${dmListType}" /> Messages</strong></span>
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
	<ul class="imagedList">
	<c:forEach items="${directMessages}" var="dm">
		<li class="imagedItem">
			<div class="image">
				<c:if test="${not empty dm.sender.profileImageUrl}"><img src="<c:out value="${dm.sender.profileImageUrl}"/>" align="left"/></c:if>
			</div>
			<div class="content">
			<strong><a href="http://twitter.com/<c:out value="${dm.sender.screenName}" />"><c:out value="${dm.sender.screenName}" /></a></strong><br/>
			<span class="dmRecipient">to <c:out value="${dm.recipient.screenName}"/></span><br/>
			<c:out value="${dm.text}" /><br/>
			<span class="postTime"><c:out value="${dm.createdAt}"/></span>
			</div>
		</li>
	</c:forEach>
	</ul>
	<c:if test="${empty directMessages}">
		No messages.
	</c:if>
</div>

