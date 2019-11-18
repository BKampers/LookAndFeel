/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


import java.util.*;


public class TimestampGrid extends Grid {
    

    private static final String YEAR_FORMAT = "yyyy>";
    private static final String MONTHS_FORMAT = "d MMM yyyy";
    private static final String MONTH_FORMAT = "MMM yyyy>";
    private static final String DAYS_FORMAT = "d MMM";
    private static final String DAY_FORMAT = "d MMM>";
    private static final String MINUTES_FORMAT = "H:mm";
    private static final String SECONDS_FORMAT = "H:mm:ss";
    private static final String MILLIS_FORMAT = "ss.SSS";

    protected enum StepSize {
        ONE_YEAR(Calendar.YEAR, 1, YEAR_FORMAT),
        SIX_MONTHS(Calendar.MONTH, 6, MONTHS_FORMAT),
        FOUR_MONTHS(Calendar.MONTH, 4, MONTHS_FORMAT),
        THREE_MONTHS(Calendar.MONTH, 3, MONTHS_FORMAT),
        ONE_MONTH(Calendar.MONTH, 1, MONTH_FORMAT),
        TWO_WEEKS(Calendar.WEEK_OF_YEAR, 2, DAYS_FORMAT),
        ONE_WEEK(Calendar.WEEK_OF_YEAR, 1, DAYS_FORMAT),
        FIVE_DAYS(Calendar.DATE, 5, DAYS_FORMAT),
        TWO_DAYS(Calendar.DATE, 2, DAYS_FORMAT),
        ONE_DAY(Calendar.DATE, 1, DAY_FORMAT),
        TWELVE_HOURS(Calendar.HOUR_OF_DAY, 12, MINUTES_FORMAT),
        SIX_HOURS(Calendar.HOUR_OF_DAY, 6, MINUTES_FORMAT),
        FOUR_HOURS(Calendar.HOUR_OF_DAY, 4, MINUTES_FORMAT),
        THREE_HOURS(Calendar.HOUR_OF_DAY, 3, MINUTES_FORMAT),
        TWO_HOURS(Calendar.HOUR_OF_DAY, 2, MINUTES_FORMAT),
        ONE_HOUR(Calendar.HOUR_OF_DAY, 1, MINUTES_FORMAT),
        THIRTY_MINUTES(Calendar.MINUTE, 30, MINUTES_FORMAT),
        TWENTY_MINUTES(Calendar.MINUTE, 20, MINUTES_FORMAT),
        FIFTEEN_MINUTES(Calendar.MINUTE, 15, MINUTES_FORMAT),
        TEN_MINUTES(Calendar.MINUTE, 10, MINUTES_FORMAT),
        FIVE_MINUTES(Calendar.MINUTE, 5, MINUTES_FORMAT),
        TWO_MINUTES(Calendar.MINUTE, 2, MINUTES_FORMAT),
        ONE_MINUTE(Calendar.MINUTE, 1, MINUTES_FORMAT),
        THIRTY_SECONDS(Calendar.SECOND, 30, SECONDS_FORMAT),
        TWENTY_SECONDS(Calendar.SECOND, 20, SECONDS_FORMAT),
        FIFTEEN_SECONDS(Calendar.SECOND, 15, SECONDS_FORMAT),
        TEN_SECONDS(Calendar.SECOND, 10, SECONDS_FORMAT),
        FIVE_SECONDS(Calendar.SECOND, 5, SECONDS_FORMAT),
        TWO_SECONDS(Calendar.SECOND, 2, SECONDS_FORMAT),
        ONE_SECOND(Calendar.SECOND, 1, SECONDS_FORMAT),
        FIVE_HUNDRED_MILLIS(Calendar.MILLISECOND, 500, MILLIS_FORMAT),
        TWO_HUNDRED_MILLIS(Calendar.MILLISECOND, 200, MILLIS_FORMAT),
        ONE_HUNDRED_MILLIS(Calendar.MILLISECOND, 100, MILLIS_FORMAT),
        FIFTY_MILLIS(Calendar.MILLISECOND, 50, MILLIS_FORMAT),
        TWENTY_MILLIS(Calendar.MILLISECOND, 20, MILLIS_FORMAT),
        TEN_MILLIS(Calendar.MILLISECOND, 10, MILLIS_FORMAT),
        FIVE_MILLIS(Calendar.MILLISECOND, 5, MILLIS_FORMAT),
        ONE_MILLI(Calendar.MILLISECOND, 1, MILLIS_FORMAT);


        StepSize(int field, int amount, String format) {
            this.field = field;
            this.amount = amount;
            this.format = format;
        }

        final int field;
        final int amount;
        final String format;

    }


