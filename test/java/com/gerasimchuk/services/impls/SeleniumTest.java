//package com.gerasimchuk.services.impls;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//
//import java.util.concurrent.TimeUnit;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath*:**/testConf.xml"})
//@WebAppConfiguration
//public class SeleniumTest {
//
//
//    @Test
//    public void firstTest(){
//        System.setProperty("webdriver.chrome.driver","C:\\ChromeSeleniumDriver\\chromedriver.exe");
//        WebDriver driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        driver.get("localhost:8080");
//        WebElement loginField = driver.findElement(By.id("text"));
//        loginField.sendKeys("7777777777");
//        WebElement passwordField = driver.findElement(By.id("pwd"));
//        passwordField.sendKeys("password");
//        WebElement loginBtn = driver.findElement(By.xpath("//button[text()='Login']"));
//        loginBtn.click();
//        WebElement loggedUserHeader = driver.findElement(By.id("loggedUserHeader"));
//        String user = loggedUserHeader.getText();
//        String expectedUser = "Admin Admin Admin";
//        boolean res = user.equals("Logged as: " + expectedUser);
//        // logout!!
//        WebElement logoutBtn = driver.findElement(By.xpath("//button[text()='Log out']"));
//        logoutBtn.click();
//        Assert.assertTrue(res);
//    }
//}
