package com.chone.fightpet.selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Create 2021/1/8 13:38
 *
 * @author chone
 */
public class LoginChrome {
    /**
     * 登录地址
     */
//    private final static String LOGIN_URL = "https://xui.ptlogin2.qq.com/cgi-bin/xlogin?appid=1006102&s_url=https://id.qq.com/index.html%23info";
    private final static String LOGIN_URL = "https://ui.ptlogin2.qq.com/cgi-bin/login?appid=614038002&&s_url=https://dld.qzapp.z.qq.com%2Fqpet%2Fcgi-bin%2Fphonepk%3Fcmd%3Dindex";

    /**
     * 成功跳转地址
     */
//    private final static String SUCCESS_URL = "https://id.qq.com";
    private final static String SUCCESS_URL = "https://dld.qzapp.z.qq.com";

    public static String loginQQ() {
        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
//        System.setProperty("webdriver.chrome.driver", "D:\\ProgramData\\Java\\jdk1.8.0_281\\bin\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get(LOGIN_URL);
        Dimension dimension = new Dimension(200, 600);
        driver.manage().window().setSize(dimension);
        // 自动点击已登录QQ
        driver.findElement(new By.ByXPath("//*[@id=\"qlogin_list\"]/a")).click();
        String currentUrl;
        do {
            currentUrl = driver.getCurrentUrl();
            //  System.out.println("currentUrl = " + currentUrl);
        } while (!currentUrl.startsWith(SUCCESS_URL));

        String cookie = (String) ((JavascriptExecutor) driver).executeScript("return document.cookie");
        System.out.println("cookie = " + cookie);
        driver.quit();
        return cookie;
    }

}
