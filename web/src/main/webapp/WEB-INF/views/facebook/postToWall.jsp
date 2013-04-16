<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
	<c:when test="${not empty event}">
		<c:set var="fuser" value="${event}" />
	</c:when>
	<c:when test="${not empty group}">
		<c:set var="fuser" value="${group}" />
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${empty fuser}">
		<c:set var="post_label" value="Post to your facebook wall:"/>
		<c:set var="posting" value="/facebook/feed"/>
	</c:when>
	<c:otherwise>
		<c:set var="post_label"  value="Write something to ${fuser.name}:"/>
		<c:set var="posting" value="/facebook/post/${fuser.id}"/>
	</c:otherwise>
</c:choose>

<div id="postToFb">
	<div class="grid">
		<div class="ym-g75 ym-gl">
			<div class="ym-gbox-left ym-clearfix">
				<form method="POST" class="ym-form" action="<c:url value="${posting}" />">
					<div class="ym-g80 ym-gl">
						<div class="ym-gbox">
							<div class="ym-fbox-text">
								<label for="message"><c:out value="${post_label}"/></label>
								<input type="hidden" name="uri" value="${uri}"/>
								<textarea id="message" name="message" rows="2" cols="72" onkeyup="textAreaAdjust(this)" style="overflow:hidden; resize: none;" placeholder="Write something..."></textarea><br/>
							</div>
						</div>
					</div>
					<div class="ym-g20 ym-gr">
						<div class="ym-gbox">
							<input class="ym-button" type="submit" value="Post"/>
						</div>
					</div>	
				</form>
			</div>
		</div>
		<div class="ym-g25 ym-gr">
			<div class="ym-gbox ym-clearfix">
				<c:choose>
					<c:when test="${empty fuser}">
						<a href="<c:url value="/facebook/wall" />" class="ym-button">Update wall</a>
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</div>
</div>