package com.github.enr.xite;


import com.github.enr.xite.App;
import com.github.enr.xite.BuildCommand;
import com.github.enr.xite.CleanCommand;
import com.github.enr.xite.Command;
import com.github.enr.xite.Configuration;
import com.github.enr.xite.ConfigurationReader;
import com.github.enr.xite.DefaultApp;
import com.github.enr.xite.DefaultConfiguration;
import com.github.enr.xite.DeployCommand;
import com.github.enr.xite.EnvironmentHolder;
import com.github.enr.xite.GroovierConfigurationReader;
import com.github.enr.xite.MainCommand;
import com.github.enr.xite.Reporter;
import com.github.enr.xite.RunCommand;
import com.github.enr.xite.StandardOutReporter;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

/*
 * Main module for Pick.
 * Used in the actual launch of application.
 */
public class AcceptanceTestsModule extends AbstractModule
{
    @Override
    protected void configure ()
    {
    	// bind(TransactionLog.class).to(InMemoryTransactionLog.class).in(Singleton.class);
    	
    	// app
        bind( App.class ).to( DefaultApp.class );
        
        // configuration
        bind( EnvironmentHolder.class ).to( AcceptanceTestsEnvironmentHolder.class ).in( Singleton.class );
        bind( Configuration.class ).to( DefaultConfiguration.class ).in( Singleton.class );
        bind( ConfigurationReader.class ).to( GroovierConfigurationReader.class );
        
        // components
        bind( Reporter.class ).to( StandardOutReporter.class ).in( Singleton.class );
        //bind( PipelineFactory.class ).to( DefaultPipelineFactory.class ).in( Singleton.class );
        //bind( ResourceFetcher.class ).to( DefaultResourceFetcher.class ).in( Singleton.class );
        
        // commands
        bind( Command.class ).annotatedWith(Names.named("command.main")).to( MainCommand.class );
        bind( Command.class ).annotatedWith(Names.named("command.build")).to( BuildCommand.class );
        bind( Command.class ).annotatedWith(Names.named("command.run")).to( RunCommand.class );
        bind( Command.class ).annotatedWith(Names.named("command.deploy")).to( DeployCommand.class );
        bind( Command.class ).annotatedWith(Names.named("command.clean")).to( CleanCommand.class );
    }
}