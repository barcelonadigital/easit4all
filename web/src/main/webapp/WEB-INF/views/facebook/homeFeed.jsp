<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>


<div id="postToFb">
	<div class="grid">
		<div class="ym-g75 ym-gl">
			<div class="ym-gbox-left ym-clearfix">
				<form method="POST" class="ym-form" action="<c:url value="/facebook/feed" />">
				<div class="ym-g80 ym-gl">
					<div class="ym-gbox">
						<div class="ym-fbox-text">
							<label for="message">Post to your Facebook wall:</label>
							<textarea id="message" name="message" rows="2" cols="72" onkeyup="textAreaAdjust(this)" style="overflow:hidden; resize: none;" placeholder="Write something..."></textarea><br/>
						</div>
					</div>
				</div>
				<div class="ym-g20 ym-gr">
					<div class="ym-gbox">
						<input class="ym-button" type="submit" value="Post"/>
						<input type="hidden" name="uri" value="${uri}"/>
					</div>
				</div>	
				</form>
			</div>
		</div>
		<div class="ym-g25 ym-gr">
			<div class="ym-gbox ym-clearfix">
				<a href="<c:url value="/facebook/feed" />" class="ym-button">Reload feed</a>
			</div>
		</div>
	</div>
</div>

<div class="feed">
	<div class="ym-grid">
		<div class="ym-hlist">
			<div id="pagination">
				<c:set var="url" value="feed?" scope="request"/>
				<tiles:insertTemplate template="pagination.jsp"/>
			</div> 
		</div>
	</div>
	<tiles:insertTemplate template="feed.jsp"/>
</div>

