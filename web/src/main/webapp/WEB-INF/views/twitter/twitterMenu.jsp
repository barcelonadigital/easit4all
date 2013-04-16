<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>

<c:url var="twitterUrl" value="/twitter" />
<c:url var="searchUrl" value="/twitter/search" />

<div class="ym-wrapper">
	<nav class="service-header">
			<div class="ym-grid">
				<div class="ym-hlist">
					<img alt="Twitter" src="<c:url value="/resources/social/twitter/twitter-word.jpg"/>" height="60px" />
					<form class="ym-searchform" action="${searchUrl}" method="get">
						 <input class="ym-searchfield" type="search" placeholder="Search in Twitter..." name="query" value="<c:out value="${param.query}"/>" style="display:inline;" required/>
						 <input class="ym-searchbutton" type="submit" value="Search" style="display:inline;"/>
					</form>
				</div>
			</div>
	</nav>
	<nav class="service-menu" id="service-menu">
		<div class="ym-hlist">
			<ul>
				<li><a href="<c:out value="${twitterUrl}"/>/timeline" class="timeline">Timeline</a></li>
				<li><a href="<c:out value="${twitterUrl}"/>/messages" class="direct_messages">Direct Messages</a></li>
				<li><a href="<c:out value="${twitterUrl}"/>/friends" class="t_friends">Following</a></li>
				<li><a href="<c:out value="${twitterUrl}"/>/followers" class="followers">Followers</a></li>
				<li><a href="<c:out value="${twitterUrl}"/>/dailyTrends" class="dailyTrends">Daily Trends</a></li>
			</ul>
		</div>
	</nav>
</div>
