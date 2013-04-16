<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:set var="app_name" value="${sessionScope.web_name}"/>
<ul class="imagedList">
<c:forEach items="${feed}" var="post" varStatus="status">
	<li class="imagedItem">
		<div class="image">
			<img src="http://graph.facebook.com/<c:out value="${post.from.id}"/>/picture" align="left"/>
		</div>
		<c:if test="${not empty post.message || post.type eq 'PHOTO' || post.type eq 'VIDEO'}">
		<div class="icons">
		 	<form name ="likeObject" action="<c:url value="/facebook/likeUnlike/${post.id}"/>" method = "POST">
			 	<input type="hidden" name="uri" value="${uri}"/>
				 	<c:set var="userLikeIt" value ="false"/>
				 	<c:forEach items="${post.likes}" var="likes">
				 		<c:if test="${likes.id eq userProf.id}">
				 			<c:set var="userLikeIt" value ="true"/>
				 		</c:if>
				 	</c:forEach>
				 	<c:choose>
				 		<c:when test="${userLikeIt eq true}">
				 			<a href="#" onclick="parentNode.submit();" alt="unlike" title="Unlike">
								<img alt="" src="<c:url value="/resources/img/unlike-facebook.png"/>" width="14" height="14"  style="appearance: icon;">
							</a>
						</c:when>
						<c:otherwise>
							<a href="#" onclick="parentNode.submit();" alt="like" title="Like">
				 				<img alt="" src="<c:url value="/resources/img/like-facebook.png"/>" width="14" height="14"  style="appearance: icon;">
				 			</a>
				 		</c:otherwise>
				 	</c:choose>
			 </form> 
			 <a href="#" onclick="showDiv(${status.index}); return false;" alt="Comment" title="Comment">&#9998</a>
			 <c:if test="${post.from.id eq userProf.id}">
			 	<c:if test="${fn:toLowerCase(post.application.name) eq fn:toLowerCase(app_name)}"> 
			  		<form name ="deletePost" action="<c:url value="/facebook/delete/${post.id}"/>" method = "POST">
			 			<input type="hidden" name="uri" value="${uri}"/>
			 			<a href="#" onclick="parentNode.submit();" alt="Delete" title="Delete">&#10005</a>
			 		</form>  
			 	</c:if>
			 </c:if>
		</div>
		</c:if>
		<div class="feedContent">
			<strong>
			<c:choose>
				<c:when test="${post.from.id eq userProf.id}">
					<a href="<c:url value="/facebook/wall"/>">
				</c:when>
				<c:otherwise>
					<a href="<c:url value="/facebook/friends/${post.from.id}/wall"/>">
				</c:otherwise>
			</c:choose>
			<c:out value="${post.from.name}"/></a></strong><br/>
			<c:if test="${not empty post.message}"> <c:out value="${post.message}"/> <br/></c:if> 
			<c:if test="${not empty post.story}"> <c:out value="${post.story}"/> <br/></c:if> 
			<c:if test="${not empty post.picture}"> <a href="<c:out value="${post.link}"/>"><img  style="max-width: 475px; max-height: 400px;" src="<c:out value="${post.picture}"/>"/></a><br/> </c:if>
			<c:if test="${not empty post.caption}"> <c:out value="${post.caption}"/> <br/></c:if> 
			<span class="postTime"><c:out value="${post.createdTime}"/> 
				<c:if test="${ not empty post.application}">
					via <c:out value="${post.application.name}"/>
				</c:if>
			</span>
			<c:if test="${not empty post.likeCount}">
				<div class="like">
					${post.likeCount} people like this.
				</div>
			</c:if>
			
				<c:if test="${not empty post.comments}">
				<ul class="commentImagedList">
					 <c:forEach items="${post.comments}" var="comment">
					 <li class="commentImagedItem">
						<div class="commentImage">
							<img src="http://graph.facebook.com/<c:out value="${comment.from.id}"/>/picture" align="left"/>
						</div>
						<div class="commentIcons">
				 			 <form name ="likeObject" action="<c:url value="/facebook/likeUnlike/${comment.id}"/>" method = "POST">
							 	<input type="hidden" name="uri" value="${uri}"/>
	<!--						 
	* 								A list of references to users who liked this comment.
	*	 							 May be null, as Facebook often sends only a count of likes. 
									If null, the application will only show "like" icon even when 
									the authenticated user already liked the comment.
	-->
							 	<c:set var="userLikeIt" value ="false"/>
					 			<c:forEach items="${comment.likes}" var="likes">
					 				<c:if test="${likes.id eq userProf.id}">
					 					<c:set var="userLikeIt" value ="true"/>
					 				</c:if>
					 			</c:forEach>
					 			<c:choose>
							 		<c:when test="${userLikeIt eq true}">
							 			<a href="#" onclick="parentNode.submit();" alt="unlike" title="Unlike">
											<img alt="" src="<c:url value="/resources/img/unlike-facebook.png"/>" width="14" height="14"  style="appearance: icon;">
										</a>
									</c:when>
									<c:otherwise>
										<a href="#" onclick="parentNode.submit();" alt="like" title="Like">
							 				<img alt="" src="<c:url value="/resources/img/like-facebook.png"/>" width="14" height="14"  style="appearance: icon;">
							 			</a>
							 		</c:otherwise>
							 	</c:choose>
				 			</form> 
				 			<c:if test="${comment.from.id eq userProf.id}">
						 			<form name ="deleteComment" action="<c:url value="/facebook/delete/${comment.id}"/>" method = "POST">
						 				<input type="hidden" name="uri" value="${uri}"/>
						 				<a href="#" onclick="parentNode.submit();" alt="Delete" title="Delete">&#10005</a>
						 			</form>  
						 	</c:if>
						</div>
						<div class="commentContent">
						<c:choose>
							<c:when test="${comment.from.id eq userProf.id}">
								<a href="<c:url value="/facebook/wall"/>">
							</c:when>
							<c:otherwise>
								 <a href="<c:url value="/facebook/friends/${comment.from.id}/wall"/>">
							</c:otherwise>
						</c:choose>
						 <c:out value="${comment.from.name}"/></a>
						  <br /><c:out value="${comment.message}" />
						  <br /><span class="postTime"><c:out value="${comment.createdTime}"/></span>
			 				<c:if test="${comment.likesCount ne 0}">
			 					<div class="likeComment">
									${comment.likesCount} people like this.
								</div>
							</c:if>
						 </div> 
						 </li>
				 	</c:forEach>
				 </ul>
				  </c:if>
				 <div id="commentDiv${status.index}" style="display: none;">
				 <ul class="commentImagedListUser"> 
				 	<li class="commentImagedItem">
				 		<div class="commentImage">
				 			<img src="http://graph.facebook.com/<c:out value="${userProf.id}"/>/picture" align="left"/>
				 		</div>
				 		<div class="commentContent">
				 			<form name="commentContent" method="POST" action="<c:url value="/facebook/comment/${post.id}" />">
				 				<input type="hidden" name="uri" value="${uri}"/>
				 				<textarea cols="50" rows="1" id="comment" name="comment" onkeyup="textAreaAdjust(this)" style="overflow:hidden; resize: none;"></textarea>
				 				<input type="submit" value="Comment" align="right" />
				 			</form>
				 		</div>
				 	</li>
				 </ul>
				 </div>
			
		</div>
	</li>
</c:forEach>
</ul>

<c:if test="${not empty searchfilter}">
	<c:if test="${empty feed}">No results found for your search criteria</c:if>
</c:if>