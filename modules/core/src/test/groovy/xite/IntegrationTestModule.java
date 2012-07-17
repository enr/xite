package xite;


import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.ClapApp;
import com.github.enr.clap.api.Command;
import com.github.enr.clap.api.Configuration;
import com.github.enr.clap.api.ConfigurationReader;
import com.github.enr.clap.api.Constants;
import com.github.enr.clap.api.EnvironmentHolder;
import com.github.enr.clap.api.Reporter;
import com.github.enr.clap.impl.DefaultAppMeta;
import com.github.enr.clap.impl.DefaultClapApp;
import com.github.enr.clap.impl.DefaultConfiguration;
import com.github.enr.clap.impl.DefaultEnvironmentHolder;
import com.github.enr.clap.impl.GroovierConfigurationReader;
import com.github.enr.clap.impl.MainCommand;
import com.github.enr.clap.impl.StandardOutReporter;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/*
 * Main default module for Clap.
 * Used in the actual launch of application.
 * It contains bindings for all components used in Clap.
 * You can use them or override them, and of course add your own components.
 */
public class IntegrationTestModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
    	// bind(TransactionLog.class).to(InMemoryTransactionLog.class).in(Singleton.class);
    	
    	// app
        //bind( ClapApp.class ).to( DefaultClapApp.class );
        
        // configuration
        bind( AppMeta.class ).to( DefaultAppMeta.class );
        bind( EnvironmentHolder.class ).to( DefaultEnvironmentHolder.class ).in( Singleton.class );
        bind( Configuration.class ).to( DefaultConfiguration.class ).in( Singleton.class );
        bind( ConfigurationReader.class ).to( GroovierConfigurationReader.class );
        
        // components
        bind( Reporter.class ).to( StandardOutReporter.class ).in( Singleton.class );
        //bind( ElasticSearchService.class ).to( DefaultElasticSearchService.class ).in( Singleton.class );
        //bind( PipelineFactory.class ).to( DefaultPipelineFactory.class ).in( Singleton.class );
        //bind( ResourceFetcher.class ).to( DefaultResourceFetcher.class ).in( Singleton.class );
        
        // commands
        //bind( Command.class ).annotatedWith(Names.named(Constants.MAIN_COMMAND_BIND_NAME)).to( MainCommand.class );
        //bind( Command.class ).annotatedWith(Names.named("command.import")).to( ImportCommand.class );
        //bind( Command.class ).annotatedWith(Names.named("command.list")).to( ListCommand.class );
    }
}