package com.jpmorgan.trade.report.workingdays;

import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.trade.report.workingdays.DefaultWorkingDays;
import com.jpmorgan.trade.report.workingdays.IWorkingDays;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class DefaultWorkingDaysTest {

    private IWorkingDays workingDays;
    @Before
    public void setUp() throws Exception {
        workingDays = DefaultWorkingDays.getInstance();
    }

    @Test
    public void testFindFirstWorkingDate_Monday() throws Exception {
        final LocalDate aMonday = LocalDate.of(2018, 12, 3);

        // should return the same, since Monday is a working by default
        assertEquals(aMonday, workingDays.findFirstWorkingDate(aMonday));
    }

    @Test
    public void testFindFirstWorkingDate_Friday() throws Exception {
        final LocalDate aFriday = LocalDate.of(2018, 11, 30);

        // should return the same, since Friday is a working by default
        assertEquals(aFriday, workingDays.findFirstWorkingDate(aFriday));
    }

    @Test
    public void testFindFirstWorkingDate_Saturday() throws Exception {
        final LocalDate aSaturday = LocalDate.of(2018, 12, 1);

        // should return the first Monday (3/12/2018), since Saturday is not a working day
        assertEquals(LocalDate.of(2018, 12, 3), workingDays.findFirstWorkingDate(aSaturday));
    }

    @Test
    public void testFindFirstWorkingDate_Sunday() throws Exception {
        final LocalDate aSunday = LocalDate.of(2018, 12, 2);

        // should return the first Monday (3/12/2018), since Sunday is not a working day
        assertEquals(LocalDate.of(2018, 12, 3), workingDays.findFirstWorkingDate(aSunday));
    }
}