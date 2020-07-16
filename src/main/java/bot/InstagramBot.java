package bot;

import org.openqa.selenium.*;

import java.util.Random;

import static java.lang.Thread.sleep;

public class InstagramBot {
    public boolean isCancelled = false;
    public final static String LOGINBUTTON = "/html/body/div[1]/section/main/div/article/div/div[1]/div/form/div[4]/button";
    public final static String ALLOWBUTTON = "/html/body/div[4]/div/div/div/div[3]/button[2]";
    public final static String NIE_TERAZ_BUTTON = "/html/body/div[1]/section/main/div/div/div/div/button";
    public final static String HEARTBUTTON = "//span[contains(@class, 'fr66n')]/button[contains(@class, 'wpO6b ')]/div/*[local-name()='svg' and @class='_8-yf5 ' and @fill='#262626']";
    public final static String FIRST_PICTURE = "/html/body/div[1]/section/main/article/div[1]/div/div/div[1]/div[1]/a/div[1]/div[2]";
    public final static String NEXT_BUTTON = "/html/body/div[4]/div[1]/div/div/a";
    public final static String AFTER_FIRST_BUTTON = "/html/body/div[4]/div[1]/div/div/a[2]";


    public void startBot(WebDriver webDriver, int howMany, String username, String userPassword, int max, int min) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        webDriver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");

        this.pause(1000);
        WebElement webElement = webDriver.findElement(By.name("username"));
        webElement.sendKeys(username);
        this.pause(1000);

        WebElement password = webDriver.findElement(By.name("password"));
        password.sendKeys(userPassword);
        this.pause(0);

        WebElement button1 = webDriver.findElement(By.xpath(LOGINBUTTON));
        button1.click();

        this.pause(3000);
        try {
            WebElement button3 = webDriver.findElement(By.xpath(NIE_TERAZ_BUTTON));
            button3.click();
        } catch (NullPointerException e){
            System.out.println(" element NIE_TERAZ_BUTTON not found ");
            return;
        }
        this.pause(3000);
        try {
        WebElement button2 = webDriver.findElement(By.xpath(ALLOWBUTTON));
        button2.click();
        } catch (NullPointerException e){
            System.out.println(" element ALLOWBUTTON not found ");
            return;
        }

        int n = 0;
        while (n != howMany && isCancelled) {
            try {
                sleep(getRandom(max, min));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean isExists = true;
            try {
                webDriver.findElement(By.xpath(HEARTBUTTON));
            } catch (NoSuchElementException e) {
                isExists = false;
            }

            WebElement element;
            if (isExists) {
                element = webDriver.findElement(By.xpath(HEARTBUTTON));
                js.executeScript("arguments[0].scrollIntoView(false);", element);
                element.click();
                n++;
                System.out.println("Post Liked, sum = " + n);
            } else {
                System.out.println("Post already Liked, scrolling down ");
                js.executeScript("javascript:window.scrollBy(0,800)");
            }
        }
    }

    public void startBotByHashTag(WebDriver webDriver, int howMany, String username, String userPassword, int max, int min, String hashtag) {
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        webDriver.get("https://www.instagram.com/accounts/login/?source=auth_switcher");

        this.pause(1000);
        WebElement webElement = webDriver.findElement(By.name("username"));
        webElement.sendKeys(username);
        this.pause(1000);
        WebElement password = webDriver.findElement(By.name("password"));
        password.sendKeys(userPassword);
        this.pause(0);
        WebElement button1 = webDriver.findElement(By.xpath(LOGINBUTTON));
        button1.click();
        this.pause(3000);
        try {
            WebElement button3 = webDriver.findElement(By.xpath(NIE_TERAZ_BUTTON));
            button3.click();
        } catch (NullPointerException e){
            System.out.println(" element NIE_TERAZ_BUTTON not found ");
            return;
        }
        this.pause(3000);
        try {
            WebElement button2 = webDriver.findElement(By.xpath(ALLOWBUTTON));
            button2.click();
        } catch (NullPointerException e){
            System.out.println(" element ALLOWBUTTON not found ");
            return;
        }
        this.pause(1000);
        webDriver.get("https://www.instagram.com/explore/tags/" + hashtag + "/");
        this.pause(2000);
        WebElement firstPicture = webDriver.findElement(By.xpath(FIRST_PICTURE));
        firstPicture.click();
        this.pause(1000);

        int howManyLiked = 0;
        int howManyForward = 0;
        while (howManyLiked != howMany && isCancelled) {
            try {
                sleep(getRandom(max, min));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean isExists = true;
            try {
                webDriver.findElement(By.xpath(HEARTBUTTON));
            } catch (NoSuchElementException e) {
                isExists = false;
            }

            WebElement element;
            if (isExists) {
                element = webDriver.findElement(By.xpath(HEARTBUTTON));
                element.click();
                howManyLiked++;
                System.out.println("Post Liked, sum = " + howManyLiked);
            } else {
                System.out.println("Post already Liked, scrolling down ");
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
        System.out.println(random);
        return random * 1000;
    }
}
