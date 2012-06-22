package com.github.enr.xite;

import javax.inject.Inject;

import com.github.enr.xite.DefaultEnvironmentHolder;
import com.github.enr.xite.Reporter;



/*
 * holder settled for the acceptance tests environment.
 */
public class AcceptanceTestsEnvironmentHolder extends DefaultEnvironmentHolder {

	@Inject
	public AcceptanceTestsEnvironmentHolder(Reporter reporter) {
		super(reporter);
	}

	@Override
	public boolean canExit() {
		return false;
	}

}
