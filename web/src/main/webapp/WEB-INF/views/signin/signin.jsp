<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>

<script type="text/javascript">
		$('#main').addClass('signin');
</script>


<div class="ym-grid" style="padding-top: 2em;">	
	<div class="ym-g60 ym-gl">
		<div class="ym-gbox-left" style="padding-top: 1em;">			
			<br/>
			<p>An easy and accessible way to use social networks</p>
			<br/>
			<p><img alt="bullet" src="<c:url value="/resources/img/tick.png"/>" /> Interaction with common social networks</p>
			<p><img alt="bullet" src="<c:url value="/resources/img/tick.png"/>" /> Auto-configuration of visual features </p>
			<p><img alt="bullet" src="<c:url value="/resources/img/tick.png"/>" /> Simple and intuitive interface</p>			
		</div>
	</div>

	<div class="ym-g40 ym-gr">
		<div class="ym-gbox-right">
			<div class="formInfo">
		  		<c:if test="${param.error eq 'bad_credentials'}">
			  		<div class="error">
			  			Your sign in information was incorrect.
			  			Please try again.
			  		</div>
		 	 	</c:if>
		  		<c:if test="${param.error eq 'multiple_users'}">
			  		<div class="error">
			  			Multiple local accounts are connected to the provider account.
			  			Try again with a different provider or with your username and password.
			  		</div>
		 	 	</c:if>
			</div>				
			<form class="ym-form" id="signin" action="<c:url value="/signin/authenticate" />" method="post">
				<div class="ym-columnar">
				<fieldset style="border:0px">
					<div class="ym-fbox-text">
						<label for="login">Username</label>
						<input id="login" name="j_username" autofocus="autofocus" type="text" size="25" <c:if test="${not empty signinErrorMessage}">value="${SPRING_SECURITY_LAST_USERNAME}"</c:if> />
					</div>
					<div class="ym-fbox-text">
						<label for="password">Password</label>
						<input id="password" name="j_password" type="password" size="25" />	
					</div>
				</fieldset>
				<div class="ym-fbox-button">
					<button type="submit" title="Sign in" class="ym-button">Sign in</button>
					<!-- class="ym-button ym-disabled" href="<c:url value="/signup"/>"  --> 
					<a title="Sign up" class="ym-button" style="float: right;" href="<c:url value="/signup"/>">Sign up</a>
				</div>
				</div>
			</form>
			 <br />
			<div class="ym-g85 ym-gl">
				<div class="ym-gbox-left">
					<form name="tw_signin" id="tw_signin" action="<c:url value="/signin/twitter"/>" method="POST" >
					<!-- onclick="document.tw_signin.submit()" -->
						<input type="button" title="Sign in with Twitter" class="button_signInTwitter" />
					</form>
				</div>
			</div>
			<div class="ym-g15 ym-gr">
				<div class="ym-gbox-right">
					<form name="fb_signin" id="fb_signin" action="<c:url value="/signin/facebook"/>" method="POST" >
				        <input type="hidden" name="scope" value="publish_stream, user_photos, read_stream, user_videos, manage_friendlists, 
				        read_friendlists, user_events, user_groups, manage_notifications, read_requests, friends_photos, friends_events, email, 
				        rsvp_events" />
				        <!-- onclick="document.fb_signin.submit()" -->
				        <input type="button" title="Sign in with Facebook" class="button_signInFacebook" />
					</form>					
				</div>
			</div>		
		</div>
	</div>	
</div>

<!-- slider 
<br/>
<div class="ym-grid" style="padding-top: 2em;">	
	<div class="ym-g100 ym-gl">					
		    <div class="slider-wrapper theme-default">
		        <div id="slider" class="nivoSlider" style="width:800px;height:500px;">
					  <img src="<c:url value="/resources/img/slider/favourites_pic.png"/>" data-transition="fold" title="THIS IS THE TITLE" alt="THIS IS THE CAPTION"/>
					  <img src="<c:url value="/resources/img/slider/home_pic.png"/>" data-transition="fold"/>
					  <img src="<c:url value="/resources/img/slider/layout_pic.png"/>" data-transition="fold"/>
					  <img src="<c:url value="/resources/img/slider/linksandbuttons_pic.png"/>" data-transition="fold"/>
					  <img src="<c:url value="/resources/img/slider/textanddisplay_pic.png"/>" data-transition="fold"/>
		        </div>
		    </div>
		    <script type="text/javascript" src="<c:url value="/resources/js/jquery-1.7.1.min.js"/>"></script>
		    <script src="<c:url value="/resources/js/jquery.nivo.slider.js"/>" type="text/javascript"></script>
		    <script type="text/javascript">
		    $(window).load(function() {
		        $('#slider').nivoSlider({
		        pauseTime: 20000
		        });
		    });
		    </script>
	</div>
</div>-->
