<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page session="true" %>
<html>
	<head>

		<meta charset="utf-8"/>
		<meta name="description" content=""/>
		<meta name="author" content=""/>
		<meta name="google-translate-customization" content="74c9a791c8ff4e8f-6b11dddc6062a424-g05e3390ad7eb749c-18"></meta>
		 <!-- mobile viewport optimisation -->
  		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>easit4all</title>
	
		<!-- slider-->
    	<%-- <link rel="stylesheet" href="<c:url value="/resources/css/nivo-slider.css"/>" type="text/css" media="screen" /> 
   		<link rel="stylesheet" href="<c:url value="/resources//themes/default/default.css"/>" type="text/css" media="screen" />
    	<link rel="stylesheet" href="<c:url value="/resources//themes/light/light.css"/>" type="text/css" media="screen" />
    	<link rel="stylesheet" href="<c:url value="/resources//themes/dark/dark.css"/>" type="text/css" media="screen" />
    	<link rel="stylesheet" href="<c:url value="/resources//themes/bar/bar.css"/>" type="text/css" media="screen" /> --%>
    		
		<!-- yaml stylesheets -->
  		<link rel="stylesheet" href="<c:url value="/resources/yaml/core/base.min.css"/>" type="text/css"/>
  		<link rel="stylesheet" href="<c:url value="/resources/yaml/navigation/hlist.css"/>" type="text/css"/>
  		<link rel="stylesheet" href="<c:url value="/resources/yaml/navigation/vlist.css"/>" type="text/css"/>
  		<link rel="stylesheet" href="<c:url value="/resources/yaml/forms/gray-theme.css"/>" type="text/css"/>
  		<link rel="stylesheet" href="<c:url value="/resources/yaml/screen/screen-FULLPAGE-layout.css"/>" type="text/css"/>
		<!--[if lte IE 7]>
		  <link rel="stylesheet" href="<c:url value="/resources/yaml/core/iehacks.min.css"/>" type="text/css"/>
		<![endif]-->
	  
	  	<!-- other stylesheets -->
		<link rel="stylesheet" href="<c:url value="/resources/page.css" />" type="text/css" media="screen" /> 
		<link rel="stylesheet" href="<c:url value="/resources/form.css" />" type="text/css" media="screen" />
		<link rel="stylesheet" href="<c:url value="/resources/messages/messages.css" />" type="text/css" media="screen" /> 
		<link rel="stylesheet" href="<c:url value="/resources/jquery/jquery-ui-1.9.custom.css"/>" type="text/css" media="screen" /> 
		<link rel="stylesheet" href="<c:url value="/resources/pagination/paging.css"/>" type="text/css" media="screen" /> 
		
		<script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>		
		<script type="text/javascript" src="<c:url value="/resources/infusion/MyInfusion.js" />" ></script>		
		<script src="<c:url value="/resources/js/jquery-1.6.4.min.js"/>"><![CDATA[<!-- -->]]></script>
		<script src="<c:url value="/resources/js/jquery-ui-1.9.custom.js"/>"><![CDATA[<!-- -->]]></script>
		<script src="<c:url value="/resources/js/form.js"/>"><![CDATA[<!-- -->]]></script>
		<script src="<c:url value="/resources/js/jquery.bgiframe.min.js"/>" ><![CDATA[<!-- -->]]></script>
		<script src="<c:url value="/resources/pagination/jquery.paging.js"/>" ><![CDATA[<!-- -->]]></script>
		
		
		<!-- FLUID ACCESSIBILITY PLUGIN -->
		<meta charset="UTF-8" />	
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/framework/fss/css/fss-layout.css" />" />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/framework/fss/css/fss-text.css" />"/>
		<!--
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/framework/fss/css/fss-theme-hc.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/framework/fss/css/fss-theme-hci.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/framework/fss/css/fss-theme-by.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/framework/fss/css/fss-theme-yb.css" />"  />
		-->
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/fss/fss-theme-bw-uio.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/fss/fss-theme-wb-uio.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/fss/fss-theme-by-uio.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/fss/fss-theme-yb-uio.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/fss/fss-text-uio.css" />"  />
	
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/UIOptions.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/FullUIOptions.css" />"  />
		<link rel="stylesheet" type="text/css" href="<c:url value="/resources/infusion/components/uiOptions/css/FullPreviewUIOptions.css" />"  />
		<!-- END FLUID ACCESSIBILITY PLUGIN -->			

		<!-- TO DO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! -->
	<script>
		//define how google translate element has to appear
	
		//register header and nav as elements so the older browsers understand.
		document.createElement('header');
		document.createElement('nav');
		
		function sleep(ms)
		{
			var dt = new Date();
			dt.setTime(dt.getTime() + ms);
			while (new Date().getTime() < dt.getTime());
		}
		
		/*SECTION SELECTED*/
		$(function() { 
		     $("#service-menu ul li").each(function(){
		     				if($(this).find('a').attr("href") == document.getElementById('cs').value) {
		     			 $(this).addClass("active");
		     		}
		    		
		     })
		});
		
		/*SHOW - HIDE DIV*/
		function showDiv(i) {
			div = document.getElementById('commentDiv' + i);
			
			if(div.style.display == 'block'){
			div.style.display = 'none';
			}else if(div.style.display == 'none'){
			div.style.display = 'block';
			};
		}
		
		/*ADJUST TEXT AREA DEPENDING ON TEXT LENGTH*/
		function textAreaAdjust(o) {
		    o.style.height = "1px";
		    o.style.height = (25+o.scrollHeight)+"px";
		}
		
		/*ALLOW SCROLL OF FIXED HEADER ALONG WITH CONTENT */
	    $(window).scroll(function() {
	        $('header').css('left', "-" + $(this).scrollLeft() + "px");
	    });
	
		
	    $(document).ready(function() { 
	    	$('a#configuration').click(function() {
	    	    var submenu = $('div#subConfiguration');
	    	    if (submenu.is(":visible")) {
	    	        submenu.fadeOut();
	    	    } else {
	    	        submenu.fadeIn();
	    	    }
	    	});
	    	 
	    	$(document).click(function(e) {
	    		 var elem = $(e.target).attr('id');
	    		 if(elem !== 'configuration'){
	    			 $('div#subConfiguration').fadeOut();
	    		 }
	    	});
	    	
	    	var theme_class = $('body').attr('class');
	    	$('body').removeClass(theme_class);
	    	$('#main-wrapper').addClass(theme_class);
	    	$('footer').addClass(theme_class);
	    	
	    });
	 new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE, autoDisplay: false}, 'google_translate_element');
		//window.sleep(90);
	</script>
	
	</head>
	<body class="uio-demo-theme"> 
		<input type="hidden" id="cs" value="${currentSection}"/>
		<!-- START FLUID ACCESSIBILITY PLUGIN -->
		<div class="flc-toc-tocContainer"> </div>
		<script type="text/javascript">
		    fluid.pageEnhancer({
		
		        // Specify the default theme class name
		        classnameMap: {
		            theme: {
		                "default": "uio-demo-theme"
		            }
		        },
		        defaultSiteSettings: ${settings},
		     
		        // Specify the table of contents' template URL
		        tocTemplate: "../resources/infusion/components/tableOfContents/html/TableOfContents.html"
		    });
		    
		  /*  fluid.uiEnhancer();
		    var that = fluid.uiEnhancer.cookieStore;
		    that.save = function (${settings}) {};*/
		</script>
		<!-- END FLUID ACCESSIBILITY PLUGIN -->
		
		<ul class="ym-skiplinks">
			<li><a class="ym-skip" href="#nav">Skip to Navigation (Press Enter)</a></li>
			<li><a class="ym-skip" href="#content">Skip to main content (Press Enter)</a></li>
		</ul>
		
		<!-- HEADER -->
		<header>
			<tiles:insertTemplate template="header.jsp"/>
		</header>	
		<!-- MAIN -->
		<div id="main-wrapper">
			<div id="main">
				<div class="ym-wrapper">
					<c:choose>
						<c:when test="${connectedToAny && empty home}">
							<div class="ym-col1">
							 	<div class="ym-cbox">
							 		<tiles:insertTemplate template="menu.jsp" />
							 	</div>
							</div>
							<div class="ym-col3">
								<div class="ym-cbox">
									<section id="content">
										<div class="ym-wrapper">
											<tiles:insertAttribute name="content" />
										</div> 
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
		</div>
		
		<!-- FOOTER -->	
		<footer>	
			<tiles:insertTemplate template="footer.jsp"/>
		</footer>		
	</body>
</html>
