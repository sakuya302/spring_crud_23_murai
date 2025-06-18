package jp.co.sss.crud.test09;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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
@DisplayName("09_入力チェック機能_ログイン")
public class ValidLoginTest {

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
	public void 異常系_ログイン操作_社員ID_空文字入力メッセージ出力() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		webDriver.findElement(By.className("input")).submit();

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());

		}

		String errMsgInputNUllOfEmpId = "社員IDを入力してください。";

		// 検証
		assertTrue(errMsgs.contains(errMsgInputNUllOfEmpId));

	}

	@Test
	@Order(2)
	public void 異常系_ログイン操作_社員ID_桁数オーバー入力メッセージ出力() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("111111");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		webDriver.findElement(By.className("input")).submit();

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());

		}

		String errMsgDigitsOverOfEmpId = "社員IDは99999までの整数値で入力してください。";

		// 検証
		assertTrue(errMsgs.contains(errMsgDigitsOverOfEmpId));

	}

	@Test
	@Order(3)
	public void 異常系_ログイン操作_社員ID_文字種エラー1入力メッセージ出力() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("abc");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		webDriver.findElement(By.className("input")).submit();

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());

		}

		String errMsgCharTypeOfEmpId = "社員IDは整数値で入力してください。";

		// 検証
		assertTrue(errMsgs.contains(errMsgCharTypeOfEmpId));

	}

	@Test
	@Order(4)
	public void 異常系_ログイン操作_社員ID_文字種エラー2入力メッセージ出力() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("12.3");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("2222");

		webDriver.findElement(By.className("input")).submit();

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());

		}

		String errMsgCharTypeOfEmpId = "社員IDは整数値で入力してください。";

		// 検証
		assertTrue(errMsgs.contains(errMsgCharTypeOfEmpId));

	}

	@Test
	@Order(5)
	public void 異常系_ログイン操作_パスワード_空文字入力メッセージ出力() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("1");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();

		webDriver.findElement(By.className("input")).submit();

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());

		}

		String errMsgNullOfEmpPass = "パスワードを入力してください。";

		// 検証
		assertTrue(errMsgs.contains(errMsgNullOfEmpPass));

	}

	@Test
	@Order(6)
	public void 異常系_ログイン操作_ログインエラーメッセージ出力() {
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement empIdElement = webDriver.findElement(By.name("empId"));
		empIdElement.clear();
		empIdElement.sendKeys("5");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("5");

		webDriver.findElement(By.className("input")).submit();

		// 最大5秒間、ページが完全に読み込まれるまで待つ
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// MOC要確認
		WebElement errElement = webDriver.findElement(By.cssSelector("p"));

		// 検証
		assertEquals("社員ID、またはパスワードが間違っています。", errElement.getText());

	}

}
