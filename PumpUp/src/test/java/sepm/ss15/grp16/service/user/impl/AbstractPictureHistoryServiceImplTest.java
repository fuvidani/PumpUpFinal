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
import sepm.ss15.grp16.persistence.dao.user.PictureHistoryDAO;
import sepm.ss15.grp16.service.user.AbstractPictureHistoryServiceTest;
import sepm.ss15.grp16.service.user.PictureHistoryService;

/**
 * This class is used to test the PictureHistoryServiceImpl
 *
 * @author Michael Sober
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) @ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class AbstractPictureHistoryServiceImplTest extends AbstractPictureHistoryServiceTest {

    @Autowired
    public void setPictureHistoryService(PictureHistoryService pictureHistoryService) {
        this.pictureHistoryService = pictureHistoryService;
    }

    @Autowired
    public void setMockedPictureHistoryDAO(PictureHistoryDAO mockedPictureHistoryDAO) {
        this.mockedPictureHistoryDAO = mockedPictureHistoryDAO;
    }

    @Before
    public void setUp() throws Exception {
        Mockito.reset(mockedPictureHistoryDAO);
    }

}
