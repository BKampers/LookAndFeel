/*
** © Bart Kampers
*/

package bka.swing.chart.grid;

import java.util.*;
import java.util.concurrent.*;
import org.junit.*;
import static org.junit.Assert.*;


public class GridTest {


    @Test
    public void numberGridTest() {
        final TestRange[] testRanges = {
            new TestRange(1, 20, new String[] { "0", "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22" }),
            new TestRange(0.0, 250.0, new String[] { "0", "25", "50", "75", "100", "125", "150", "175", "200", "225", "250", "275" }),
            new TestRange(0.0, 25.0, new String[] { "0.0", "2.5", "5.0", "7.5", "10.0", "12.5", "15.0", "17.5", "20.0", "22.5", "25.0", "27.5" }),
            new TestRange(0.0, 2.5, new String[] { "0.00", "0.25", "0.50", "0.75", "1.00", "1.25", "1.50", "1.75", "2.00", "2.25", "2.50", "2.75" }),
            new TestRange(0.0, 0.25, new String[] { "0.000", "0.025", "0.050", "0.075", "0.100", "0.125", "0.150", "0.175", "0.200", "0.225", "0.250", "0.275" })
        };
        NumberGrid grid = new NumberGrid();
        testGrid(grid, testRanges);
    }


    @Test
    public void integerGridTest() {
        final TestRange[] testRanges = {
            new TestRange(1, 20, new String[] { "0", "2", "4", "6", "8", "10", "12", "14", "16", "18", "20", "22" }),
            new TestRange(0, 20, new String[] { "0", "5", "10", "15", "20", "25" }),
            new TestRange(0, 200, new String[] { "0", "50", "100", "150", "200", "250" })
        };
        IntegerGrid grid = new IntegerGrid();
        testGrid(grid, testRanges);
    }


    @Test
    public void timestampGridTest() {
        final TestRange[] testRanges = {
            new TestRange(timestamp(1970, 0, 1), timestamp(1970, 11, 31), new String[] { "1 Jan 1970", "1 Apr 1970", "1 Jul 1970", "1 Oct 1970", "1 Jan 1971" }),
            new TestRange(TimeUnit.SECONDS.toMillis(1), TimeUnit.SECONDS.toMillis(4), new String[][] {
                { "01.000", "01.500", "02.000", "02.500", "03.000", "03.500", "04.000", "04.500" },
                { "1:00>" , "1:00>"}
            }),
            new TestRange(TimeUnit.MINUTES.toMillis(1), TimeUnit.MINUTES.toMillis(10), new String[][] {
                { "1:01", "1:02", "1:03", "1:04", "1:05", "1:06", "1:07", "1:08", "1:09", "1:10", "1:11" },
                { "1 Jan>" , "1 Jan>"}
            }),
            new TestRange(0, 1000, new String[][] {
                { "00.000", "00.100", "00.200", "00.300", "00.400", "00.500", "00.600", "00.700", "00.800", "00.900", "01.000", "01.100" },
                { "1:00>", "1:00>" }
            })
        };
        TimestampGrid grid = new TimestampGrid();
        testGrid(grid, testRanges);
    }


    @Test
    public void mapGridTest() {
        final TestRange[] testRanges = {
            new TestRange(1, 3, new String[] { "one", "two", "three" })
        };
        Map<Number, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        MapGrid grid = new MapGrid(map);
        testGrid(grid, testRanges);
    }


    private static long timestamp(int year, int month, int date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);
        return calendar.getTimeInMillis();
    }


    private void testGrid(Grid grid, TestRange[] testRanges) {
        for (TestRange range : testRanges) {
            grid.initialize(range.getMin(), range.getMax());
            assertLabelsEqual(range.getExpectedLabels(), grid.getMarkerLists());
        }
    }

    
    private void assertLabelsEqual(String[][] expectedLabels, List<Grid.MarkerList> markerLists) {
        assertEquals(expectedLabels.length, markerLists.size());
        for (int row = 0; row < markerLists.size(); ++row) {
            assertLabelsEqual(expectedLabels[row], markerLists.get(row));
        }
    }


    private void assertLabelsEqual(String[] expectedLabels, Grid.MarkerList markerList) {
        assertEquals(expectedLabels.length, markerList.getValues().size());
        for (int col = 0; col < markerList.getValues().size(); ++col) {
            assertEquals(expectedLabels[col], markerList.getLabel(Locale.ENGLISH, markerList.getValues().get(col)));
        }
    }


    private class TestRange {

        public TestRange(Number min, Number max, String[] expectedLabels) {
            this(min, max, new String[][] { expectedLabels });
        }

        public TestRange(Number min, Number max, String[][] expectedLabels) {
            this.min = min;
            this.max = max;
            this.expectedLabels = expectedLabels;
        }

        public TestRange(Date min, Date max, String[] expectedLabels) {
            this(min.getTime(), max.getTime(), expectedLabels);
        }

        public Number getMin() {
            return min;
        }

        public Number getMax() {
            return max;
        }

        public String[][] getExpectedLabels() {
            return expectedLabels;
        }

        private final Number min;
        private final Number max;
        private final String[][] expectedLabels;
    }

}
