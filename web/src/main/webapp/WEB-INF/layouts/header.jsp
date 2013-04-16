<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="ym-wrapper">
	<div class="ym-grid">
		<div class="ym-g56 ym-gl">
			<div class="ym-gbox-left ym-clearfix">
					<ul style="right: 0;">
						<h2> 
							<a href="<c:url value="/"/>"
								style="color: white; text-decoration: none; font-size: 1.5em"> <span
								class="notranslate"><i>easit4all <sup>beta</sup></i></span></a>
						</h2>
					</ul>
			</div>
		</div>
		<div class="ym-g44 ym-gr">
			<div class="ym-gbox-right">
				<nav class="ym-hlist">
					<ul style="right: 0;">
						<c:if test="${connectedToAny}">
							<li><a><span class="notranslate">${user.firstName}
										${user.lastName}</span></a></li>
							<li><a>|</a></li>
							<li><a href="#" id="configuration">Configuration &#x25BC</a>
								<div id="subConfiguration">
									<div class="ym-vlist">
										<ul>
											<li><a href="<c:url value="/connect"/>">Connections</a></li>
											<li><a
												href="<c:url value="/user/applicationPreferences"/>">Preferences</a></li>
											<li><a href="<c:url value="/user"/>">Account</a></li>
										</ul>
									</div>
								</div></li>
							<li><a>|</a></li>
						</c:if>
						<li><a href="<c:url value="/contact"/>">Contact us</a></li>
						<li><a>|</a></li>						
						<li><a href="<c:url value="/about"/>">About</a></li>						
						<c:if test="${connectedToAny}">
							<li><a>|</a></li>
							<li><a href="<c:url value="/j_spring_security_logout"/>">Sign Out</a></li>
						</c:if>
					</ul>
				</nav>
			</div>
		</div>
	</div>
</div>