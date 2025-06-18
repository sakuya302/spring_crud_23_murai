package jp.co.sss.crud.test04;

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
@DisplayName("04_社員名検索機能")
public class SearchEmpNameTest {

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
		loginIdElement.sendKeys("2");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		webDriver.findElement(By.className("input")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

	}

	@Test
	@Order(1)
	public void 正常系_社員名検索操作_入力_郎() {

		doLogin();

		WebElement searchEmpNameElement = webDriver.findElement(By.name("empName"));
		searchEmpNameElement.clear();
		searchEmpNameElement.sendKeys("郎");

		searchEmpNameElement.submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empNameOfSuzuki = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(2)"));
		WebElement empNameOfTanaka = webDriver.findElement(By.cssSelector("table tr:nth-of-type(3) td:nth-of-type(2)"));

		// 検証
		assertEquals("鈴木太郎", empNameOfSuzuki.getText());
		assertEquals("田中二郎", empNameOfTanaka.getText());

	}

	@Test
	@Order(2)
	public void 正常系_社員名検索操作_入力_空文字() {

		doLogin();

		WebElement searchEmpNameElement = webDriver.findElement(By.name("empName"));
		searchEmpNameElement.clear();

		searchEmpNameElement.submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empNameOfSuzuki = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(2)"));
		WebElement empNameOfTanaka = webDriver.findElement(By.cssSelector("table tr:nth-of-type(3) td:nth-of-type(2)"));
		WebElement empNameOfWatanabe = webDriver
				.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(2)"));

		// 検証
		assertEquals("鈴木太郎", empNameOfSuzuki.getText());
		assertEquals("田中二郎", empNameOfTanaka.getText());
		assertEquals("渡辺花子", empNameOfWatanabe.getText());

	}

	@Test
	@Order(3)
	public void 正常系_社員名検索操作_入力_上() {

		doLogin();

		WebElement searchEmpNameElement = webDriver.findElement(By.name("empName"));
		searchEmpNameElement.clear();
		searchEmpNameElement.sendKeys("上");

		searchEmpNameElement.submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement msgOfNoneEmployee = webDriver.findElement(By.className("back_to_top_message"));

		// 検証
		assertEquals("該当する社員は存在しません。", msgOfNoneEmployee.getText());

	}

}
