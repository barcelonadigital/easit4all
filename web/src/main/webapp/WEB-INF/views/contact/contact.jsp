<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:url value="/contact" var="contactUrl" />
 <div class="ym-grid">
	<div class="ym-g80 ym-gr">
 		<div class="ym-gbox">
			<form:form class="ym-form" id="contact" action="${contactUrl}" method="post" modelAttribute="ContactForm">
				<div class="formInfo">
				<c:if test="${param_error eq 'error'}">
			  		<div class="error">
			  			Please fill the required fields and try again.
			  		</div>
		 	 	</c:if>
		  		<c:if test="${send_ok eq 'mail_enviat'}">
			  			<script type="text/javascript">
			  			alert("Your email has been sent");
			  			window.location ="signin"
			  			</script>
		 	 	</c:if>
		  		<c:if test="${no_send eq 'mail_no_enviat'}">
			  			<script type="text/javascript">
			  			alert("Your email wasn't sent. Try again later");
			  			</script>
		 	 	</c:if>
				</div>
				<div class="ym-full">
					<fieldset>
						<h3>Contact us:</h3>
						<br/>
						<div class="ym-fbox-text">
						<p>If you have any question or issue, fill the following form and someone of our team will answer as soon as possible.</p>
						</div>
						<div class="ym-fbox-text">
							<form:label path="firstName">First Name: <form:errors path="firstName" cssClass="error" /></form:label>
							<form:input path="firstName" autofocus="autofocus" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="lastName">Last Name:</form:label>
							<form:input path="lastName"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="email">Email: </form:label>
							<form:input path="email" type="email" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="subject">Subject: <form:errors path="subject" cssClass="error" /></form:label>
							<form:input path="subject" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="text">Text: <form:errors path="text" cssClass="error" /></form:label>
							<form:textarea path="text" required="required" rows="10"/>
						</div>
					</fieldset>
					<div class="ym-fbox-button">
						<button class="ym-button" type="submit" >Send</button>
						<button class="ym-button" type="button"  onclick="document.location.href='signin'">Cancel</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
