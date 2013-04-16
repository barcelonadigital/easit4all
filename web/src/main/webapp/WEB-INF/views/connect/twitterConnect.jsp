<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="connectMenu.jsp"/>
<div id="serviceConnect">
	<form name="tw_connect" id="tw_connect" action="<c:url value="/connect/twitter" />" method="POST">
		<div class="box warning">
			<p>Your easit4all account doesn't have created any connections with Twitter yet.</p>			
			<p>Click the button to connect easit4all with your Twitter account.</p>
			<p>(You'll be redirected to Twitter where you'll be asked to authorize the connection.)</p>
		</div>
		<p>
		 <br />
		<input type="button" title="Connect to Twitter" class="button_connectTwitter" onclick="document.tw_connect.submit()"/><br/><br/>			 
		<p>If you don't have any Twitter account, you can create a <a href="http://www.twitter.com" target="_blank">new</a> one.  <br />
		</p>
	</form>
</div>