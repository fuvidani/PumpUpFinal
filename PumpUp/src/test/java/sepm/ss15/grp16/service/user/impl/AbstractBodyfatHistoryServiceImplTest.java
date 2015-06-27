package sepm.ss15.grp16.service.user.impl;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.persistence.dao.user.BodyfatHistoryDAO;
import sepm.ss15.grp16.service.user.AbstractBodyfatHistoryServiceTest;
import sepm.ss15.grp16.service.user.BodyfatHistoryService;

/**
 * This class is used to test the BodyFatHistoryServiceImpl
 *
 * @author Michael Sober
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) @ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class AbstractBodyfatHistoryServiceImplTest extends AbstractBodyfatHistoryServiceTest {

    @Autowired
    public void setBodyfatHistoryService(BodyfatHistoryService bodyfatHistoryService) {
        this.bodyfatHistoryService = bodyfatHistoryService;
    }

    @Autowired
    public void setMockedBodyfatHistoryDAO(BodyfatHistoryDAO mockedBodyfatHistoryDAO) {
        this.mockedBodyfatHistoryDAO = mockedBodyfatHistoryDAO;
    }

    @Before
    public void setUp() throws Exception {
        Mockito.reset(mockedBodyfatHistoryDAO);
    }
}
