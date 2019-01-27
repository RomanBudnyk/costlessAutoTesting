package costlessTest.appTests;

import costlessTest.elements.*;
import costlessTest.helpTests.HelpClass;
import costlessTest.helpTests.HelpMethods;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static costlessTest.helpTests.HelpMethods.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class AppTests extends HelpClass {
    private int rand = (int) (Math.random() * 1000);
    private String name = "Testero Rom " + rand;
    private String email = "testerorom+costless" + rand + "@gmail.com";

    //регистрация
    @Test
    public void registration() throws InterruptedException {
        tutorial();
        d.findElementById(WelcomeScreen.CREATE_ACC_BTN_ID).click();
        Thread.sleep(300);
        HelpMethods.scrollUp(548, 1528, 548, 428);
        d.findElementById(SignUpScreen.MALE_GENDER_BTN_ID).click();
        d.findElementById(SignUpScreen.NAME_FLD_ID).sendKeys(name);
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(SignUpScreen.DATE_OF_BIRTH_WDGT_ID))).isDisplayed();
//        d.findElementById(SignUpScreen.OK_CALEND_BTN_ID);
        d.findElementById(SignUpScreen.EMAIL_FLD_ID).sendKeys(email);
        d.findElementById(SignUpScreen.PHONE_FLD_ID).sendKeys("+380631112233");
        d.findElementById(SignUpScreen.PASS_FLD_ID).sendKeys("qwerty");
        d.findElementById(SignUpScreen.CONF_PASS_FLD_ID).sendKeys("qwerty");

        d.findElementById(SignUpScreen.TERMS_CHKBX_ID).click();
        d.findElementById(SignUpScreen.NEWS_CHKBX_ID).click();
        d.findElementById(SignUpScreen.SIGN_UP_BTN_ID).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DashboardScreen.TOOLBAR_DASHBOARD_ID))).isDisplayed());
    }

    //положить товар в корзину
    @Test //(dependsOnMethods = {"tutorial", "loginViaEmail"})
    public void putGoodIntoBasket() {
        searchProducts();
        int init = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.COUNTER_OF_PROD_IN_CART_ID))).getText());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.ADD_ITEM_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.SEARCH_FIELD_ID))).sendKeys("milk");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.PRODUCT_BTN_ID))).click();
        d.findElementById(QuickShoppingScreen.DONE_SEARCH_BTN_ID).click();
        int after = Integer.parseInt(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.COUNTER_OF_PROD_IN_CART_ID))).getText());
        assertEquals(after - init, 1);
        System.out.println("Product was successfully added.");
    }

    //создать шоплист с кастомным товаром
    @Test //(dependsOnMethods = {"tutorial", "loginViaEmail"})
    public void createShopListWithCustomProduct() {
        loginViaEmail();
        helpMethodForShoplistFlow("custom product");
    }

    //создать шоплист с товаром из БД
    @Test
    public void createShopListWithDBProduct() {
        loginViaEmail();
        helpMethodForShoplistFlow("milka");
    }


    //добавить продукт с акцией в корзину
    @Test
    public void addDealProductIntoCart() {
        loginViaEmail();
        d.findElementById(DashboardScreen.DEALS_BTN_ID).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DealsScreen.PRODUCT_NAME_ID))).click();
        try {
            d.findElementById(DealsScreen.ADD_TO_CART_BTN_ID).click();
        } catch (NoSuchElementException e) {
            System.out.println("No button to add product into cart.");
        }
        System.out.println("Deal product was successfully added into cart.");
    }

    //добавить пользовательскую карту
    @Test
    public void addCustomDiscountCard() throws InterruptedException {
        loginViaEmail();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DashboardScreen.DISCOUNT_CARDS_BTN_ID))).click();
        try {
            d.findElementById(DiscountCardsScreen.TUTORIAL_ID).click();
            System.out.println("Tutorial was shown and skipped.");
        } catch (Exception e) {
            System.out.println("No tutorial was displayed.");
        }
        Thread.sleep(500);
        try {
            d.findElementById(DiscountCardsScreen.ADD_CARD_BIG_BTN_ID).click();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.ADD_CARD_BIG_BTN_ID))).click();
        } catch (NoSuchElementException e) {
            System.out.println("There are some cards already present.");
            d.findElementById(DiscountCardsScreen.ADD_CARD_SMALL_BTN_ID).click();
//            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.ADD_CARD_SMALL_BTN_ID))).click();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.CUSTOM_CARD_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.MANUAL_ENTER_CARD_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.support.v7.widget.LinearLayoutCompat"))).isDisplayed();
        d.findElementById(DiscountCardsScreen.SHOP_NAME_FLD_ID).sendKeys("Test shop name" + (int) (Math.random() * 1000));
        d.findElementById(DiscountCardsScreen.CARD_NUMBER_FLD_ID).sendKeys("" + (int) (Math.random() * 100000));
        d.findElementById(DiscountCardsScreen.DISCOUNT_FLD_ID).sendKeys("" + (int) (Math.random() * 100));
        d.findElementById(DiscountCardsScreen.FRONT_PHOTO_BTN_ID).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.TAKE_PHOTO_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.BACK_PHOTO_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.TAKE_PHOTO_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(DiscountCardsScreen.DONE_BTN_ID))).click();
    }

    //отправка фибека
    @Test
    public void sendFeedback() {
        loginViaEmail();
        HelpMethods.scrollDrawer();
        d.findElementById(Drawer.FEEDBACK_BTN_ID).click();
        d.findElementById(FeedbackScreen.SUBJECT_DROP_ID).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(FeedbackScreen.CHOOSE_BTN_ID))).click();
        d.findElementById(FeedbackScreen.MESSAGE_FLD_ID).sendKeys("Some text here. This is a test from IDEA.");
