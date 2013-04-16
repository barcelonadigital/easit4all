<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="miniFeed">
	<p>Where:  <br /><c:out value="${event.location}"/> </p>
	<p>Start Time:  <br /><c:out value="${event.startTime}"/> </p>
	<p>End Time:  <br /><c:out value="${event.endTime}"/> </p>
	<p>Invited by:  <br /><c:out value="${event.owner.name}"/> </p>
	<p>Description:  <br />${event.description}</p>
</div>