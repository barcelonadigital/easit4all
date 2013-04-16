<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
 <br />
<c:if test="${topSitesLoaded}">
	<!-- <table width="100%" style="align: justify; margin-left: 70px;" >
		<tr>
			<td> -->
	 <div class="ym-grid">
			<div class="ym-g50 ym-gl">
				<div class="ym-gbox">			
					<div id="topSites">
					<h4><img src="<c:url value="/resources/social/twitter/t_logo-a.png"/>" width="36" height="36"/> Twitter </h4>
					<c:url var="twitterUrl" value="/twitter" />
					<ul class="menu">
						<c:choose>
							<c:when test="${connectedToTwitter}">
								<c:forEach items="${topTwitter}" var="topTwitter" >
									<c:choose>
										<c:when test="${empty topTwitter.operation}"><c:set var="operation" value="" /></c:when>
										<c:otherwise><c:set var="operation" value="/${topTwitter.operation}" /></c:otherwise>
									</c:choose>
									<li>
									<a href="<c:url value="/${topTwitter.providerId}${operation}"/>">
									<c:choose>
										<c:when test="${empty topTwitter.operation}">user profile</c:when>
										<c:otherwise>${topTwitter.operation}</c:otherwise>
									</c:choose>
									</a>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li><a href="<c:url value="/connect/twitter"/>">Connect</a></li>
							</c:otherwise>
						</c:choose>
				</ul>
			</div>
		</div>
	</div>
	<div class="ym-g50 ym-gr">
		<div class="ym-gbox">
				<div id="topSites">
					<h4><img src="<c:url value="/resources/social/facebook/f_logo.jpg"/>" width="36" height="36"/> Facebook </h4>
					<c:url var="facebookUrl" value="/facebook" />
					<ul class="menu">
					<c:choose>
							<c:when test="${connectedToFacebook}">
								<c:forEach items="${topFacebook}" var="topFacebook" >
									<c:choose>
										<c:when test="${empty topFacebook.operation}"><c:set var="operation" value="" /></c:when>
										<c:otherwise><c:set var="operation" value="/${topFacebook.operation}" /></c:otherwise>
									</c:choose>
									<li>
									<a href="<c:url value="/${topFacebook.providerId}${operation}"/>">
									<c:choose>
										<c:when test="${empty topFacebook.operation}">user profile</c:when>
										<c:otherwise>${topFacebook.operation}</c:otherwise>
									</c:choose>
									</a>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<li><a href="<c:url value="/connect/facebook"/>">Connect</a></li>
							</c:otherwise>
						</c:choose>
				</ul>
			</div>
		</div>
	</div>
</div>

 <br />
</c:if>
