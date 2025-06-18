package jp.co.sss.crud.test01;

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
@DisplayName("01_ログイン機能")
public class LoginTest {

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
	public void 正常系_ログイン操作_画面遷移_一般権限() {
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

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement headerMsg = webDriver.findElement(By.cssSelector("header .user_info"));

		// 検証
		assertEquals("社員管理システム", webDriver.getTitle());
		assertTrue(headerMsg.getText().contains("ようこそ"));

	}

	@Test
	@Order(2)
	public void 正常系_ログイン操作_画面遷移_管理者権限() {
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

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement headerMsg = webDriver.findElement(By.cssSelector("header .user_info"));

		// 検証
		assertEquals("社員管理システム", webDriver.getTitle());
		assertTrue(headerMsg.getText().contains("ようこそ"));

	}

	//入力チェックと重複するため削除（テストNo3）
	//	@Test
	//	@Order(3)
	//	public void 異常系_ログイン操作_ログインエラーメッセージ出力() {
	//		// 指定したURLに遷移する
	//		webDriver.get("http://localhost:7779/spring_crud/");
	//
	//		// 最大5秒間、ページが完全に読み込まれるまで待つ
	//		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
	//
	//		WebElement empIdElement = webDriver.findElement(By.name("empId"));
	//		empIdElement.clear();
	//		empIdElement.sendKeys("100");
	//
	//		WebElement password = webDriver.findElement(By.name("empPass"));
	//		password.clear();
	//		password.sendKeys("1111");
	//
	//		webDriver.findElement(By.className("input")).submit();
	//
	//		// 最大5秒間、ページが完全に読み込まれるまで待つ
	//		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));
	//
	//		// MOC要確認
	//		WebElement errElement = webDriver.findElement(By.cssSelector("p"));
	//
	//		// 検証
	//		assertEquals("社員ID、またはパスワードが間違っています。", errElement.getText());
	//
	//	}

}
