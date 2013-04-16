<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

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
			 				<!-- <c:if test="${fn:toLowerCase(post.application.name) eq fn:toLowerCase(app_name)}">--> 
					 			<form name ="deleteComment" action="<c:url value="/facebook/delete/${comment.id}"/>" method = "POST">
					 				<input type="hidden" name="uri" value="${uri}"/>
					 				<a href="#" onclick="parentNode.submit();" alt="Delete" title="Delete">&#10005</a>
					 			</form>  
					 		<!-- </c:if>-->
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