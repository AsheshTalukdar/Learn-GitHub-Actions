package com.example.tests;

import com.example.base.BaseTest;
import com.example.pages.InventoryPage;
import com.example.pages.LoginPage;
import com.example.utils.ConfigReader;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

@Epic("Sauce Demo")
@Feature("Login & Inventory")
public class LoginTest extends BaseTest {

    @Test(description = "Standard user can log in and sees the expected inventory")
    @Story("Successful login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Logs in with a valid user and asserts the inventory page title, "
            + "product count, and that a known product is listed.")
    public void standardUserSeesInventoryAfterLogin() {
        LoginPage loginPage = new LoginPage(driver);

        InventoryPage inventoryPage = loginPage.login(
                ConfigReader.getUsername(),
                ConfigReader.getPassword()
        );

        Assert.assertEquals(inventoryPage.getPageTitle(), "Products",
                "Inventory page title is wrong");

        List<String> productNames = inventoryPage.getProductNames();
        Assert.assertEquals(productNames.size(), 6,
                "Expected 6 products on the inventory page");

        Assert.assertTrue(inventoryPage.isProductDisplayed("Sauce Labs Backpack"),
                "Expected 'Sauce Labs Backpack' to be in the product list");
    }

    @Test(description = "Locked out user sees an error message")
    @Story("Failed login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Logs in with the locked_out_user and asserts the correct error is shown.")
    public void lockedOutUserSeesError() {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login("locked_out_user", ConfigReader.getPassword());

        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("locked out"),
                "Expected a 'locked out' error message but got: " + error);
    }
}
