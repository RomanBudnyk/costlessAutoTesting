package costlessTest.helpTests;

import costlessTest.elements.*;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HelpMethods extends HelpClass {
    public static void tutorial() {
        //to think how to do this with the cycle "do-while" (or "while")
        for (int i = 1; i <= 5; i++) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Onboarding.NEXT_BTN_ID))).click();
        }
        d.findElementById(Onboarding.START_BTN_ID).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(WelcomeScreen.LOGO_ID))).isDisplayed());
        System.out.println("The onboarding was successfully watched.");
    }

    public static void loginViaEmail() {
        tutorial();
        d.findElementById(WelcomeScreen.SIGN_IN_BTN_ID).click();
        d.findElementById(SignInScreen.EMAIL_FLD_ID).sendKeys("lp22@i.ua");
        d.findElementById(SignInScreen.PASSWORD_FLD_ID).sendKeys("qwerty");
        d.findElementById(SignInScreen.SHOW_PSWD_BTN_ID).click();
        d.findElementById(SignInScreen.SIGNIN_BTN_ID).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DashboardScreen.TOOLBAR_DASHBOARD_ID))).isDisplayed();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DashboardScreen.TOOLBAR_DASHBOARD_ID))).isDisplayed());
//        assertTrue(d.findElementById(DashboardScreen.TOOLBAR_DASHBOARD_ID).isDisplayed());
        System.out.println("Login via email was successful.");
    }

    public void loginViaGoogle() {
        tutorial();
        d.findElementById(WelcomeScreen.GOOGLE_BTN_ID).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("account_picker"))).isDisplayed();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("account_display_name"))).click();

        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DashboardScreen.TOOLBAR_DASHBOARD_ID))).isDisplayed());

        System.out.println("Login via Google was successful");


    }

//    @Test
    public void loginViaFacebook() {
        tutorial();
        d.findElementById(WelcomeScreen.FACEBOOK_BTN_ID).click();
    }

    public static void helpMethodForShoplistFlow(String product) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DashboardScreen.SHOPPING_LIST_BTN_ID))).click();
//        d.findElementById(DashboardScreen.SHOPPING_LIST_BTN_ID).click();
        try {
            d.findElementById(ShopListScreen.ADD_SHOP_LIST_MAIN_BTN_ID).click();
        } catch (Exception e) {
            System.out.println("Shoplist was already created, new one will be added.");
            d.findElementById(ShopListScreen.ADD_SHOP_LIST_SMALL_BTN_ID).click();
        }
        String shopListName = "TestShopList" + (int) (Math.random() * 1000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ShopListScreen.SHOP_LIST_NAME_FLD_ID))).sendKeys(shopListName);
//        d.findElementById(ShopListScreen.SHOP_LIST_NAME_FLD_ID).sendKeys(shopListName);
        assertTrue(d.findElementById(ShopListScreen.CANCEL_SHOP_LIST_NAME_BTN_ID).isDisplayed());
        d.findElementById(ShopListScreen.ACCEPT_SHOP_LIST_NAME_BTN_ID).click();
        d.findElementById(ShopListScreen.SHOPLIST_BTN_ID).click();
        int initCountOfProducts = Integer.parseInt(
                d.findElementById(ShopListScreen.COUNTER_OF_PROD_ID)
                        .getText()
                        .substring(d.findElementById(ShopListScreen.COUNTER_OF_PROD_ID)
                                .getText()
                                .lastIndexOf(" ") + 1));

        System.out.println("initCountOfProducts = " + initCountOfProducts);
        try {
            d.findElementById(ShopListScreen.ADD_PRODUCT_MAIN_BTN_ID).click();
        } catch (Exception e) {
            System.out.println("Shoplist is not empty, adding new product.");
            d.findElementById(ShopListScreen.ADD_PRODUCT_SMALL_BTN_ID).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ShopListScreen.SEARCH_PROD_FIELD_ID))).sendKeys(product);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ShopListScreen.ADD_PROD_FROM_DB_BTN_XPATH))).click();
        } catch (Exception e) {
            System.out.println("No similar products in DB.");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ShopListScreen.ADD_CUSTOM_PROD_BTN_ID))).click();
        }
        d.findElementById(ShopListScreen.INCREASE_QUANT_BTN_ID).click();
        d.findElementById(ShopListScreen.MEASURES_DROPDOWN_ID).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ShopListScreen.OPENED_DROPDOWN_ID))).isEnabled();
        d.findElement(By.xpath(ShopListScreen.KG_XPATH)).click();
        try {
            d.findElementById(ShopListScreen.PRICE_FLD_ID).sendKeys("5000");
        } catch (NoSuchElementException e) {
            System.out.println("No price field, product from DB.");
        }
        d.findElementById(ShopListScreen.ADD_SELECTED_PROD_BTN_ID).click();
        d.findElementById(ShopListScreen.DONE_BTN_ID).click();
        int afterCountOfProducts = Integer
                .parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(ShopListScreen.COUNTER_OF_PROD_ID)))
                        .getText().substring(d.findElementById(ShopListScreen.COUNTER_OF_PROD_ID)
                                .getText()
                                .lastIndexOf(" ") + 1));
        System.out.println("afterCountOfProucts = " + afterCountOfProducts);
        assertEquals(afterCountOfProducts - initCountOfProducts, 1);
        System.out.println("Custom product was successfully added into the shoplist.");

    }

    public static void searchProducts() {
        loginViaEmail();
        d.findElementById(DashboardScreen.QUICK_SHOPPING_BTN_ID).click();
        try {
            d.findElementById(QuickShoppingScreen.SUB_ACC_BTN_ID).click();
        } catch (Exception e) {
            System.out.println("No subscription window was displayed.");
        }
        try {
            d.findElementById(QuickShoppingScreen.TUT_SIGN_ID).click();
            System.out.println("Tutorial was shown and skipped.");
        } catch (Exception e) {
            System.out.println("No tutorial was displayed.");
        }
    }

    public static void scrollUp(int startX, int startY, int finalX, int finalY) {
        action
                .press(PointOption.point(startX, startY))
                .moveTo(PointOption.point(finalX, finalY))
                .release()
                .perform();
    }

    public static void scrollDrawer() {
//        d.findElementByClassName("android.widget.ImageButton").click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.ImageButton"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Drawer.DRAWER_BTN_ID))).isEnabled();
        action
                .longPress(PointOption.point(274, 1500))
                .moveTo(PointOption.point(247, 600))
                .release()
                .perform();
    }

    public static int countOfProductsInFavourites() {
        int countOfProductsInFavourites = 0;

        try {
            for (int x = 1; ; x++) {
                WebElement webElement = d.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
                        "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
                        "android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.view.ViewGroup/" +
                        "android.view.ViewGroup/android.widget.ScrollView/android.widget.FrameLayout/android.view.ViewGroup/" +
                        "android.view.ViewGroup/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[" + x + "]/" +
                        "android.view.ViewGroup");
                if (webElement.isDisplayed()) {
                    countOfProductsInFavourites++;
                } else {
                    break;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("No more products on favourites. Cont of products in favourite = " + countOfProductsInFavourites);
        }
        return countOfProductsInFavourites;
    }
}
