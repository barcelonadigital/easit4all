<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="main">
	<div class="ym-wrapper">
		<c:choose>
			<c:when test="${connectedToAny}">
				<div class="ym-col1">
				 	<div class="ym-cbox">
				 		<tiles:insertTemplate template="menu.jsp" />
				 	</div>
				</div>
				<div class="ym-col3">
					<div class="ym-cbox">
						<section id="content">
							<tiles:insertAttribute name="content" /> 
						</section> 
					</div>
				</div>
			</c:when>
			<c:otherwise>
				<section id="fullContent">
							<tiles:insertAttribute name="content" /> 
				</section> 
			</c:otherwise>
		</c:choose>
	</div>
</div>