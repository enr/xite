package xite;


import com.github.enr.xite.Configuration;
import com.github.enr.xite.ConfigurationReader;
import com.github.enr.xite.DefaultConfiguration;
import com.github.enr.xite.EnvironmentHolder;
import com.github.enr.xite.GroovierConfigurationReader;
import com.github.enr.xite.Reporter;
import com.github.enr.xite.StandardOutReporter;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

/*
 * Main module for Pick.
 * Used in the actual launch of application.
 */
public class IntegrationTestsModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
        
        // configuration
        bind( EnvironmentHolder.class ).to( IntegrationTestsEnvironmentHolder.class ).in( Singleton.class );
        bind( Configuration.class ).to( DefaultConfiguration.class ).in( Singleton.class );
        bind( ConfigurationReader.class ).to( GroovierConfigurationReader.class );
        
        // components
        bind( Reporter.class ).to( StandardOutReporter.class ).in( Singleton.class );
    }
}