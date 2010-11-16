package xite.api

interface XitePlugin
{
    PluginResult init();
    PluginResult apply();
    PluginResult cleanup();
}
