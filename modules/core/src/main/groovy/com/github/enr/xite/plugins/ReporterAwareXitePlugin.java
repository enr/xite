package com.github.enr.xite.plugins;

import com.github.enr.clap.api.Reporter;

public interface ReporterAwareXitePlugin {

    void setReporter(Reporter reporter);
}
