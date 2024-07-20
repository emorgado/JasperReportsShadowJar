package br.com.emorgado.jrsj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.emorgado.jrsj.controllers.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
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

public class App
                 extends
                 Application {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void start( Stage primaryStage ) throws Exception {

        logger.info( "Starting GUI... " );
        MainController controller = new MainController( primaryStage );
        Scene scene = new Scene( controller );

        primaryStage.setScene( scene );
        primaryStage.setTitle( "JasperReports ShadowJar" );

        primaryStage.show();

    }

}
