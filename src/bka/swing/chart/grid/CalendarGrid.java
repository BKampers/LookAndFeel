/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


public class CalendarGrid extends TimestampGrid {

    
    @Override
    protected Step[] steps() {
        return new Step[] { Step.ONE_YEAR, Step.ONE_MONTH, Step.ONE_WEEK, Step.ONE_DAY };
    }
    
        
}
