package jp.co.sss.crud.test03;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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
@DisplayName("03_社員一覧表示機能")
public class ShowEmployeeListTest {

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

	@Test
	@Order(1)
	public void 正常系_社員一覧表示_一般権限() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		WebElement loginIdElement = webDriver.findElement(By.name("empId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("1");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("1111");

		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empId = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(1)"));
		WebElement empName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(2)"));
		WebElement departmentName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(3)"));

		// 検証
		assertEquals("1", empId.getText());
		assertEquals("鈴木太郎", empName.getText());
		assertEquals("営業部", departmentName.getText());

	}

	@Test
	@Order(2)
	public void 正常系_社員一覧表示_管理者権限() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		WebElement loginIdElement = webDriver.findElement(By.name("empId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("2");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empId = webDriver.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(1)"));
		WebElement empName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(2)"));
		WebElement gender = webDriver.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(3)"));
		WebElement address = webDriver.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(4)"));
		WebElement birthday = webDriver.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(5)"));
		WebElement auth = webDriver.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(6)"));
		WebElement departmentName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(7)"));
		WebElement buttonUpdate = webDriver
				.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(8) input[type='submit']"));
		WebElement buttonDelete = webDriver
				.findElement(By.cssSelector("table tr:nth-of-type(4) td:nth-of-type(9) input[type='submit']"));

		// 検証
		assertEquals("3", empId.getText());
		assertEquals("渡辺花子", empName.getText());
		assertEquals("女性", gender.getText());
		assertEquals("大阪府", address.getText());
		assertEquals("1988/04/23", birthday.getText());
		assertEquals("管理者", auth.getText());
		assertEquals("経理部", departmentName.getText());
		assertEquals("変更", buttonUpdate.getAttribute("value"));
		assertEquals("削除", buttonDelete.getAttribute("value"));

	}

}
