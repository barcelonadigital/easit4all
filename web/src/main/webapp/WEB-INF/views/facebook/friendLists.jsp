<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="friendHeader">
	<div class="grid">
		<div class="ym-g65 ym-gl">
			<div class="ym-gbox-left ym-clearfix">
				<form class="ym-form ym-columnar" id ="friendList" name="createFriendList" action="<c:url value="/facebook/friends/lists/create"/>" method ="POST">
					<div class="grid">
						<div class="ym-g70 ym-gl">
							<div class="ym-gbox ym-clearfix">
								<div class="ym-fbox-text">
									<label for="newFriendList">Create new friend list: </label>
									<input type="text" name="newFriendList"/>
								</div>
							</div>
						</div>
						<div class="ym-g30 ym-gr">
							<div class="ym-gbox ym-clearfix">
								<input type="submit" value ="Create" class="ym-button"/>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="ym-g35 ym-gr">
			<div class="ym-box-right ym-clearfix" id="backTo">
				<a href="<c:url value="/facebook/friends" />" class="ym-button">Back to Friends</a>
			</div>
		</div>
	</div>
</div>
<div class="feed">
	<div class="ym-grid">
		<div class="ym-hlist">
			<div id="pagination">
				<c:set var="url" value="lists?" scope="request"/>
				<tiles:insertTemplate template="pagination.jsp"/>
			</div> 
		</div>
	</div>
	<ul class="textList">
	<c:forEach items="${friendLists}" var="fl" varStatus="status">
			<li class="textItem">
				<div class="icons">
					 <form name ="deleteFriendList" action="<c:url value="/facebook/friends/lists/delete/${fl.id}"/>" method = "POST">
				 		<a href="#" onclick="parentNode.submit();" alt="Delete" title="Delete">&#10005</a>
				 	 </form>  
				</div> 
				<div class="content">
					<c:out value="${fl.name}"/><br/>
				</div>
			</li>
	</c:forEach>
	</ul>
</div>
