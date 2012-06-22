app {
	version = '0.1-SNAPSHOT'
	baseContext = ''
	// default: platform default
	encoding = 'UTF-8'
  }
  
  project {
	  source = 'src/xite'
	  destination = 'target/xite'
  }
  
  plugins {
	  enabled = [ 'code',
				  'html',
				  'markdown',
				  'resources']
  }
  // auth data should be defined in user settings
  deploy {
	  ftp {
		  enabled = true
		  username = ''
		  password = ''
		  host = ''
		  baseDirectory = ''
	  }
  }
  
  // apparently naming the header file 'header' causes a class cast excp.
  // FIXME: find why
  templates {
	  directory = 'templates'
	  bottom = 'footer.html'
	  top = 'header.html'
  }
  
  markdown {
	  // if you want markdown process only some extensions
	  // ie: extensions = ['md', 'markdown']
	  extensions = []
	  code {
		  // template = '<pre>%s</pre>
		  template = null
	  }
  }
  
  resources {
	  // by default, resources are in source directory / resources
	  directory = 'resources'
	  filter {
		  enabled = true
		  // relative from the sources directory
		  properties = "xite/site.properties"
		  prefix = ''
		  suffix = ''
	  }
	  sources {
		  additionals = [:]
	  }
	  // if a file in resources dir ends with a suffix in excludedFilenameSuffix, that file is not processed
	  excludedFilenameSuffix = []
  }
  
  code {
	  enabled = true
	  // by default process every file in src/code and put it in dest/code
	  source = "code"
	  // the base web context.
	  // by default the doc root
	  // to set it, keep the first slash eg. '/xite'
	  baseContext = ''
	  // the base directory for every code sources.
	  // additionals destinations will be subdir of this.
	  destination = "code"
	  // by default uses the standard header and footer
	  // paths are relatives to the source dir
	  // apparently naming the header file 'header' causes a class cast excp.
	  // FIXME: find why
	  top = "templates/header.html"
	  bottom = "templates/footer.html"
	  
	  template = '<pre><code lang="%s">%n%s%n</code></pre>'
	  // examples of templates for syntax highlighting
	  //template = '<pre class="brush: %s">\n%s\n</pre>'; // http://alexgorbatchev.com/wiki/SyntaxHighlighter
	  //template = '<pre class="sh_%s">\n%s\n</pre>'; // http://shjs.sourceforge.net/doc/documentation.html
	  
	  // user can add additional sources dir (useful for multimodules projects)
	  sources {
		  additionals = [:]
		  excludes = []
	  }
	  // if a file in code dir ends with a suffix in excludedFilenameSuffix, that file is not processed
	  excludedFilenameSuffix = []
  }
  