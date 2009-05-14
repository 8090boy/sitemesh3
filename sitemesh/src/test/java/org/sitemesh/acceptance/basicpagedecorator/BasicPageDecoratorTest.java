package org.sitemesh.acceptance.basicpagedecorator;

import junit.framework.Test;
import org.sitemesh.acceptance.AcceptanceTestSuiteBuilder;
import org.sitemesh.config.SiteMeshConfig;
import org.sitemesh.config.SiteMeshFilter;
import org.sitemesh.offline.SiteMeshOfflineGenerator;
import org.sitemesh.offline.directory.FileSystemDirectory;
import org.sitemesh.webapp.WebEnvironment;

import java.nio.charset.Charset;

/**
 * Tests a basic page decorator.
 *
 * @author Joe Walnes
 * @see AcceptanceTestSuiteBuilder
 */
public class BasicPageDecoratorTest {

    public static Test suite() throws Exception {
        String suiteName = "basicpagedecorator";

        // Config used by both web-app and offline suites.
        SiteMeshConfig siteMeshConfig = new SiteMeshConfig()
                .addDecoratorPath("/*", "/decorator.html");

        // Create web environment (Servlet container, configured with Servlets, Filters, content, etc).
        WebEnvironment webEnvironment = new WebEnvironment.Builder()
                .addFilter("/*", new SiteMeshFilter(siteMeshConfig))
                .setRootDir(AcceptanceTestSuiteBuilder.getInputDir(suiteName))
                .create();

        // Create offline site generator.
        SiteMeshOfflineGenerator offlineGenerator = new SiteMeshOfflineGenerator(
                siteMeshConfig, siteMeshConfig,
                new FileSystemDirectory(AcceptanceTestSuiteBuilder.getInputDir(suiteName), Charset.defaultCharset()));

        // Build suites.
        return AcceptanceTestSuiteBuilder.buildWebAppAndOfflineSuite(suiteName, webEnvironment, offlineGenerator);
    }

}