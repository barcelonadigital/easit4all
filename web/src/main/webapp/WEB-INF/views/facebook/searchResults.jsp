<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="grid">
	<div class="ym-g66 ym-gl">
		<div class="ym-gbox-left">
			<div class="miniFeed">
				<h4>Users:</h4>
				<p>
					<c:choose>
						<c:when test="${not empty users}">
							<tiles:insertTemplate template="usersList.jsp" />
						</c:when>
						<c:otherwise>
							<c:out value="${noresult}"/>
						</c:otherwise>
					</c:choose>
				</p>
				 <br />
				<c:if test="${techSkills eq '3'}">
					<h4>Events:</h4>
					<p>
					<c:choose>
						<c:when test="${not empty events}">
							<tiles:insertTemplate template="eventsList.jsp" />
						</c:when>
						<c:otherwise>
							<c:out value="${noresult}"/>
						</c:otherwise>
					</c:choose>
					</p>
					 <br />
					
					<h4>Groups:</h4>
					<p>
					<c:choose>
						<c:when test="${not empty groups}">
							<tiles:insertTemplate template="groupsList.jsp" />
						</c:when>
						<c:otherwise>
							<c:out value="${noresult}"/>
						</c:otherwise>
					</c:choose>
					</p>
					 <br />
				</c:if>
				<h4>My feed:</h4>
				<p>
					<c:choose>
					<c:when test="${not empty myfeed}">
						<c:set var="feed" value="${myfeed}" scope="request"/>
						<tiles:insertTemplate template="feed.jsp" />
					</c:when>
					<c:otherwise>
						<c:out value="${noresult}"/>
					</c:otherwise>
				</c:choose>
				</p>
				 <br />
				
				<!--<h4>Public feed:</h4>
					<c:choose>
					<c:when test="${not empty publicfeed}">
						<c:set var="feed" value="${publicfeed}" scope="request"/>
						<tiles:insertTemplate template="feed.jsp" />
					</c:when>
					<c:otherwise>
						<c:out value="${noresult}"/>
					</c:otherwise>
				</c:choose>
				 -->
				<h4>My wall:</h4>
				<p>
					<c:choose>
					<c:when test="${not empty mywall}">
						<c:set var="feed" value="${mywall}" scope="request"/>
						<tiles:insertTemplate template="feed.jsp" />
					</c:when>
					<c:otherwise>
						<c:out value="${noresult}"/>
					</c:otherwise>
				</c:choose>
				</p>
			</div>
		</div>
	</div>
	<div class="ym-g33 ym-gr">
		<div class="ym-gbox-right ym-clearfix">
				<div class="ym-vlist">
					<ul>
						<li><span>Filter by:</span>
							<ul>
								<li>
									<a href="<c:url value="/facebook/search/users?query=${param.query}"/>">Users</a>
									<c:if test="${techSkills eq '3'}">
										<a href="<c:url value="/facebook/search/events?query=${param.query}"/>">Events</a>
										<a href="<c:url value="/facebook/search/groups?query=${param.query}"/>">Groups</a>
									</c:if>
									<a href="<c:url value="/facebook/search/myfeed?query=${param.query}"/>">My feed</a>
							<!--	<a href="<c:url value="/facebook/search/publicfeed?query=${param.query}"/>">Public feed</a></li> -->
									<a href="<c:url value="/facebook/search/mywall?query=${param.query}"/>">My wall</a>
									</li>
								</ul> 
							</li> 
						</ul>
				</div>
			</div>
		</div>
	</div>


 <br />