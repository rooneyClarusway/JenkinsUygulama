package com.vytrack.pages;

import com.vytrack.utilities.ConfigurationReader;
import com.vytrack.utilities.Driver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends BasePage{

    @FindBy(id = "prependedInput")
    public WebElement userNameInput;

    @FindBy(id = "prependedInput2")
    public WebElement passwordInput;

    @FindBy(id = "_submit")
    public WebElement loginButton;

    @FindBy(css = "[class='alert alert-error']")
    public WebElement warningMessage;

    public LoginPage() {

        PageFactory.initElements(Driver.get(),this);
    }


    public void login(String userName, String password){
        userNameInput.sendKeys(userName);

        passwordInput.sendKeys(password, Keys.ENTER);
    }

    public void login(String role){
      String userName="";
      String password=ConfigurationReader.getProperty("password");
      switch (role){
          case "driver": userName= ConfigurationReader.getProperty("driver.username");
          break;
          case "store manager":
              userName=ConfigurationReader.getProperty("store.manager.username");
          break;
          case "sales manager":
              userName=ConfigurationReader.getProperty("sales.manager.username");
           break;
          default:
              throw new RuntimeException("Invalid role");

      }
      login(userName,password);
    }












}
