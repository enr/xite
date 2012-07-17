package com.github.enr.xite.plugins

import com.github.enr.clap.api.Configuration

interface ConfigurationAwareXitePlugin
{
    void setConfiguration(Configuration config);
}
