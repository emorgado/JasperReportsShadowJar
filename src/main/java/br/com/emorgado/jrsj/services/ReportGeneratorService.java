package br.com.emorgado.jrsj.services;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.emorgado.jrsj.model.People;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/*
 
MIT License

Copyright (c) 2017 Emerson Jose Morgado Brito <emerson.morgado@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

*/


public class ReportGeneratorService
                                    extends
                                    Service< List< File > > {

    private static final Logger logger = LogManager.getLogger();

    private Map< String, Object > params = new HashMap<>();
    private List< People >        data   = new ArrayList<>();

    public void setReportData( List< People > data, Map< String, Object > params ) {

        this.data = data;
        this.params = params;
    }

    public void setReportData( List< People > data ) {

        this.data = data;
    }

    @Override
    protected Task< List< File > > createTask() {

        return new Task< List< File > >() {

            @Override
            protected List< File > call() throws Exception {

                List< File > generatedReports = new ArrayList<>();

                updateMessage( "Generating report!" );
                updateProgress( 60, 100 );
                Thread.sleep( 200 );

                File generatedFile = null;

                // Create Report
                String reportTemplatePathName = "reports/simpleBeanReport.jrxml";
                String filePathName = System.getProperty( "user.home" ) + File.separator + "Downloads"+File.separator+"SimpleBeanReport-" + new Date().getTime() + ".pdf";

                reportTemplatePathName = reportTemplatePathName.replace( "jrxml", "jasper" );

                logger.debug( " generateReport template: {}, records: {}, filePath: {}", reportTemplatePathName, data.size(), filePathName );

                updateMessage( "Checking report template!" );
                updateProgress( 65, 100 );
                Thread.sleep( 200 );

                InputStream is = null;
                if ( reportTemplatePathName.endsWith( "jrxml" ) ) {
                    is = getClass().getClassLoader()
                                   .getResourceAsStream( reportTemplatePathName );

                } else {
                    is = ClassLoader.getSystemResourceAsStream( reportTemplatePathName ); // Traz o resource no jar
                }

                // System.out.println( "IS \n"+getStringFromInputStream( is ) );

                if ( is == null ) {

                    updateMessage( "Report template not found!" );
                    updateProgress( 100, 100 );
                    Thread.sleep( 200 );

                    logger.error( "Report template file {} not found!", reportTemplatePathName );
                    
                    return null;

                } else {

                    updateMessage( "Report Template found!" );
                    updateProgress( 70, 100 );
                    Thread.sleep( 200 );
                    logger.debug( "Report template found, generating report!" );

                    try {

                        updateMessage( "Preparing datasource!" );
                        updateProgress( 75, 100 );
                        
                        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource( data );

                        JasperReport jasperReport = null;

                        if ( reportTemplatePathName.endsWith( "jrxml" ) ) {

                            JasperDesign jasperDesign = JRXmlLoader.load( is );

                            jasperReport = JasperCompileManager.compileReport( jasperDesign );

                        } else {

                            jasperReport = (JasperReport) JRLoader.loadObject( is );

                        }

                        updateMessage( "Setting metadata and parameters!" );
                        updateProgress( 80, 100 );
                        Thread.sleep( 200 );

                        jasperReport.setProperty( "net.sf.jasperreports.export.pdf.metadata.title", "People's" );
                        jasperReport.setProperty( "net.sf.jasperreports.export.pdf.metadata.subject", "Example of report regerated with jasperreports on a shadow jar" );
                        jasperReport.setProperty( "net.sf.jasperreports.export.pdf.metadata.keywords", "JasperReports, ShadowJar" );
                        jasperReport.setProperty( "net.sf.jasperreports.export.pdf.metadata.author", System.getProperty( "user.name" ) );
                        jasperReport.setProperty( "net.sf.jasperreports.export.pdf.metadata.creator", "Gradle Build, " + System.getProperty( "os.name" ) );

                        Map< String, Object > parameters = new HashMap<>();

                        updateMessage( "Filling report!" );
                        updateProgress( 85, 100 );
                        Thread.sleep( 200 );
                        JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport, parameters, dataSource );

                        updateMessage( "Generating pdf file!" );
                        updateProgress( 95, 100 );
                        Thread.sleep( 200 );
                        JasperExportManager.exportReportToPdfFile( jasperPrint, filePathName );

                        updateMessage( "Report done!" );
                        updateProgress( 100, 100 );

                    } catch ( Exception e ) {
                        e.printStackTrace();
                        logger.error( "Error generating report {}", e.getMessage() );
                        updateMessage( "Report failed!" );
                        updateProgress( 100, 100 );
                        throw e;

                    }

                    logger.debug( "Report(s) successfully generated." );
                    generatedFile = new File( filePathName );

                }

                // Send response
                if ( generatedFile != null ) {
                    generatedReports.add( generatedFile );
                }
                return generatedReports;
            }

        };
    }

}
