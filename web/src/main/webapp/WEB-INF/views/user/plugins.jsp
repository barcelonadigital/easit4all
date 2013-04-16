<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<script>
	$(document).ready(function() { 
		 $('#sensors').attr('checked', JSON.parse(document.getElementById('vSensors').value));
		 $('#news').attr('checked', JSON.parse(document.getElementById('vNews').value));
		 $('#favourites').attr('checked', JSON.parse(document.getElementById('vFavs').value));
		
		function toggleStatus() {
		    if ($('#sensors').is(':checked')) {
		        $('#sensors-info :input').attr('disabled', true);
		    } else {
		        $('#sensors-info :input').removeAttr('disabled');
		    }   
		}
	});
	

</script>

<tiles:insertTemplate template="userMenu.jsp" />

<c:url value="/user/plugins" var="p" />
<form:form class="ym-form" id="plugins" action="${p}" modelAttribute="pluginsForm">
	<div class="ym-columnar">
		<fieldset>
			 <div class="ym-grid">
   			 	<div class="ym-g50 ym-gl">
   			 		<div class="ym-gbox">
   			 			<span class="ym-label"><strong>Available plug-ins</strong></span>
   			 			<div class="ym-fbox-check">
   			 				  <input type="hidden" id="vSensors" value="${isSensors}"/>
							  <form:checkbox name="sensors" value="${isSensors}" id="sensors" path="sensors" />
 				 			  <label for="sensors">Sensors</label>
						</div>
						<div class="ym-fbox-check">
						  	  <input type="hidden" id="vNews" value="${isNews}"/>
							  <form:checkbox name="news" id="news" path="news"/>
 				 			  <label for="news">News</label>
						</div>
						<div class="ym-fbox-check">
						      <input type="hidden" id="vFavs" value="${isFavourites}"/>
							  <form:checkbox name="favourites" id="favourites" path="favourites"/>
 				 			  <label for="favourites">Favourites</label>
						</div>
					</div>
				</div>
				<div class="ym-g50 ym-gr">
					<div class="ym-gbox" id="sensor-info">
					<span class="ym-label"><strong>Sensors</strong></span>
						<p>Sensor settings</p>
					</div>
				</div>
			</div> 
		</fieldset> 
		
		<div class="ym-fbox-button">
			<input class="ym-button" type="submit" value="Submit changes"/>
		</div>
		</div>
</form:form>