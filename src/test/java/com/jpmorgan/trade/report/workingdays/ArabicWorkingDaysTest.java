package com.jpmorgan.trade.report.workingdays;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.trade.report.workingdays.ArabicWorkingDays;
import com.jpmorgan.trade.report.workingdays.IWorkingDays;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class ArabicWorkingDaysTest {
    private IWorkingDays workingDays;
    @Before
    public void setUp() throws Exception {
        workingDays = ArabicWorkingDays.getInstance();
    }

    @Test
    public void testFindFirstWorkingDate_Sunday() throws Exception {
        final LocalDate aSunday = LocalDate.of(2018, 12, 2);

        // should return the same, since Sunday is a working day in Arabia
        assertEquals(aSunday, workingDays.findFirstWorkingDate(aSunday));
    }

    @Test
    public void testFindFirstWorkingDate_Thursday() throws Exception {
        final LocalDate aThursday = LocalDate.of(2018, 11, 29);

        // should return the same, since Thursday is a working day in Arabia
        assertEquals(aThursday, workingDays.findFirstWorkingDate(aThursday));
    }

    @Test
    public void testFindFirstWorkingDate_Friday() throws Exception {
        final LocalDate aFriday = LocalDate.of(2018, 11, 30);

        // should return the first Sunday (2/12/2018), since Friday is not a working day
        assertEquals(LocalDate.of(2018, 12, 2), workingDays.findFirstWorkingDate(aFriday));
    }

    @Test
    public void testFindFirstWorkingDate_Saturday() throws Exception {
        final LocalDate aSaturday = LocalDate.of(2018, 12, 1);

        // should return the first Sunday (2/12/2018), since Saturday is not a working day
        assertEquals(LocalDate.of(2018, 12, 2), workingDays.findFirstWorkingDate(aSaturday));
    }

}