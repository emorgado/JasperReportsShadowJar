package br.com.emorgado.jrsj.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.emorgado.jrsj.model.People;
import br.com.emorgado.jrsj.services.PeopleLoaderService;
import br.com.emorgado.jrsj.services.ReportGeneratorService;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/*
 * 
 * MIT License
 * 
 * Copyright (c) 2017 Emerson Jose Morgado Brito <emerson.morgado@gmail.com>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 */

public class MainController
                            extends
                            GridPane {

    private static final Logger logger = LogManager.getLogger();

    private Stage                  primaryStage;
    private PeopleLoaderService    peopleLoaderService;              // Responsavel pela Aquisiçao de dados
    private ReportGeneratorService reportGeneratorService;           // Responsavel por criar o pdf
    private ProgressBar            progressBar   = new ProgressBar();
    private Label                  progressLabel = new Label();

    public MainController( Stage primaryStage ) {

        super();
        this.primaryStage = primaryStage;

        setupPeopleLoaderService();
        setupReportGeneratorService();
        createGUI();
    }

    private void setupPeopleLoaderService() {

        // Setup Data aquisition service
        peopleLoaderService = new PeopleLoaderService();
        peopleLoaderService.setOnReady( event -> {

            System.out.println( "dataAquisition READY" );

        } );

        peopleLoaderService.setOnRunning( event -> {

            System.out.println( "dataAquisition Running" );
            // Bind service progress to progress bar
            progressBar.progressProperty()
                       .bind( peopleLoaderService.progressProperty() );
            // Bind service message to progress message
            progressLabel.textProperty()
                         .bind( peopleLoaderService.messageProperty() );

        } );

        peopleLoaderService.setOnFailed( event -> {
            // what to do in case of error
            logger.error( "Error getting data!" );

        } );

        peopleLoaderService.setOnSucceeded( event -> {
            // what to do in case of success
            List< People > result = (List< People >) event.getSource()
                                                          .getValue();

            // Call report Service
            reportGeneratorService.setReportData( result );
            // Preciso iniciar ou reiniciar o servico, pode tbm estar dentro do proprio
            // servico, por exeplo no metodo configure
            reportGeneratorService.restart();
        } );
    }

    private void setupReportGeneratorService() {

        // Setup report service
        reportGeneratorService = new ReportGeneratorService();
        reportGeneratorService.setOnReady( event -> {

            System.out.println( "reportService READY" );

        } );

        reportGeneratorService.setOnRunning( event -> {
            System.out.println( "Report RUNNING" );

            // Bind service progress to progress bar
            progressBar.progressProperty()
                       .bind( reportGeneratorService.progressProperty() );

            // Bind service message to progress message
            progressLabel.textProperty()
                         .bind( reportGeneratorService.messageProperty() );

        } );

        reportGeneratorService.setOnFailed( event -> {
            // What to do in case of error
            logger.error( "Error generating report!" );

        } );

        reportGeneratorService.setOnSucceeded( event -> {
            // What to do in case of success
            List< File > pdfFiles = (List< File >) event.getSource()
                                                        .getValue();

            if ( pdfFiles != null ) {
                for ( int i = 0; i < pdfFiles.size(); i++ ) {
                    File pdfFile = pdfFiles.get( i );

                    if ( Desktop.isDesktopSupported() ) {

                        Desktop desktop = Desktop.getDesktop();
                        new Thread( () -> {

                            try {
                                logger.debug( "Opening file " + pdfFile + " with Desktop = " + desktop );
                                desktop.open( pdfFile );

                            } catch ( IOException e ) {
                                logger.error( "!Error while opening the file!" );
                                // throw e; //TODO: Pq não dá certo?!?!
                                e.printStackTrace();
                            }

                        } ).start();

                    }
                }
            }
        } );
    }

    private void createGUI() {

        // Grid Setup
        setHgap( 10 );
        setVgap( 5 );
        setPadding( new Insets( 10 ) );

        getColumnConstraints().add( 0, new ColumnConstraints( 90 ) );

        ColumnConstraints column1Constraints = new ColumnConstraints();
        column1Constraints.setMinWidth( 220 );
        column1Constraints.setPrefWidth( 220 );
        getColumnConstraints().add( 1, column1Constraints );

        // setGridLinesVisible( true ); // Use to debug grid

        int rowIndex = 1;

        // Button setup
        Button btnGenerate = new Button( "Generate" );
        btnGenerate.setOnAction( actionEvent -> {
            peopleLoaderService.restart();
        } );

        add( btnGenerate, 0, rowIndex );
        add( new Label( "Click to generete the report" ), 1, rowIndex++ );

        progressBar.setPrefWidth( Double.MAX_VALUE );
        progressBar.progressProperty()
                   .set( 0 );
        progressLabel.setText( "Waiting your command!" );
        add( progressBar, 0, rowIndex++, 2, 1 );
        add( progressLabel, 0, rowIndex++, 2, 1 );
    }

}
