/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


public class CalendarDemarcations extends TimestampDemarcations {

    
    protected Step[] steps() {
        return new Step[] { Step.ONE_YEAR, Step.ONE_MONTH, Step.ONE_WEEK, Step.ONE_DAY };
    }
    
        
}
