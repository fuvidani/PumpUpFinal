package sepm.ss15.grp16.persistence.dao.training.helper.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.persistence.dao.training.TrainingsplanDAO;
import sepm.ss15.grp16.persistence.dao.training.helper.AbstractSessionHelperDAOTest;
import sepm.ss15.grp16.persistence.dao.training.helper.TrainingsSessionHelperDAO;

/**
 * Author: Lukas
 * Date: 13.05.2015
 */

@RunWith(SpringJUnit4ClassRunner.class) @ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class H2SessionHelperDAOTestImpl extends AbstractSessionHelperDAOTest {

    @Autowired
    TrainingsplanDAO          trainingsplanDAO;
    @Autowired
    TrainingsSessionHelperDAO trainingsSessionHelperDAO;

    @Override
    public TrainingsplanDAO getTrainingsplanDAO() {
        return trainingsplanDAO;
    }

    @Override
    public TrainingsSessionHelperDAO getDAO() {
        return trainingsSessionHelperDAO;
    }
}
