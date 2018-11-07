package com.gerasimchuk.controllers;


import com.gerasimchuk.entities.User;
import com.gerasimchuk.repositories.UserRepository;
import com.gerasimchuk.services.impls.AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * User Controller
 *
 * @author Reaper
 * @version 1.0
 */
@Controller
public class UserController {

    private UserRepository userRepository;
    private AdminController adminController;
    private DriverController driverController;
    private ManagerController managerController;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UserController.class);

    private String defineUrlForLoggedUser(){
        LOGGER.info("Controller: UserController, metod = defineUrlForLoggedUser");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.getByPersonalNumber(authentication.getName());
        if (user == null) {
            LOGGER.info("User not authorized; definedUrl is null");
            return null;
        }
        if (user.getDriver() != null){
            LOGGER.info("Authorized user is driver");
            return "/drivermainpage";
        }
        if (user.getManager() != null){
            LOGGER.info("Authorized user is manager");
            return "/managermainpage";
        }
        if (user.getAdmin() != null){
            LOGGER.info("Authorized user is admin");
            return "/adminmainpage";
        }
        return null;
    }

    /**
     * Instantiates a new User controller.
     *
     * @param userRepository    the user repository
     * @param adminController   the admin controller
     * @param driverController  the driver controller
     * @param managerController the manager controller
     */
    @Autowired
    public UserController(UserRepository userRepository, AdminController adminController, DriverController driverController, ManagerController managerController) {
        this.userRepository = userRepository;
        this.adminController = adminController;
        this.driverController = driverController;
        this.managerController = managerController;
    }

    /**
     * Index string.
     *
     * @param ui the ui
     * @return the string
     */
    @RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
    public String index(Model ui) {
        LOGGER.info("Controller: UserController, metod = index,  action = \"/\", request = GET");
        String url = defineUrlForLoggedUser();
        if (url == null) return "/login";
        if (url.equals("/adminmainpage")) {
            adminController.setUpAdminPageAttributes(ui);
            return "/admin/adminmainpage";
        }
        if (url.equals("/drivermainpage")){
            return driverController.setDriverMainPageAttributes(ui);
        }
        if (url.equals("/managermainpage")){
            return managerController.setManagerMainPageAttributes(ui);
        }
        ui.addAttribute("actionFailed", "Can not define logged user");
        return "failure";
    }

    /**
     * Login string.
     *
     * @param error the error
     * @param ui    the ui
     * @return the string
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "login_error", required = false)String error, Model ui){
        LOGGER.info("Controller: UserController, metod = login,  action = \"/login\", request = GET");
        if (!error.isEmpty()){
            LOGGER.error("Controller: UserController, metod = login, error: wrong personal number or password");
            ui.addAttribute("actionFailed", "Wrong personal number or password!");
            return "failure";
        }
        String url = defineUrlForLoggedUser();
        if (url == null) return "/login";
        return url;
    }

    /**
     * Login post string.
     *
     * @param error the error
     * @param ui    the ui
     * @return the string
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPost(@RequestParam(value = "login_error", required = false)String error, Model ui){
        LOGGER.info("Controller: UserController, metod = login,  action = \"/login\", request = POST");
        if (error!=null){
            LOGGER.error("Login error during executing login method");
            ui.addAttribute("actionFailed", "Login error during executing login method!"); //todo: message about error while log in
            return "failure";
        }
        return "/login";
    }


    /**
     * Login error string.
     *
     * @return the string
     */
    @RequestMapping(value = "/loginerror", method = RequestMethod.GET)
    public String loginError(){
        LOGGER.info("Controller: UserController, metod = loginError,  action = \"/loginerror\", request = GET");
        return "loginerror";
    }
}
