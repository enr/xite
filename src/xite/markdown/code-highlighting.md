
Code highlighting
=================

Using SyntaxHighlighter
-----------------------

To use SyntaxHighlighter (<http://alexgorbatchev.com/SyntaxHighlighter) follow these steps.

Create templates for code plugin and put them in templates directory.

In the header, insert the code for the js and css files

    lang:html
    <link href='/context/css/sh/shCore.css' rel='stylesheet' type='text/css' />
    <link href='/context/css/sh/shThemeDefault.css' rel='stylesheet' type='text/css' />
    <script src="/context/js/sh/shCore.js" type="text/javascript"></script>
    <script src="/context/js/sh/shAutoloader.js" type="text/javascript"></script>

In the footer add SyntaxHighlighter configuration and startup

    lang:html
    [...]
    </body>
    
    <script type="text/javascript">
    function path()
    {
      var args = arguments,
          result = []
          ;
           
      for(var i = 0; i < args.length; i++)
          result.push(args[i].replace('@', '/context/js/sh/'));
           
      return result
    };
     
    SyntaxHighlighter.autoloader.apply(null, path(
      'bash shell             @shBrushBash.js',
      'css                    @shBrushCss.js',
      'diff patch pas         @shBrushDiff.js',
      'groovy                 @shBrushGroovy.js',
      'java                   @shBrushJava.js',
      'js jscript javascript  @shBrushJScript.js',
      'php                    @shBrushPhp.js',
      'text plain             @shBrushPlain.js',
      'py python              @shBrushPython.js',
      'sql                    @shBrushSql.js',
      'xml xhtml xslt html    @shBrushXml.js'
    ));
    SyntaxHighlighter.all();
    </script>

    
Put js and css files in a resources directory, to have something similar:

    lang:text
    /resources/js/sh/shAutoloader.js
    /resources/js/sh/shBrushAS3.js
    [...]
    /resources/js/sh/shBrushXml.js
    /resources/js/sh/shCore.js
    /resources/js/sh/shLegacy.js
    [...]
    /resources/css/sh/shCore.css


Configure the code plugin in xite/site.groovy

    lang:groovy
    code {
        //[...]
    template = '<script type="syntaxhighlighter" class="brush: %s"><![CDATA[%s]]></script>';
    top = "templates/header-code.html"
    bottom = "templates/footer-code.html"
    }



Using SHJS
----------

To use SHJS (<http://shjs.sourceforge.net>) follow these steps.

Create templates for code plugin and put them in templates directory.

In the header, insert the code for the js and css files

    lang:html
    <script type="text/javascript" src="/context/js/sh_main.min.js"></script>
    <link type="text/css" rel="stylesheet" href="/context/css/sh_nedit.min.css">

Modify the body tag:

    lang:html
    <body onload="sh_highlightDocument('/[ your context path ]/js/lang/', '.min.js');">
    
Put js and css files in a resources directory, to have something similar:

    lang:text
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

    lang:groovy
    code {
        //[...]
        template = '<pre class="sh_%s">\n%s\n</pre>';
        top = "templates/header-code.html"
        bottom = "templates/footer-code.html"
    }