//        d.findElementById(FeedbackScreen.EMAIL_FLD_ID).sendKeys(email);
        d.findElementById(FeedbackScreen.SUBMIT_BTN_ID).click();
        assertEquals(d.findElementById(FeedbackScreen.MESSAGE_FLD_ID).getText(), "");
        System.out.println("Feedback was sent successfully");
    }

    //клацаем по всем свитчам для получения пушей
    @Test
    public void clickOnPushSwitches() {
        loginViaEmail();
        scrollDrawer();
        d.findElementById(Drawer.SETTINGS_BTN_ID).click();
        d.findElementById(SettingsScreen.NOTIF_BTN_ID).click();
        d.findElementById(SettingsScreen.INFO_NOTIF_BTN_ID).click();
        d.findElementById(SettingsScreen.DEALS_NOTIF_BTN_ID).click();
        d.findElementById(SettingsScreen.POINTS_NOTIF_BTN_ID).click();
        d.findElementById(SettingsScreen.BONUSES_NOTIF_BTN_ID).click();
        d.findElementById(SettingsScreen.CONTRIBUTION_NOTIF_BTN_ID).click();
        d.findElementById(SettingsScreen.SECURITY_NOTIF_BTN_ID).click();
        d.findElementById(SettingsScreen.GEO_NOTIF_BTN_ID).click();
        System.out.println("All switches are present and clicked.");
    }

    //читать ЧАВО
    @Test
    public void readFAQ() {
        loginViaEmail();
        scrollDrawer();
        d.findElementById(Drawer.SETTINGS_BTN_ID).click();
        d.findElementById(SettingsScreen.FAQ_BTN_ID).click();
        d.findElementById(SettingsScreen.QUESTION_FAQ_BTN_ID).click();
        assertTrue(d.findElementById(SettingsScreen.ANSWER_FAQ_AREA_ID).isDisplayed());
        System.out.println("FAQ was displayed and read successfully.");
    }

    //читать "условия и положения"
    @Test
    public void readTerms() {
        loginViaEmail();
        scrollDrawer();
        d.findElementById(Drawer.SETTINGS_BTN_ID).click();
        d.findElementById(SettingsScreen.TERMS_BTN_ID).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SettingsScreen.WEBWIEW_XPATH))).isDisplayed());
        System.out.println("Terms were viewed and read successfully.");
    }

    //читать "политика конфиденциальности"
    @Test
    public void readPrivacyPolicy() {
        loginViaEmail();
        scrollDrawer();
        d.findElementById(Drawer.SETTINGS_BTN_ID).click();
        d.findElementById(SettingsScreen.PRIVACY_BTN_ID).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SettingsScreen.WEBWIEW_XPATH))).isDisplayed());
        System.out.println("Privacy policy was viewed and read successfully.");
    }

    //читать "о нас"
    @Test
    public void readAboutUs() {
        loginViaEmail();
        scrollDrawer();
        d.findElementById(Drawer.SETTINGS_BTN_ID).click();
        d.findElementById(SettingsScreen.ABOUT_BTN_ID).click();
        assertTrue(d.findElementById(SettingsScreen.ABOUT_US_SC_BTN_ID).isDisplayed());
        System.out.println("About us was viewed and read successfully.");
    }

    //добавляем чек
    @Test
    public void addReceiptFromDrawer() {
        loginViaEmail();
        scrollDrawer();
        d.findElementById(Drawer.ADD_RECEIPT_BTN_ID).click();
        d.findElementById(AddReceiptScreen.TAKE_PHOTO_BTN_ID).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(AddReceiptScreen.CAMERA_TAKE_PHOTO_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(AddReceiptScreen.ROTATE_BTN_ID))).click();
        d.findElementById(AddReceiptScreen.ACCEPT_BTN_ID).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(AddReceiptScreen.PHOTO_PIC_ID))).isDisplayed());
        assertTrue(d.findElementById(AddReceiptScreen.PHOTO_PIC_ID).isDisplayed());
        d.findElementById(AddReceiptScreen.SUBMIT_BTN_ID).click();
        assertTrue(wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(AddReceiptScreen.PHOTO_PIC_ID))));
        System.out.println("Receipt was successfully sent.");
    }

    //логаут
    @Test
    public void logout() {
        loginViaEmail();
        HelpMethods.scrollDrawer();
        d.findElementById(Drawer.SIGNOUT_BTN_ID).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(WelcomeScreen.LOGO_ID))).isDisplayed());
        System.out.println("Logout was successful.");
    }

    //добавить продукт в избранные
    @Test
    public void addToFavorites() {
        searchProducts();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.ADD_ITEM_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.SEARCH_FIELD_ID))).sendKeys("milk");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.SHOPS_WITH_PRODUCT_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.FAVOURITE_BTN_ID))).click();
        assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(QuickShoppingScreen.PRODUCT_IMG_ID))).isDisplayed());
        System.out.println("Product was added to favourites successfully.");
    }

    //удалить с избранного
    @Test
    public void deleteFromFavourites() throws InterruptedException {
        loginViaEmail();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("android.widget.ImageButton"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Drawer.DRAWER_BTN_ID))).isEnabled();
        d.findElementById(Drawer.FAVOURITES_BTN_ID).click();
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
                    "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
                    "android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.view.ViewGroup/" +
                    "android.view.ViewGroup/android.widget.ScrollView/android.widget.FrameLayout/android.view.ViewGroup/" +
                    "android.view.ViewGroup/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[1]/" +
                    "android.view.ViewGroup"))).isDisplayed();
        } catch (TimeoutException e) {
            System.err.println("No products in favourites.");
            return;
        }
        int initCountOfProductsInFavourites = countOfProductsInFavourites();
        action.longPress(new LongPressOptions().withElement(new ElementOption().withElement(d.findElement(By.id(FavouritesScreen.PRODUCT_BTN_ID))))).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(FavouritesScreen.DELETE_PRODUCT_BTN_ID))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(FavouritesScreen.CONFIRM_DELETE_PRODUCT_BTN_ID))).click();
        Thread.sleep(3000);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/" +
                    "android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/" +
                    "android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.view.ViewGroup/" +
                    "android.view.ViewGroup/android.widget.ScrollView/android.widget.FrameLayout/android.view.ViewGroup/" +
                    "android.view.ViewGroup/android.support.v7.widget.RecyclerView/android.widget.FrameLayout[1]/" +
                    "android.view.ViewGroup"))).isDisplayed();
        } catch (NoSuchElementException e) {
            System.err.println("No products in favourites.");
        }
        int afterCountOfProductsInFavourites = countOfProductsInFavourites();
        assertEquals(initCountOfProductsInFavourites - afterCountOfProductsInFavourites, 1);
    }

    @Test
    public void test() {

    }
}
