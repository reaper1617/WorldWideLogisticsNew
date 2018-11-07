package com.gerasimchuk.services.impls;

import com.gerasimchuk.entities.Manager;
import com.gerasimchuk.entities.User;
import com.gerasimchuk.repositories.ManagerRepository;
import com.gerasimchuk.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
@WebAppConfiguration
public class CustomUserDetailsServiceTest {

//    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ManagerRepository managerRepository;


    @Before
    public void setUp() throws Exception {
        System.out.println("in setup!");
        customUserDetailsService = new CustomUserDetailsService(userRepository);
    }

    @Test
    public void loadUserByUsername() {
        Manager manager = managerRepository.create();
        User user = userRepository.create("unitTestUser","unitTestUser","unitTestUser","dhcbf64732f","password",null,manager,null);
        UserDetails res = customUserDetailsService.loadUserByUsername("dhcbf64732f");
        String usernameByUserDetails = res.getUsername();
        boolean namesEqual = (user.getPersonalNumber().equals(usernameByUserDetails));
        userRepository.remove(user.getId());
        managerRepository.remove(manager.getId());
        assertTrue(namesEqual);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsernameNullInput() {
        UserDetails res = customUserDetailsService.loadUserByUsername(null);
    }
}