package com.github.enr.xite;

import java.io.File;

/*
 * main configuration container
 */
public interface Configuration {

    /*
     * configuration for a given dataset
     */
    //Dataset dataset(String datasetId);

    /*
     * the number of threads used for fetch datasources
     */
    // do you really need to configure the number of threads ?!
    //int fetchThreads();

    /*
     * File representing Pick's home, ie the dir containing bin/, lib/ and config/ directories.
     */
    File applicationHome();
}
