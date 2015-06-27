package sepm.ss15.grp16.persistence.dao.music.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import sepm.ss15.grp16.entity.music.Playlist;
import sepm.ss15.grp16.persistence.dao.DAO;
import sepm.ss15.grp16.persistence.dao.music.AbstractMusicDAOTest;
import sepm.ss15.grp16.persistence.dao.music.MusicDAO;

/**
 * Author: Lukas
 * Date: 04.06.2015
 */
@RunWith(SpringJUnit4ClassRunner.class) @ContextConfiguration("classpath:spring-config-test.xml")
@TestExecutionListeners(inheritListeners = false, listeners = {DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class})
public class MusicDAOTestImpl extends AbstractMusicDAOTest {

    @Autowired
    private MusicDAO musicDAO;

    @Override
    public DAO<Playlist> getDAO() {
        return musicDAO;
    }
}
