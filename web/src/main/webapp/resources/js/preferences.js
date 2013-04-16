
/**UPDATE PREFERENCES - APPLICATION FEATURES**/
function doIt() {
	
	/*var browser = $.browser; 
    if ( browser.mozilla && browser.version.slice(0,3) >= "5.0" ) { 
            var styleMoz = $('#map_canvas > div > div').css('-moz-transform').replace (/\(/g, "").replace (/\)/g, "").split(","); 
            var index = styleMoz[4].indexOf('p')-1; 
            var t1 = styleMoz[4].replace(/^\s+|\s+$/g, '').substring(0,index); 
            index = styleMoz[5].indexOf('p')-1; 
            var t2 = styleMoz[5].replace(/^\s+|\s+$/g, '').substring(0,index); 
            var t1t = t1; 
            if(t1!=='0'){ 
                    t1 = parseInt(t1)*-1;                           
            } 
            var t2t = t2; 
            if(t2!=='0'){ 
                    t2 = parseInt(t2)*-1; 
            } 
            $body.css('-moz-transform','translate('+t1+'px,'+t2+'px)'); 
            $body.css({'position':'relative','left':t1t+'px','top':t2t+'px'}); 
    } */
	
	new google.translate.TranslateElement({pageLanguage: 'en', layout: google.translate.TranslateElement.InlineLayout.SIMPLE, autoDisplay: false}, 'google_translate_element');
	
	/*var textSize = document.getElementById('textSize').value;
	var colour = document.getElementById('colour').value;
	var contrast = document.getElementById('contrast').value;
	var magnification = document.getElementById('magnification').value;
	var volume = document.getElementById('volume').value;
	
	//Set text size
	document.getElementById('content').style.fontSize = textSize + "px";
	
	//Set colour
	if (colour == "B/W") {
		document.body.setAttribute("id", "grayscale");
	}
	//First the app should verify if the high contrast system mode is enabled. 
	if (colour == "High Contrast") {
		document.body.setAttribute("id", "invert");
	}
	if (colour == "Normal") {
		document.body.setAttribute("id", "default");
	}
	
	//Set contrast
	//document.getElementById('main').style.filter... = contrast + "%";
	//-webkit-filter: contrast(200%);  
	
	//Set magnification..
	document.body.style.zoom = magnification + "%"; 
	
	//Magnification for Firefox.. dropdown-menus don't work correctly due to a firefox bug
	/*document.body.style.MozTransform = 'scale(' + magnification/100 + ')';
	document.body.style.MozTransformOrigin = '0 0'; 
	*/
	
	//Set volume
	/*if (volume == "Normal") {
		document.getElementById('audioVideo').volume = 50;
	}
	if (volume == "High") {
		document.getElementById('audioVideo').volume = 100;
	}*/
	
	//Set time response
	
	
	/**/
	document.body.style.display = 'block';
} 

