<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div id="leftMenu">
	<br/>		
	<nav class="ym-vlist">
		<ul>
			<li><a class="ym-vtitle" class="ym-skip" href="<c:url value="/"/>"><strong>Home</strong></a></li>
		</ul>
	</nav>
	<nav class="ym-vlist">
		<ul>
			<li><span>Services</span>
				<ul>
					<li>
						<a href="<c:url value="/twitter"/>">
							<img src="<c:url value="/resources/social/twitter/t_logo-a.png"/>" width="18" height="18"  style="appearance: icon; display:inline;"/>
							<span class="notranslate" style="display: inline;">Twitter</span>
						</a>
					</li>
					<li>
						<a href="<c:url value="/facebook"/>">
							<img src="<c:url value="/resources/social/facebook/f_logo.jpg"/>" width="18" height="18" style="appearance: icon;  display:inline;" />
							<span class="notranslate" style="display: inline;">Facebook</span>
						</a>
					</li>
				</ul>
			</li>					
		</ul>		
	</nav>
	<br/>
	<nav class="ym-vlist">
		<ul>	
			<tiles:insertTemplate template="../views/plugins/favourites.jsp"/>
		</ul>
	</nav>
</div>
