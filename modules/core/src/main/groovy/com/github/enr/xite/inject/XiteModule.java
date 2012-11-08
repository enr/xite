package com.github.enr.xite.inject;

import com.github.enr.clap.api.AppMeta;
import com.github.enr.clap.api.Command;
import com.github.enr.clap.impl.PropertiesBackedAppMeta;
import com.github.enr.xite.commands.BuildCommand;
import com.github.enr.xite.commands.CleanCommand;
import com.github.enr.xite.commands.DeployCommand;
import com.github.enr.xite.commands.ServeCommand;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class XiteModule extends AbstractModule {
    @Override
    protected void configure() {
        // bind(TransactionLog.class).to(InMemoryTransactionLog.class).in(Singleton.class);

        // app
        // bind( App.class ).to( DefaultApp.class );

        // configuration
        //bind(AppMeta.class).to(XiteMeta.class);
        bind( AppMeta.class ).toInstance( PropertiesBackedAppMeta.from("xite-app.properties") );
        /*
         * bind( EnvironmentHolder.class ).to( DefaultEnvironmentHolder.class
         * ).in( Singleton.class ); bind( Configuration.class ).to(
         * DefaultConfiguration.class ).in( Singleton.class ); bind(
         * ConfigurationReader.class ).to( GroovierConfigurationReader.class );
         */
        // components
        // bind( Reporter.class ).to( StandardOutReporter.class ).in(
        // Singleton.class );
        // bind( ElasticSearchService.class ).to(
        // DefaultElasticSearchService.class ).in( Singleton.class );
        // bind( PipelineFactory.class ).to( DefaultPipelineFactory.class ).in(
        // Singleton.class );
        // bind( ResourceFetcher.class ).to( DefaultResourceFetcher.class ).in(
        // Singleton.class );

        // commands
        bind(Command.class).annotatedWith(Names.named("command.clean")).to(CleanCommand.class);
        bind(Command.class).annotatedWith(Names.named("command.deploy")).to(DeployCommand.class);
        bind(Command.class).annotatedWith(Names.named("command.process")).to(BuildCommand.class);
        bind(Command.class).annotatedWith(Names.named("command.run")).to(ServeCommand.class);
    }
}
