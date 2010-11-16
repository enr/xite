import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.Paths
import xite.api.XitePlugin
import xite.api.PluginResult
import xite.plugin.code.CodePlugin

def pathz = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")

XitePlugin plugin = new CodePlugin(configuration: configuration, paths: pathz)

PluginResult result = plugin.apply()

