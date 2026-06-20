package com.example.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class InventoryPage extends BasePage {

    private final By pageTitle = By.className("title");
    private final By productItems = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get inventory page title")
    public String getPageTitle() {
        return getText(pageTitle);
    }

    @Step("Get number of products listed")
    public int getProductCount() {
        return count(productItems);
    }

    @Step("Get all product names")
    public List<String> getProductNames() {
        return getTextOfAll(productNames);
    }

    @Step("Check product '{name}' is displayed")
    public boolean isProductDisplayed(String name) {
        return getProductNames().contains(name);
    }
}
