package br.com.emorgado.jrsj.report.datasource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.com.emorgado.jrsj.model.People;

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

/**
 * This class aim to be used as a JavaBean datasouce to be passed to jasper
 * report
 * in develoment time
 * 
 * @author emorgado
 *
 */
public class ReportDataMockDatasource {

    public static List< People > createBeanCollection() {

        List< People > data = new ArrayList<>();

        data.add( new People( "Emerson", "Morgado Brito", LocalDate.of( 1980, 10, 1 ) ) );
        data.add( new People( "Angela", "Dias Brito", LocalDate.of( 1984, 4, 4 ) ) );
        data.add( new People( "Julia", "Dias Brito", LocalDate.of( 2018, 5, 30 ) ) );

        return data;
    }
}
