<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<div class="mediaUpload">
	<form:form method="post" action="${upload}" enctype="multipart/form-data" modelAttribute="uploadItem" class="ym-form">
		<p>Upload a photo:</p>
		<div class="ym-fbox-text">
		    <form:label for="fileData" path="fileData">File</form:label><br/>
		    <form:input path="fileData" type="file" multiple="multiple"/> <!-- multiple photo upload not currently available-->
	    </div>
		<div class="ym-fbox-text">
			<form:label for="caption" path="caption">Description</form:label>
			<form:input path="caption" type="text"/>
		</div>
	    <div class="ym-fbox-button">        
	    	<input type="submit" value="Upload Photo" class="ym-button"/>
	    </div>
	</form:form>
</div> 
