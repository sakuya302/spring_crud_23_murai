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
import org.openqa.selenium.support.ui.Select;

import com.google.common.io.Files;

@TestMethodOrder(OrderAnnotation.class)
@DisplayName("09_入力チェック機能_社員登録")
public class ValidRegisterEmployeeTest {

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
	public void 異常系_社員登録操作_パスワード_空文字入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.sendKeys("太田五郎");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.sendKeys("東京都");

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgNullOfEmpPass = "パスワードを入力してください。";
		String errMsgDigitsOverOfEmpPass = "パスワードは16文字以内で入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgNullOfEmpPass));
		assertTrue(errMsgs.contains(errMsgDigitsOverOfEmpPass));

	}

	@Test
	@Order(2)
	public void 異常系_社員登録操作_パスワード_桁数オーバー入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("12345678901234567");

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.sendKeys("太田五郎");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.sendKeys("東京都");

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgDigitsOverOfEmpPass = "パスワードは16文字以内で入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgDigitsOverOfEmpPass));

	}

	@Test
	@Order(3)
	public void 異常系_社員登録操作_社員名_空文字入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.sendKeys("東京都");

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgNullOfEmpName = "社員名を入力してください。";
		String errMsgDigitsOverOfEmpName = "社員名は30文字以内で入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgNullOfEmpName));
		assertTrue(errMsgs.contains(errMsgDigitsOverOfEmpName));

	}

	@Test
	@Order(4)
	public void 異常系_社員登録操作_社員名_桁数オーバー入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほま");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.sendKeys("東京都");

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgDigitsOverOfEmpName = "社員名は30文字以内で入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgDigitsOverOfEmpName));

	}

	@Test
	@Order(5)
	public void 異常系_社員登録操作_住所_空文字入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgNullOfAddress = "住所を入力してください。";
		String errMsgDigitsOverOfAddress = "住所は60文字以内で入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgNullOfAddress));
		assertTrue(errMsgs.contains(errMsgDigitsOverOfAddress));

	}

	@Test
	@Order(6)
	public void 異常系_社員登録操作_住所_桁数オーバー入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("あいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほあいうえおかきくけこさしすせそたちつてとなにぬねのはひふへほま");

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.sendKeys("1986/10/12");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgDigitsOverOfAddress = "住所は60文字以内で入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgDigitsOverOfAddress));

	}

	@Test
	@Order(7)
	public void 異常系_社員登録操作_生年月日_空文字入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgNullOfBirthday = "生年月日を入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgNullOfBirthday));

	}

	@Test
	@Order(8)
	public void 異常系_社員登録操作_生年月日_非存在日付入力メッセージ出力() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement registerLink = webDriver.findElement(By.linkText("新規社員登録"));

		registerLink.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.clear();
		empPassElement.sendKeys("1111");

		WebElement empNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		empNameElement.clear();
		empNameElement.sendKeys("太田五郎");

		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='gender'][value='1']")).click();

		WebElement addressElement = webDriver.findElement(By.name("address"));
		addressElement.clear();
		addressElement.sendKeys("東京都");

		WebElement birthdayElement = webDriver.findElement(By.name("birthday"));
		birthdayElement.clear();
		birthdayElement.sendKeys("1986/10/32");
		//radioボタン
		webDriver.findElement(By.cssSelector("input[name='authority'][value='1']")).click();

		//プルダウン
		Select select = new Select(webDriver.findElement(By.cssSelector(".update select[name='deptId']")));
		select.selectByValue("2");

		webDriver.findElement(By.cssSelector("input[value='登録']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInputError = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInputError, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員登録入力画面（エラーメッセージ）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String errMsgNotExistOfBirthday = "正しい日付を入力してください。";

		List<WebElement> errElements = webDriver.findElements(By.cssSelector("p span span"));

		List<String> errMsgs = new ArrayList<String>();

		for (WebElement errElement : errElements) {
			errMsgs.add(errElement.getText());
		}

		assertTrue(errMsgs.contains(errMsgNotExistOfBirthday));

	}

}
