<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<c:if test="${not empty message}">
<div class="${message.type.cssClass}">${message.text}</div>
</c:if>

<c:url value="/signup" var="signupUrl" />
 <div class="ym-grid">
 	<div class="ym-g20 ym-gl">
 		<div class="ym-gbox">
 		    &nbsp;
 		</div>
 	</div>
	<div class="ym-g80 ym-gr">
 		<div class="ym-gbox">
			<form:form class="ym-form" id="signup" action="${signupUrl}" method="post" modelAttribute="signupForm">
				<h3>Sign Up</h3>
				<div class="formInfo">
					<s:bind path="*">
						<c:choose>
							<c:when test="${status.error}">
								<div class="error">Unable to sign up. Please fix the errors below and resubmit.</div>
							</c:when>
						</c:choose>                     
					</s:bind>
				</div>
				<div class="ym-columnar">
					<fieldset>
						<div class="ym-fbox-text">
							<form:label path="firstName">First Name <form:errors path="firstName" cssClass="error" /></form:label>
							<form:input path="firstName" autofocus="autofocus" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="lastName">Last Name <form:errors path="lastName" cssClass="error" /></form:label>
							<form:input path="lastName" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="username">Username <form:errors path="username" cssClass="error" /></form:label>
							<form:input path="username" required="required"/>
						</div>		
						<div class="ym-fbox-text">
							<form:label path="password">Password (at least 6 characters) <form:errors path="password" cssClass="error" min="6"/></form:label>
							<form:password path="password" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="email">Email <form:errors path="email" cssClass="error" /></form:label>
							<form:input path="email" type="email"/>
						</div>
					</fieldset>
					<div class="ym-fbox-button">
						<button class="ym-button" type="submit">Sign Up</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
