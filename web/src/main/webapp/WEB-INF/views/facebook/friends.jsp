<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<c:choose>
	<c:when test="${(currentListId) ne null && (currentListId ne 1)}">
		<c:set var="url" value="friends?friendListId=${currentListId}&" scope="request"/>
	</c:when>
	<c:otherwise>
		<c:set var="url" value="friends?"  scope="request"/>
	</c:otherwise>
</c:choose>

<div id="friendHeader">
	<div class="ym-gbox-left">
		<form class="ym-form" id ="friendList" name="choose" method ="GET">
			<div class="grid">
				<div class="ym-g80 ym-gl">
					<div class="ym-gbox-left ym-clearfix">
						<div class="ym-fbox">
							<label for="friendListId">Choose a friend list </label>
							<select name="friendListId">
								<option selected value="${currentListName}">${currentListName}</option>
								<c:forEach items="${friendLists}" var="fl">
									<c:if test="${fl.name ne currentListName}">
									<option value="${fl.id}">${fl.name} </option>
									</c:if>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>
				<div class="ym-g30 ym-gr">
					<div class="ym-gbox-right ym-clearfix">
						<input type="submit" value ="Choose" class="ym-button"/>
					</div>
				</div>
			</div>
		</form>
	</div>
</div>

<div class="feed">
	<div class="ym-grid">
		<div class="ym-hlist">
			<div id="pagination">
				<tiles:insertTemplate template="pagination.jsp"/>
			</div> 
		</div>
	</div>
	<ul class="imagedList">
	<c:forEach items="${friends}" var="friend" varStatus="status">
			<li class="imagedItem">
				<div class="image">
					<img src="http://graph.facebook.com/<c:out value="${friend.id}"/>/picture" align="left"/>
				</div> 
				<div class="content">
					<div class="grid">
						<div class="ym-g55 ym-gl">
							<div class="ym-gbox-left ym-clearfix">
								<strong><a href="<c:url value="/facebook/friends/${friend.id}/wall"/>"><c:out value="${friend.name}"/></a></strong>
							</div>
						</div>
						<div class="ym-g45 ym-gr">
							<div class="ym-gbox-right ym-clearfix">
								<c:choose>
									<c:when test="${techSkills eq '3'}">
										<form name="list" action="<c:url value="/facebook/manageLists/${friend.id}"/>" method = "POST">
											<select id="s1" name="fListId[]" multiple="multiple" style="height: 40px; min-width:60px; display:inline-block;">
												<!--  <c:forEach items="${listsPerUser[status.index]}" var="lu">
													<option value="${lu.id}">${lu.name} </option>
												</c:forEach>  -->
												 <c:forEach items="${friendLists}" var="fl">
												 	<c:if test="${fl.id ne 1}">
														 <c:choose>
													 		<c:when test="${fn:contains(listsPerUser[status.index], fl)}">
																<option selected value="${fl.id}">${fl.name} </option>
															</c:when>
															<c:otherwise>
															 	<option value="${fl.id}">${fl.name} </option>
															</c:otherwise>
														</c:choose>
													</c:if>
												</c:forEach> 
											</select>
											<input type="submit" value="Submit" style="display:inline-block;"/>
										</form>
									</c:when>
									<c:otherwise>
										&nbsp;
									</c:otherwise>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</li>
	</c:forEach>
	</ul>
</div>




