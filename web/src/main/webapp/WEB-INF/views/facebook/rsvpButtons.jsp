<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form name ="joinEvent" action="<c:url value="/facebook/events/joinEvent/${event.id}"/>" method = "POST">
		<input type="hidden" name="uri" value="${uri}"/>
		<c:choose>
			<c:when test="${rsvp eq 'ATTENDING' }">
				<button type="submit" disabled="disabled" class="ym-button">Join</button>
			</c:when>
			<c:otherwise><button type="submit" class="ym-button">Join</button></c:otherwise>
		</c:choose>
</form>  
<form name ="maybeEvent" action="<c:url value="/facebook/events/maybeEvent/${event.id}"/>" method = "POST">
		<input type="hidden" name="uri" value="${uri}"/>
		<c:choose>
			<c:when test="${rsvp eq 'UNSURE'}">
				<button type="submit" disabled="disabled" class="ym-button">Maybe</button>
			</c:when>
			<c:otherwise><button type="submit" class="ym-button" >Maybe</button></c:otherwise>
		</c:choose>
</form> 
<form name ="declineEvent" action="<c:url value="/facebook/events/declineEvent/${event.id}"/>" method = "POST">
		<input type="hidden" name="uri" value="${uri}"/>
		<c:choose>
			<c:when test="${rsvp eq 'DECLINED' }">
				<button type="submit" disabled="disabled" class="ym-button">Decline</button>
			</c:when>
			<c:otherwise><button type="submit" class="ym-button">Decline</button></c:otherwise>
		</c:choose>
</form> 