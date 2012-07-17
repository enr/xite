package com.github.enr.xite.plugins;

import com.github.enr.clap.api.EnvironmentHolder;

public interface EnvironmentAwareXitePlugin {

    void setEnvironment(EnvironmentHolder environment);
}
