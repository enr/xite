package xite;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.ConfigurationReader;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.impl.ConsoleReporter;
import com.github.enr.clap.impl.DefaultConfiguration;
import com.github.enr.clap.impl.DefaultEnvironmentHolder;
import com.github.enr.clap.impl.GroovierFlattenConfigurationReader;
import com.github.enr.clap.impl.PropertiesBackedAppMeta;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/*
 * Integration test module.
 * It contains bindings for all components used in integration tests.
 */
public class IntegrationTestModule extends AbstractModule {
    @Override
    protected void configure() {
        // configuration
        bind( AppMeta.class ).toInstance( PropertiesBackedAppMeta.from("xite-app.properties") );
        bind(EnvironmentHolder.class).to(DefaultEnvironmentHolder.class).in(Singleton.class);
        bind(Configuration.class).to(DefaultConfiguration.class).in(Singleton.class);
        bind(ConfigurationReader.class).to(GroovierFlattenConfigurationReader.class);

        // components
        bind(Reporter.class).to(ConsoleReporter.class).in(Singleton.class);
    }
}