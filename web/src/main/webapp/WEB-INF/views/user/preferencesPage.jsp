<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<tiles:insertTemplate template="userMenu.jsp"/>
	<!-- ACCESSIBILITY -->
	<div class="flc-toc-tocContainer" />

    <!-- This is the div that will contain the UI Options component -->
    <div id="myUIOptions" class="ym-form"> </div>


	<script type="text/javascript">
	
	
		// Instantiate the UI Enhancer component, specifying the table of contents' template URL
		fluid.pageEnhancer({
			tocTemplate: "../resources/infusion/components/tableOfContents/html/TableOfContents.html",
			defaultSiteSettings: ${settings}
		});
	
    // Instantiate the UI Options component
    fluid.uiOptions.fullPreview("#myUIOptions", {
        // Tell UIOptions where to find all the templates, relative to this file
        prefix: "../resources/infusion/components/uiOptions/html/",

        // Tell UIOptions where to redirect to if the user cancels the operation
        uiOptions: {
            options: {
                listeners: {
                    
                	onCancel: function(){
                        window.location = '${webNameUri}';
                    },
                    
        			onUIOptionsRefresh: function(){
        				location.reload(true);
        				$.post('applicationPreferences', 'content='+JSON.stringify(fluid.cookieStore.fetch('fluid-ui-settings')));
        			}
                }
            }
        },
        
        previewEnhancer: {
            options: {
               // Tell the Preview's UI Enhancer where the Table of Contents template is
                tocTemplate: "../resources/infusion/components/tableOfContents/html/TableOfContents.html",

                // and the name of the default theme
                classnameMap: {
                    theme: {
                        "default": "uio-demo-theme"
                    }
                }
            }
        },        
    });
    
	</script>
	<!-- END ACCESSIBILITY -->