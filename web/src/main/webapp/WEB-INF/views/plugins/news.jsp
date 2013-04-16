<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript">
	$(document).ready(function(){
		getFacebookNews();
		getTwitterNews();
	});
	function getFacebookNews() {
		$.get('newsPlugin', 
			{provider: "facebook"},
			function(fb) {
			$('#fb_news').html('<li><a href="<c:url value="/facebook/feed"/>">' + fb.fb_events + ' new <b>videos</b> of your friends.</a></li>' + 
			'<li><a href="<c:url value="/facebook/feed"/>">' + fb.fb_photos + ' new <b>photos</b> of your friends.</a></li>' +
			'<li><a href="<c:url value="/facebook/feed"/>">' + fb.fb_status + ' new <b>posts</b> of your friends.</a></li>' +
			'<li><a href="<c:url value="/facebook/notifications"/>">' + fb.fb_nots + ' new <b>friendship</b> requests.</a></li>' +
			'<li><a href="<c:url value="/facebook/events"/>">' + fb.fb_events + ' <b>events</b> have been updated.</a></li>');
		})
	}
	function getTwitterNews() {
		$.get('newsPlugin', 
			{provider: "twitter"},
			function(tw) {
			$('#tw_news').html('<li><a href="<c:url value="/twitter/timeline"/>">' + tw.tw_timeline + ' new <b>tweets</b> of your friends.</a></li>' +
			'<li><a href="<c:url value="/twitter/timeline/Mentions"/>">' + tw.tw_mentions + ' new <b>mentions</b> about you.</a></li>' +
			'<li><a href="<c:url value="/twitter/messages"/>">' + tw.tw_messages + ' new <b>private</b> messages.</a></li>' +
			'<li><a href="<c:url value="/twitter/followers"/>">' + tw.tw_requests + ' new <b>friendship</b> requests.</a></li>');
		})
	}
</script>


<c:if test="${newsLoaded}">
	<div class="ym-grid">
		 <div class="ym-grid ym-equalize">
				<div class="ym-g33 ym-gl">
					<div class="ym-gbox ym-clearfix">			
						<div id="topSites">
						<h4>
							<a href="<c:url value="/twitter"/>" title="Go to Twitter">
								<img class ="img-rounded" src="<c:url value="/resources/social/twitter/t_logo-a.png"/>" width="36" height="36"/> 
								Twitter 
							</a>
						</h4>
						<c:url var="twitterUrl" value="/twitter" />
						<div class="ym-vlist">
							<ul class="menu">
								<c:choose>
									<c:when test="${connectedToTwitter}">
										<div id="tw_news">				
												<img src="<c:url value="/resources/img/load.gif"/>" alt="Loading Twitter news" width="38" height="38" style="display: block; margin: 1em auto;"/>
											</div>
									</c:when>
									<c:otherwise>
										<li><a href="<c:url value="/connect/twitter"/>">Click here to connect</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="ym-g33">
				<div class="ym-gbox ym-clearfix">
						<div id="topSites">
							<h4>
								<a href="<c:url value="/facebook"/>" title="Go to Facebook">
									<img class ="img-rounded" src="<c:url value="/resources/social/facebook/f_logo.jpg"/>" width="36" height="36"/> 
									Facebook
								</a> 
							</h4>
							<c:url var="facebookUrl" value="/facebook" />
							<div class="ym-vlist">
								<ul class="menu">
									<c:choose>
										<c:when test="${connectedToFacebook}">
											<div id="fb_news">				
												<img src="<c:url value="/resources/img/load.gif"/>" alt="Loading Facebook news" width="38" height="38" style="display: block; margin: 1em auto;"/>
											</div>
										</c:when>
										<c:otherwise>
											<li><a href="<c:url value="/connect/facebook"/>">Click here to connect</a></li>
										</c:otherwise>
									</c:choose>
								</ul>
							</div>
					</div>
				</div>
			</div>
			<div class="ym-g33 ym-gr">
				<div class="ym-gbox ym-clearfix">
					<div id="topSites">
						<h4><img class ="img-rounded" src="<c:url value="/resources/img/config.png"/>" width="36" height="36"/> Configuration </h4>
						<div class="ym-vlist">
							<ul class="menu favs">
								<li><a href="<c:url value="/connect"/>">Connections</a></li>
								<li><a href="<c:url value="/user/applicationPreferences"/>">Preferences</a></li>
								<li><a href="<c:url value="/user/account"/>">Account</a></li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>	
		<tiles:insertTemplate template="favouritesHome.jsp"/>
	</div>

</c:if>