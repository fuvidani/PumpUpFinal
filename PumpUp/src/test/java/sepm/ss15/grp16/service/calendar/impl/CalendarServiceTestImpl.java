package sepm.ss15.grp16.service.calendar.impl;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.calendar.Appointment;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.calendar.CalendarDAO;
import sepm.ss15.grp16.service.Service;
import sepm.ss15.grp16.service.calendar.AbstractCalendarServiceTest;
import sepm.ss15.grp16.service.calendar.CalendarService;
import sepm.ss15.grp16.service.user.UserService;

/**
 * Created by David on 2015.06.22..
 */

@RunWith(SpringJUnit4ClassRunner.class) @ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class CalendarServiceTestImpl extends AbstractCalendarServiceTest {

    @Autowired
    public void setMockedCalendarDAO(CalendarDAO mockedCalendarDAO) {
        this.mockedCalendarDAO = mockedCalendarDAO;
    }

    @Autowired
    public void setMockedUserService(UserService mockedUserService) {
        this.mockedUserService = mockedUserService;
    }

    @Autowired
    public void setCalendarService(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Before
    public void setUp() throws Exception {
        Mockito.reset(mockedCalendarDAO);
        Mockito.reset(mockedUserService);
    }

    @Override
    public Service<Appointment> getService() {
        return calendarService;
    }

    @Override
    public DAO<Appointment> getMockedDAO() {
        return mockedCalendarDAO;
    }
}