    @Override
    public String label(String format, Number value) {
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(format, locale);
        return dateFormat.format(new Date(value.longValue()));
    }
    
    
    @Override
    protected void compute(Number min, Number max) {
        Calendar low = Calendar.getInstance();
        low.setTimeInMillis(min.longValue());
        Calendar high = Calendar.getInstance();
        high.setTimeInMillis(max.longValue());
        StepSize stepSize = determineStepSize(low, high);
        adjust(low, stepSize);
        round(low, stepSize);
        List<Number> primarySet = new ArrayList<>();
        List<Number> secondarySet = new ArrayList<>();
        primarySet.add(low.getTimeInMillis());
        boolean ready = false;
        while (! ready) {
            low.add(stepSize.field, stepSize.amount);
            primarySet.add(low.getTimeInMillis());
            ready = low.after(high);
        }
        markerLists.add(new MarkerList(primarySet, stepSize.format));
        StepSize secondaryStepSize = secondaryStepSize(stepSize);
        if (secondaryStepSize != null) {
            low.setTimeInMillis(min.longValue());
            adjust(low, secondaryStepSize);
            round(low, secondaryStepSize);
            secondarySet.add(min);
            ready = false;
            while (! ready) {
                low.add(secondaryStepSize.field, secondaryStepSize.amount);
                ready = low.after(high);
                if (! ready) {
                    secondarySet.add(low.getTimeInMillis());
                }
            }
            secondarySet.add(max);
            String format = secondaryStepSize.format;
            if (! format.endsWith(">")) {
                format += ">";
            }
            markerLists.add(new MarkerList(secondarySet, format));
        }
    }


    private static void adjust(Calendar calendar, StepSize stepSize) {
        switch (stepSize.field) {
            case Calendar.YEAR:
                calendar.set(Calendar.DAY_OF_YEAR, calendar.getMinimum(Calendar.DAY_OF_YEAR));
                break;
            case Calendar.MONTH:
                calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
                break;
            case Calendar.WEEK_OF_YEAR:
                int delta = calendar.getActualMinimum(Calendar.DAY_OF_WEEK) - calendar.get(Calendar.DAY_OF_WEEK);
                calendar.add(Calendar.DAY_OF_WEEK, delta);
                calendar.set(Calendar.HOUR_OF_DAY, calendar.getMinimum(Calendar.HOUR_OF_DAY));
                break;
            case Calendar.DATE:
                delta = calendar.getActualMinimum(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
                calendar.add(Calendar.HOUR_OF_DAY, delta);
                break;
            case Calendar.HOUR_OF_DAY:
                calendar.add(Calendar.HOUR_OF_DAY, - (calendar.get(Calendar.HOUR_OF_DAY) % stepSize.amount));
                break;
        }
    }


    private static void round(Calendar calendar, StepSize stepSize) {
        switch (stepSize.field) {
            case Calendar.YEAR:
            case Calendar.MONTH:
            case Calendar.WEEK_OF_YEAR:
            case Calendar.DATE:
            case Calendar.HOUR_OF_DAY:
                //low.set(Calendar.MINUTE, 0); <- This affects DST offset, do not use it
                calendar.add(Calendar.MILLISECOND, - calendar.get(Calendar.MILLISECOND));
                calendar.add(Calendar.SECOND, - calendar.get(Calendar.SECOND));
                calendar.add(Calendar.MINUTE, - calendar.get(Calendar.MINUTE));
                break;
            case Calendar.MINUTE:
                //low.clear(Calendar.SECOND); <- This affects DST offset, do not use it
                long round = stepSize.amount * bka.numeric.Time.MILLIS_PER_MINUTE;
                calendar.setTimeInMillis((calendar.getTimeInMillis() / round) * round);
                break;
            case Calendar.SECOND:
                //low.clear(Calendar.MILLISECOND); <- This affects DST offset, do not use it
                round = stepSize.amount * bka.numeric.Time.MILLIS_PER_SECOND;
                calendar.setTimeInMillis((calendar.getTimeInMillis() / round) * round);
                break;
            case Calendar.MILLISECOND:
                round = stepSize.amount;
                calendar.setTimeInMillis((calendar.getTimeInMillis() / round) * round);
                break;
        }
    }


    private StepSize determineStepSize(Calendar low, Calendar high) {
        Calendar test = Calendar.getInstance();
        StepSize[] sizes = getStepSizes();
        StepSize stepSize = sizes[0];
        for (StepSize size : sizes) {
            test.setTimeInMillis(low.getTimeInMillis());
            test.add(size.field, size.amount * MAX_GRID_AREAS);
            if (! test.after(high)) {
                return stepSize;
            }
            stepSize = size;
        }
        return stepSize;
    }


    private static StepSize secondaryStepSize(StepSize primary) {
        switch (primary.field) {
            case Calendar.MILLISECOND:
                return StepSize.ONE_HOUR;
            case Calendar.SECOND:
            case Calendar.MINUTE:
            case Calendar.HOUR_OF_DAY:
                return StepSize.ONE_DAY;
            default:
                return null;
        }
    }

    
    protected StepSize[] getStepSizes() {
        return StepSize.values();
    }


    private static final int MAX_GRID_AREAS = 11;

}
