package bot;

import org.openqa.selenium.*;

import java.util.Random;

import static java.lang.Thread.sleep;

public class InstagramBot {
    public boolean isCancelled = false;
    private final static String LOGINBUTTON = "/html/body/div[1]/section/main/div/div/div[1]/div/form/div/div[3]/button/div";
    private final static String ALLOWBUTTON = "/html/body/div[4]/div/div/div/div[3]/button[2]";
    private final static String NIE_TERAZ_BUTTON = "/html/body/div[1]/section/main/div/div/div/div/button";
    private final static String HEARTBUTTON = "//span[contains(@class, 'fr66n')]/button[contains(@class, 'wpO6b ')]/div/span/*[local-name()='svg' and @class='_8-yf5 ' and @fill='#262626']";
    private final static String HEARTBUTTON_FILLED = "//span[contains(@class, 'fr66n')]/button[contains(@class, 'wpO6b ')]/div/span/*[local-name()='svg' and @class='_8-yf5 ' and @fill='#ed4956']";
    private final static String FIRST_PICTURE = "/html/body/div[1]/section/main/article/div[1]/div/div/div[1]/div[1]/a/div[1]/div[2]";
    private final static String NEXT_BUTTON = "/html/body/div[5]/div[1]/div/div/a";
    private final static String AFTER_FIRST_BUTTON = "/html/body/div[5]/div[1]/div/div/a[2]";
    private final static String COOKIES_ALLOW = "/html/body/div[2]/div/div/div/div[2]/button[1]";


    public void startBot(WebDriver webDriver, int howMany, String username, String userPassword, int max, int min) {
        JavascriptExecutor js = executeScript(webDriver);
        login(webDriver,username,userPassword);

        int n = 0;
        while (n != howMany && isCancelled) {

            boolean isExists = true;
            try {
                webDriver.findElement(By.xpath(HEARTBUTTON));
            } catch (NoSuchElementException e) {
                isExists = false;
            }

            boolean isAlreadyLiked = false;
            try {
                webDriver.findElement(By.xpath(HEARTBUTTON_FILLED));
                isAlreadyLiked = true;
            } catch (NoSuchElementException e) {
            }

            WebElement element;
            if (isExists) {
                this.pause(getRandom(max, min));
                element = webDriver.findElement(By.xpath(HEARTBUTTON));
                js.executeScript("arguments[0].scrollIntoView(false);", element);
                element.click();
                n++;
                System.out.println("Post Liked, sum = " + n);
            } else if (isAlreadyLiked){
                this.pause(2000);
                System.out.println("Post already Liked, scrolling down ");
                js.executeScript("javascript:window.scrollBy(0,800)");
            } else {
                System.out.println("Can not find element in the DOM");
            }
        }
    }

    public void startBotByHashTag(WebDriver webDriver, int howMany, String username, String userPassword, int max, int min, String hashtag) {
        JavascriptExecutor js = executeScript(webDriver);
        login(webDriver,username,userPassword);

        this.pause(1000);
        webDriver.get("https://www.instagram.com/explore/tags/" + hashtag + "/");
        this.pause(2000);
        WebElement firstPicture = webDriver.findElement(By.xpath(FIRST_PICTURE));
        firstPicture.click();
        this.pause(1000);

        int howManyLiked = 0;
        int howManyForward = 0;
        while (howManyLiked != howMany && isCancelled) {

            boolean isExists = true;
            try {
                webDriver.findElement(By.xpath(HEARTBUTTON));
            } catch (NoSuchElementException e) {
                isExists = false;
            }

            boolean isAlreadyLiked = false;
            try {
                webDriver.findElement(By.xpath(HEARTBUTTON_FILLED));
                isAlreadyLiked = true;
            } catch (NoSuchElementException e) {
            }

            WebElement element;
            if (isExists) {
                this.pause(getRandom(max, min));
                element = webDriver.findElement(By.xpath(HEARTBUTTON));
                element.click();
                howManyLiked++;
                System.out.println("Post Liked, sum = " + howManyLiked);
            } else if(isAlreadyLiked)  {
                System.out.println("Post already Liked, scrolling down ");
            } else {
                System.out.println("Can not find element in the DOM");
            }
            pause(2000);

            WebElement nextButton;
            if (howManyForward == 0) {
                nextButton = webDriver.findElement(By.xpath(NEXT_BUTTON));
            } else {
                nextButton = webDriver.findElement(By.xpath(AFTER_FIRST_BUTTON));
            }
            nextButton.click();
            howManyForward++;
            pause(1000);
        }
    }

    private JavascriptExecutor executeScript(WebDriver webDriver){
        return (JavascriptExecutor) webDriver;
    }

    private void login(WebDriver webDriver,String username,String userPassword) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        webDriver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");

        this.pause(1000);
        webDriver.findElement(By.xpath(COOKIES_ALLOW)).click();
        webDriver.findElement(By.name("username")).sendKeys(username);
        this.pause(1000);
        webDriver.findElement(By.name("password")).sendKeys(userPassword);
        webDriver.findElement(By.xpath(LOGINBUTTON)).click();
        this.pause(3000);
        try {

            webDriver.findElement(By.xpath(NIE_TERAZ_BUTTON)).click();
        } catch (NullPointerException e){
            System.out.println(" element NIE_TERAZ_BUTTON not found ");
        }
        this.pause(3000);
        try {
            WebElement button2 = webDriver.findElement(By.xpath(ALLOWBUTTON));
            button2.click();
        } catch (NullPointerException e){
            System.out.println(" element ALLOWBUTTON not found ");
        }
    }

    private void pause(int time) {
        try {
            sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getRandom(int max, int min) {
        Random rn = new Random();
        int random = rn.nextInt((max - min) + 1) + min;
        System.out.println(random + " seconds to next like");
        return random * 1000;
    }
}
