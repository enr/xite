
Code highlighting
=================

Using SHJS
----------

To use SHJS (<http://shjs.sourceforge.net>) follow these steps.

Create templates for code plugin and put them in templates directory.

In the header, insert the code for the js and css files

    <script type="text/javascript" src="/xite/js/sh_main.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/xite/css/sh_nedit.min.css">

Modify the body tag:

    <body onload="sh_highlightDocument('/[ your context path ]/js/lang/', '.min.js');">
    
Put js and css files in a resources directory, to have something similar:

    /resources/js/lang
    /resources/js/lang/sh_css.min.js
    /resources/js/lang/sh_groovy.min.js
    /resources/js/lang/sh_html.min.js
    /resources/js/lang/sh_java.min.js
    /resources/js/lang/sh_javascript.min.js
    /resources/js/lang/sh_properties.min.js
    /resources/js/lang/sh_sh.min.js
    /resources/js/lang/sh_xml.min.js
    /resources/js/sh_main.min.js
    /resources/css
    /resources/css/main.css
    /resources/css/sh_nedit.min.css

Configure the code plugin in xite/site.groovy

    code {
        //[...]
        template = '<pre class="sh_%s">\n%s\n</pre>';
        top = "templates/header-code.html"
        bottom = "templates/footer-code.html"
    }


