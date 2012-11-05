package com.github.enr.xite;

import javax.inject.Inject;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.impl.DefaultEnvironmentHolder;

/*
 * holder settled for the acceptance tests environment.
 */
public class AcceptanceTestsEnvironmentHolder extends DefaultEnvironmentHolder {

    @Inject
    public AcceptanceTestsEnvironmentHolder(AppMeta meta, Reporter reporter) {
        super(meta, reporter);
    }

    @Override
    public boolean canExit() {
        return false;
    }

}
