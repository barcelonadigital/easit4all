<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="userMenu.jsp" />

			<c:if test="${not empty message}">
			<div class="${message.type.cssClass}">${message.text}</div>
			</c:if>
			<c:url value="/user/account" var="account" />
			<form:form class="ym-form" id="account" action="${account}" method="post" modelAttribute="signupForm">
				<div class="formInfo">
					<s:bind path="*">
						<c:choose>
							<c:when test="${status.error}">
								<div class="error">Unable to submit your changes. Please fix the errors below and resubmit.</div>
							</c:when>
						</c:choose>                     
					</s:bind>
				</div>
				<div class="ym-columnar">
					<fieldset>
						<div class="ym-fbox-text">
							<form:label path="username" type="hidden"><form:errors path="firstName" cssClass="error" /></form:label>
							<form:input path="username" type="hidden" value="${acc.username}"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="firstName">First Name <form:errors path="firstName" cssClass="error" /></form:label>
							<form:input path="firstName" value="${acc.firstName}" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="lastName">Last Name <form:errors path="lastName" cssClass="error" /></form:label>
							<form:input path="lastName" value="${acc.lastName}" required="required"/>	
						</div>
						<div class="ym-fbox-text">
							<form:label path="password" >Password (at least 6 characters) <form:errors path="password" cssClass="error"/></form:label>
							<form:password path="password" value="${acc.password}" pattern=".{6,}" title="At least 6 characters"  required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="email" >Email <form:errors path="email" cssClass="error" /></form:label>
							<form:input path="email" value="${acc.email}" type="email" required="required"/>
						</div>
						<div class="ym-fbox-text">
							<form:label path="">Language</form:label>
							<div id="google_translate_element">
							</div>
				        </div>
					</fieldset>
					<div class="ym-fbox-button">
						<button type="submit" class="ym-button">Save your changes</button>
					</div>
				</div>
			</form:form> 
