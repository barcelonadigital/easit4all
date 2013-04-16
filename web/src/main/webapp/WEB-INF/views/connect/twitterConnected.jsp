<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="connectMenu.jsp"/>
<div id="serviceConnect">
	<form id="disconnect" method="post" action="<c:url value="/disconnect/twitter" />">
		<div class="box info">
			<p>Your easit4all account is already connected to your Twitter account.</p>
			<p>Click the button if you wish to disconnect.</p>			
		</div>
	
		<button type="submit" class="ym-button ym-delete">Disconnect</button>	
		<input type="hidden" name="_method" value="delete" />
	</form>
	 <br />
	<p><a href="<c:url value="/twitter" />">View your timeline</a></p>
</div>
