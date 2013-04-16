<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="connectMenu.jsp"/>
<div id="serviceConnect">
	<form id="disconnect" method="post"  action="<c:url value="/disconnect/facebook" />" >
		<div class="box info">
			<p>Your easit4all account is already connected to your Facebook account.</p>
			<p>Click the button if you wish to disconnect.</p>			
		</div>
		<button class="ym-button ym-delete" type="submit">Disconnect</button>	
		<input type="hidden" name="_method" value="delete" />
	</form>
	 <br />
	<a href="<c:url value="/facebook"/>">Go to News feed</a>
</div>