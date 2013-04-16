<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="userMenu.jsp" />

<c:url value="/user/profile" var="p" />
<form:form class="ym-form" id="profile" action="${p}" modelAttribute="profileForm">
	<div class="ym-columnar">
		<fieldset>
			 <div class="ym-grid">
   			 	<div class="ym-g50 ym-gl">
   			 		<div class="ym-gbox">
						<div class="ym-fbox-text">
							<form:label path="age">Age</form:label>
							<form:input type="number" size="3" name="age" path="age"  min="1" max="110" value="${profile.age}" required="required"/>
						</div>
						
						<div class="ym-fbox-select">
							<form:label path="technologySkills">Technology Skills</form:label> 	
							<form:select name="technologySkills" path="technologySkills">
								<c:forEach begin="0" end="3" varStatus="tecLoop">
									<c:choose>
										<c:when test="${profile.technologySkills eq tecLoop.index}"> 
											<option selected="selected" value="${tecLoop.index}">${skillLevel[tecLoop.index]}</option>
										</c:when>
										<c:otherwise>
											<option value="${tecLoop.index}">${skillLevel[tecLoop.index]}</option>
										</c:otherwise>	
									</c:choose>
								</c:forEach>
							</form:select>
						</div>
					</div>
				</div>
				<div class="ym-g50 ym-gr">
					<div class="ym-gbox">
						<div class="ym-fbox-select">
							<form:label path="visualSkills">Visual Skills</form:label>	
							<form:select name ="visualSkills" path="visualSkills">
								<c:forEach begin="0" end="3" varStatus="visLoop">
									<c:choose>
										<c:when test="${profile.visualSkills eq visLoop.index}"> 
											<option selected="selected" value="${visLoop.index}">${skillLevel[visLoop.index]}</option>
										</c:when>
										<c:otherwise>
											<option value="${visLoop.index}">${skillLevel[visLoop.index]}</option>
										</c:otherwise>	
									</c:choose>
								</c:forEach>
							</form:select>
						</div>
						
						<div class="ym-fbox-select">
						<form:label path="hearingSkills">Hearing Skills	</form:label>	
						<form:select name="hearingSkills" path="hearingSkills">
							<c:forEach begin="0" end="3" varStatus="heaLoop">
								<c:choose>
									<c:when test="${profile.hearingSkills eq heaLoop.index}"> 
										<option selected="selected" value="${heaLoop.index}">${skillLevel[heaLoop.index]}</option>
									</c:when>
									<c:otherwise>
										<option value="${heaLoop.index}">${skillLevel[heaLoop.index]}</option>
									</c:otherwise>	
								</c:choose>
							</c:forEach>
						</form:select>
						</div>
					</div>
				</div>
			</div>
		</fieldset>
		
		<div class="ym-fbox-button">
			<input class="ym-button" type="submit" value="Save profile"/>
		</div>
		</div>
</form:form>