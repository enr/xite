
import xite.api.XitePlugin
import xite.api.PluginResult
import xite.plugin.markdown.MarkdownPlugin

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")

XitePlugin plugin = new MarkdownPlugin(configuration: configuration, paths: paths)

PluginResult result = plugin.apply()



