import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.api.XitePlugin
import xite.api.PluginResult
import xite.plugin.html.HtmlPlugin

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")

XitePlugin plugin = new HtmlPlugin(configuration: configuration, paths: paths)

PluginResult result = plugin.apply()



