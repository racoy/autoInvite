import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Inviter {
    WebDriver driver;
    WebDriverWait wait;
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    final int usersPerAccount;
    final int MAX_THREADS_NUM;
    final int MIN_THREADS_NUM;
    final int ROWS_CHECKED;
    final int MIN_ACCS;

    private boolean clearInvList(StringBuilder inviteList) {
        ArrayList<String> accsOld = new ArrayList<String>(Arrays.asList(inviteList.toString().split("\n")));
        String lastAccs = driver.findElement(By.id("receivers")).getAttribute("value");
        ArrayList<String> accsNew = new ArrayList<String>(Arrays.asList(lastAccs.split("\n")));
        inviteList = AccFilter.clearList(accsOld, accsNew, ROWS_CHECKED);
        setInviteList(inviteList);
        return accsNew.size() > MIN_ACCS;
    }

    private void setInviteList(StringBuilder inviteList) {
        StringSelection stringSelection = new StringSelection(inviteList.toString());
        Transferable oCash = clipboard.getContents(null);
        clipboard.setContents(stringSelection, null);
        driver.findElement(By.id("receivers")).clear();
        driver.findElement(By.id("receivers")).sendKeys(Keys.CONTROL + "v");
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {}
        clipboard.setContents(oCash, null);
    }

    public void changeIp(String addres) {
        driver.get(addres);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".label:nth-child(8)")));
        driver.findElement(By.id("change_ip")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading_overlay")));
    }

    public Inviter() {
        System.setProperty("webdriver.chrome.driver", "D:\\rep\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 900);
        usersPerAccount = 4;
        MAX_THREADS_NUM = 10;
        MIN_THREADS_NUM = 2;
        ROWS_CHECKED = 10;
        MIN_ACCS = 50;
    }

    public int invite(int firstAccPosition, int addedPlan, String baseAddres, String destGroup,
                      StringBuilder inviteList) {

        driver.get(baseAddres);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("invite_num_per_iter")));


        driver.findElement(By.id("invite_num_per_iter")).clear();
        driver.findElement(By.id("invite_num_per_iter")).sendKeys("1");
        driver.findElement(By.id("max_inv")).clear();
        driver.findElement(By.id("max_inv")).sendKeys(Integer.toString(usersPerAccount));
        driver.findElement(By.cssSelector(".inline_list:nth-child(2) .use_blacklist")).click();

        driver.findElement(By.id("target_group")).sendKeys(destGroup);

        setInviteList(inviteList);

        int i = 1;
        int addedAll = 0;
        int accNeeded = Integer.min(MAX_THREADS_NUM, (addedPlan - addedAll) / usersPerAccount);
        while (accNeeded > 0) {
            driver.findElement(By.cssSelector(".select_users_i")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr:nth-child(1) > .name")));
            for (int j = firstAccPosition; j < firstAccPosition + accNeeded; j++) {
                driver.findElement(By.cssSelector("tr:nth-child(" + j + ") > .name")).click();
            }
            driver.findElement(By.cssSelector(".lnp")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("start_inviter")));
            driver.findElement(By.id("start_inviter")).click();

            while (i < Integer.MAX_VALUE) {
                try {wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"log\"]/li[" + i + "]"))); }
                catch (Exception e) {
                    String lastAccs = driver.findElement(By.id("receivers")).getAttribute("value");
                    inviteList.delete(0, inviteList.length());
                    inviteList.append(lastAccs);
                    return (firstAccPosition + accNeeded); }
                try {
                    String s = "class";
                    String className;
                    className = driver.findElement(By.xpath("//*[@id=\"log\"]/li[" + i + "]/span[2]")).getAttribute(s);
                    if (className.equals("log_success")) {
                        int added;
                        String addedText;
                        addedText = driver.findElement(By.xpath("//*[@id=\"log\"]/li[" + (i - 2) + "]/span[2]")).getText();
                        added = Integer.parseInt(addedText);
                        addedAll += added;
                        i++;
                        break;
                    }
                } catch (NoSuchElementException ignored) {}
                i++;
            }
            firstAccPosition += accNeeded;
            accNeeded = Integer.min(MAX_THREADS_NUM, (addedPlan - addedAll) / usersPerAccount);
            if ((accNeeded > 0) && (accNeeded > MIN_THREADS_NUM)) accNeeded = MIN_THREADS_NUM;
            if (!clearInvList(inviteList)) break;
        }
        return  firstAccPosition;
    }
}
