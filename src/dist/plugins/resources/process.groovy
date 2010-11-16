import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import xite.Paths
import xite.api.XitePlugin
import xite.api.PluginResult
import xite.plugin.resources.ResourcesPlugin

def paths = binding.getVariable("xite_paths")
def configuration = binding.getVariable("xite_config")

XitePlugin plugin = new ResourcesPlugin(configuration: configuration, paths: paths)

PluginResult result = plugin.apply()


