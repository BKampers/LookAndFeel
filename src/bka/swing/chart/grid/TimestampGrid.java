/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


import java.util.*;


public class TimestampGrid extends Grid {
    

    @Override
    public String label(Number value) {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(format, locale);
        return dateFormat.format(new Date(value.longValue()));
    }
    
    
    @Override
    protected void compute(Number min, Number max) {
        Calendar low = Calendar.getInstance();
        low.setTimeInMillis(min.longValue());
        Calendar high = Calendar.getInstance();
        high.setTimeInMillis(max.longValue());
        Calendar test = Calendar.getInstance();
        Step[] steps = steps();
        Step step = steps[0];
        for (Step s : steps) {
            test.setTimeInMillis(low.getTimeInMillis());
            test.add(s.field, s.amount * MAX_GRID_AREAS);
            if (test.after(high)) {
                step = s;
            }
            else {
                break; // Appropriate step found, no need to finish loop.
            }
        }
        switch (step.field) {
            case Calendar.YEAR:
                low.set(Calendar.DAY_OF_YEAR, low.getMinimum(Calendar.DAY_OF_YEAR));
                break;
            case Calendar.MONTH:
                low.set(Calendar.DATE, low.getMinimum(Calendar.DATE));
                break;
            case Calendar.WEEK_OF_YEAR:
                int δ = low.getActualMinimum(Calendar.DAY_OF_WEEK) - low.get(Calendar.DAY_OF_WEEK);
                low.add(Calendar.DAY_OF_WEEK, δ);
                low.set(Calendar.HOUR_OF_DAY, low.getMinimum(Calendar.HOUR_OF_DAY));
                break;
            case Calendar.DATE:
                δ = low.getActualMinimum(Calendar.HOUR_OF_DAY) - low.get(Calendar.HOUR_OF_DAY);
                low.add(Calendar.HOUR_OF_DAY, δ);
                break;
        }
        switch (step.field) {
            case Calendar.YEAR:
            case Calendar.MONTH:
            case Calendar.WEEK_OF_YEAR:
            case Calendar.DATE:
            case Calendar.HOUR_OF_DAY:
                //low.set(Calendar.MINUTE, 0); <- This affects DST offset, do not use it
                low.add(Calendar.MILLISECOND, - low.get(Calendar.MILLISECOND));
                low.add(Calendar.SECOND, - low.get(Calendar.SECOND));
                low.add(Calendar.MINUTE, - low.get(Calendar.MINUTE));
                break;
            case Calendar.MINUTE:
                //low.clear(Calendar.SECOND); <- This affects DST offset, do not use it
                long round = step.amount * bka.numeric.Time.MILLIS_PER_MINUTE;
                low.setTimeInMillis((low.getTimeInMillis() / round) * round);
                break;
            case Calendar.SECOND:
                //low.clear(Calendar.MILLISECOND); <- This affects DST offset, do not use it
                round = step.amount * bka.numeric.Time.MILLIS_PER_SECOND;
                low.setTimeInMillis((low.getTimeInMillis() / round) * round);
                break;
            case Calendar.MILLISECOND:
                round = step.amount;
                low.setTimeInMillis((low.getTimeInMillis() / round) * round);
                break;
        }
        values.add(low.getTimeInMillis());
        boolean ready = false;
        while (! ready) {
            low.add(step.field, step.amount);
            values.add(low.getTimeInMillis());
            ready = low.getTimeInMillis() > high.getTimeInMillis();
        }
        format = step.format;
    }

    
    protected Step[] steps() {
        return Step.values();
    }
    
    
    protected enum Step {
        ONE_YEAR           (Calendar.YEAR        ,  1, "yyyy>"),
        SIX_MONTHS         (Calendar.MONTH       ,  6, "d MMM yyyy"),
        FOUR_MONTHS        (Calendar.MONTH       ,  4, "d MMM yyyy"),
        THREE_MONTHS       (Calendar.MONTH       ,  3, "d MMM yyyy"),
        ONE_MONTH          (Calendar.MONTH       ,  1, "MMM yyyy>"),
        TWO_WEEKS          (Calendar.WEEK_OF_YEAR,  2, "d MMM"),
        ONE_WEEK           (Calendar.WEEK_OF_YEAR,  1, "d MMM"),
        FIVE_DAYS          (Calendar.DATE        ,  5, "d MMM"),
        TWO_DAYS           (Calendar.DATE        ,  2, "d MMM"),
        ONE_DAY            (Calendar.DATE        ,  1, "d MMM>"),
        TWELVE_HOURS       (Calendar.HOUR_OF_DAY , 12, "H:mm"),
        SIX_HOURS          (Calendar.HOUR_OF_DAY ,  6, "H:mm"),
        FOUR_HOURS         (Calendar.HOUR_OF_DAY ,  4, "H:mm"),
        THREE_HOURS        (Calendar.HOUR_OF_DAY ,  3, "H:mm"),
        TWO_HOURS          (Calendar.HOUR_OF_DAY ,  2, "H:mm"),
        ONE_HOUR           (Calendar.HOUR_OF_DAY ,  1, "H:mm"),
        THIRTY_MINUTES     (Calendar.MINUTE      , 30, "H:mm"),
        TWENTY_MINUTES     (Calendar.MINUTE      , 20, "H:mm"),
        FIFTEEN_MINUTES    (Calendar.MINUTE      , 15, "H:mm"),
        TEN_MINUTES        (Calendar.MINUTE      , 10, "H:mm"),
        FIVE_MINUTES       (Calendar.MINUTE      ,  5, "H:mm"),
        TWO_MINUTES        (Calendar.MINUTE      ,  2, "H:mm"),
        ONE_MINUTE         (Calendar.MINUTE      ,  1, "H:mm"),
        THIRTY_SECONDS     (Calendar.SECOND      , 30, "H:mm:ss"),
        TWENTY_SECONDS     (Calendar.SECOND      , 20, "H:mm:ss"),
        FIFTEEN_SECONDS    (Calendar.SECOND      , 15, "H:mm:ss"),
        TEN_SECONDS        (Calendar.SECOND      , 10, "H:mm:ss"),
        FIVE_SECONDS       (Calendar.SECOND      ,  5, "H:mm:ss"),
        TWO_SECONDS        (Calendar.SECOND      ,  2, "H:mm:ss"),
        ONE_SECOND         (Calendar.SECOND      ,  1, "H:mm:ss"),
        FIVE_HUNDRED_MILLIS(Calendar.MILLISECOND ,500, "mm''ss.SSS"),
        TWO_HUNDRED_MILLIS (Calendar.MILLISECOND ,200, "mm''ss.SSS"),
        ONE_HUNDRED_MILLIS (Calendar.MILLISECOND ,100, "mm''ss.SSS"),
        FIFTY_MILLIS       (Calendar.MILLISECOND , 50, "mm''ss.SSS"),
        TWENTY_MILLIS      (Calendar.MILLISECOND , 20, "mm''ss.SSS"),
        TEN_MILLIS         (Calendar.MILLISECOND , 10, "mm''ss.SSS"),
        FIVE_MILLIS        (Calendar.MILLISECOND ,  5, "mm''ss.SSS"),
        ONE_MILLI          (Calendar.MILLISECOND ,  1, "mm''ss.SSS");

        Step(int field, int amount, String format) {
            this.field = field;
            this.amount = amount;
            this.format = format;
        }

        int field;
        int amount;
        String format;

    }
    
    
    private static final int MAX_GRID_AREAS = 11;

}
