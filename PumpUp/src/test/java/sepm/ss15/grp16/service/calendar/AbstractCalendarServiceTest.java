package sepm.ss15.grp16.service.calendar;

import org.junit.Test;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.persistence.dao.calendar.CalendarDAO;
import sepm.ss15.grp16.service.AbstractServiceTestMockito;
import sepm.ss15.grp16.service.exception.ServiceException;
import sepm.ss15.grp16.service.exception.ValidationException;
import sepm.ss15.grp16.service.user.UserService;

import java.util.Date;

/**
 * Created by David on 2015.06.22..
 */
public abstract class AbstractCalendarServiceTest extends AbstractServiceTestMockito<Appointment> {

    protected CalendarService calendarService;
    protected UserService     mockedUserService;
    protected CalendarDAO     mockedCalendarDAO;

    @Test
    public void createValid() throws Exception {
        createTest(dummyAppointment());
    }

    @Test(expected = ValidationException.class)
    public void createWithNull() throws Exception {
        createTestFail(null);
    }

    @Test
    public void updateValid() throws Exception {
        updateTest(dummyAppointment());
    }

    @Test(expected = ValidationException.class)
    public void updateWithNull() throws Exception {
        updateTestFail(null);
    }

    @Test
    public void deleteValid() throws Exception {
        deleteTest(dummyAppointment());
    }

    @Test(expected = ValidationException.class)
    public void deleteWithNull() throws Exception {
        deleteTest(null);
    }

    @Test(expected = ServiceException.class)
    public void findAllFail() throws Exception {
        findAllTestFail();
    }

    /**
     * HELPER METHODS
     */

    private Appointment dummyAppointment() throws Exception {

        Appointment appointment = new Appointment(1, new Date(), 4, 3, false, false);
        return appointment;
    }
}
