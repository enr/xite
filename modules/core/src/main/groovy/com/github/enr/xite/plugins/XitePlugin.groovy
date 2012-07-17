package com.github.enr.xite.plugins

interface XitePlugin
{
    void setSourcePath(String sourcePath);
    void setDestinationPath(String destinationPath);
    PluginResult init();
    PluginResult apply();
    PluginResult cleanup();
}
