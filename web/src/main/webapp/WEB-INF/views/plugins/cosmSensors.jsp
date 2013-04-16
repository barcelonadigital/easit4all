<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${cosmSensorsLoaded}">

	 <div class="ym-grid">
			<div class="ym-g50 ym-gl">
				<div class="ym-gbox">			
					<object width="280" height="210">
						<param name="allowScriptAccess" value="always" />
						<param name="movie" value="http://apps.pachube.com/media/pachublog.swf" />
						<param name="FlashVars" value="feedId=${feedId}&dataId=0&tagName=Temperature&unitName=%BAC&lineColour=0066FF" />
						<embed src="http://apps.pachube.com/media/pachublog.swf" width="280" height="210" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" FlashVars="feedId=${feedId}&dataId=0&tagName=Temperature&unitName=%BAC&lineColour=0066FF" />
					</object>
				</div>
			</div>
			
			<div class="ym-g50 ym-gr">
				<div class="ym-gbox">
					<object width="280" height="210">
						<param name="allowScriptAccess" value="always" />
						<param name="movie" value="http://apps.pachube.com/media/pachublog.swf" />
						<param name="FlashVars" value="feedId=${feedId}&dataId=1&tagName=Luminosity&unitName=%BAL&lineColour=0066FF" />
						<embed src="http://apps.pachube.com/media/pachublog.swf" width="280" height="210" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" FlashVars="feedId=${feedId}&dataId=1&tagName=Luminosity&unitName=%BAL&lineColour=0066FF" />
					</object>
				</div>
			</div>
		</div>

</c:if>