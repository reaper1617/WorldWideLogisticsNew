package com.gerasimchuk.services.impls;

import com.gerasimchuk.services.interfaces.SecurityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class SecurityServiceImplTest {

    @Autowired
    private SecurityService securityService;

    @Test
    public void findLoggedInUsername() {
        String name = securityService.findLoggedInUsername();
        assertNull(name);
    }

}