package jp.co.sss.crud.test10;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.google.common.io.Files;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("10_ログイン判定")
public class JudgeIsLoginedTest {

	private WebDriver webDriver;

	/**
	 * テストメソッドを実行する前に実行されるメソッド
	 */
	@BeforeEach
	public void createDriver() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--remote-allow-origins=*");
		webDriver = new ChromeDriver(ops);
	}

	@AfterEach
	public void quitDriver() {
		webDriver.close();
	}

	private void doLogin() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement loginIdElement = webDriver.findElement(By.name("empId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("1");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("1111");

		webDriver.findElement(By.className("input")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

	}

	@Test
	@Order(1)
	public void 正常系_ログイン判定操作_ログアウト後の登録画面アクセス() {

		doLogin();

		/*****社員一覧から入力画面へ*****/

		WebElement logoutWebElement = webDriver.findElement(By.linkText("ログアウト"));

		logoutWebElement.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		String path = "http://localhost:7779/spring_crud/regist/input";//URLが違う場合、書き換えてください

		webDriver.get(path);

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("http://localhost:7779/spring_crud/", webDriver.getCurrentUrl());

	}

	@Test
	@Order(2)
	public void 正常系_ログイン判定操作_ブラウザ終了後の登録画面アクセス() {

		doLogin();

		/*****社員一覧から入力画面へ*****/

		// ブラウザを閉じる代わりにページをクリアする
		webDriver.manage().deleteAllCookies();
		webDriver.navigate().refresh();
	
		//社員登録入力のURLが違う場合、書き換えてください
		String path = "http://localhost:7779/spring_crud/regist/input";

		webDriver.get(path);

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertEquals("http://localhost:7779/spring_crud/", webDriver.getCurrentUrl());

	}

}
