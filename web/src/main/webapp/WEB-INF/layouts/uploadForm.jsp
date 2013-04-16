<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page session="false" %>

<form:form method="post" action="${upload}" enctype="multipart/form-data" modelAttribute="uploadItem">
 
                <p>
                    <form:label for="fileData" path="fileData">File</form:label><br/>
                    <form:input path="fileData" type="file"/>
                </p>
                <p> 
					<form:label for="caption" path="caption">Description</form:label>
					<form:input path="caption" type="text"/>
				
				</p> 
                
                	<input type="submit" value="Upload Item"/>
 </form:form>