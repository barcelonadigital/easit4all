<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="fbEvents">
	<ul class="imagedList">
	<c:forEach items="${events}" var="event">
			<li class="imagedItem">
				<div class="image">
					<img src="http://graph.facebook.com/<c:out value="${event.id}"/>/picture" align="left"/>
				</div>
				<div class="icons">
					<c:if test="${empty searchfilter}">
						<form name ="joinEvent" action="<c:url value="/facebook/events/joinEvent/${event.id}"/>" method = "POST">
							<input type="hidden" name="uri" value="${uri}"/>
							<c:choose>
								<c:when test="${event.rsvpStatus eq 'ATTENDING' }">
									<button type="submit" class="ym-button ym-disabled">Join</button>
								</c:when>
								<c:otherwise><button class="ym-button" type="submit">Join</button></c:otherwise>
							</c:choose>
						</form>  
						<form name ="maybeEvent" action="<c:url value="/facebook/events/maybeEvent/${event.id}"/>" method = "POST">
								<input type="hidden" name="uri" value="${uri}"/>
								<c:choose>
									<c:when test="${event.rsvpStatus eq 'UNSURE' }">
										<button type="submit" class="ym-button ym-disabled">Maybe</button>
									</c:when>
									<c:otherwise><button class="ym-button" type="submit">Maybe</button></c:otherwise>
								</c:choose>
						</form> 
						<form name ="declineEvent" action="<c:url value="/facebook/events/declineEvent/${event.id}"/>" method = "POST">
								<input type="hidden" name="uri" value="${uri}"/>
								<c:choose>
									<c:when test="${event.rsvpStatus eq 'DECLINED' }">
										<button type="submit" class="ym-button ym-disabled">Decline</button>
									</c:when>
									<c:otherwise><button class="ym-button" type="submit">Decline</button></c:otherwise>
								</c:choose>
						</form> 
					</c:if>
				</div>
				<div class="content">
					<strong><a href="<c:url value="/facebook/events/${event.id}/wall"/>"><c:out value="${event.name}"/></a></strong><br/>
					<c:out value="${event.location}"/>  <br />
					<span class="postTime"><c:out value="${event.startTime}"/></span>
				</div>
			</li>
	</c:forEach>
	</ul>
	
	<c:if test="${not empty searchfilter}">
		<c:if test="${empty events}">No results found for your search criteria</c:if>
	</c:if>
</div>