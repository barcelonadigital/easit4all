<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<script type="text/javascript">
	$(document).ready(function() { 
		$('#main').addClass("home");
	});
</script>


<div id="home">
	<br/><br/>

	 <div class="dimmed" style="text-align:justify">
						<p>Please, select any of the menu option list or choose one of the recommended actions listed below. </p>
	</div>
	
	<tiles:insertTemplate template="../plugins/news.jsp"/>
	
	
	<tiles:insertTemplate template="../plugins/cosmSensors.jsp"/>

</div>
