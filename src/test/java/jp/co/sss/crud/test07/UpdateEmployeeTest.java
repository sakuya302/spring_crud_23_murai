package jp.co.sss.crud.test07;

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
@DisplayName("07_社員変更機能")
public class UpdateEmployeeTest {

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

		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

	}

	@Test
	@Order(1)
	public void 正常系_社員変更操作_変更完了_管理者権限() {

		doLogin();

		/*****社員一覧から入力画面へ*****/
		WebElement buttonUpdate = webDriver
				.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']"));
		buttonUpdate.submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement articleInputTitle = webDriver.findElement(By.cssSelector("article h3"));

		WebElement inputedEmpNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		WebElement inputedGenderElement = webDriver
				.findElement(By.cssSelector(".update input[name='gender'][value='1']"));
		WebElement inputedAddressElement = webDriver.findElement(By.cssSelector(".update input[name='address']"));
		WebElement inputedBirthdayElement = webDriver.findElement(By.cssSelector(".update input[name='birthday']"));
		WebElement inputedAuthorityElement = webDriver
				.findElement(By.cssSelector(".update input[name='authority'][value='1']"));
		WebElement inputedDeptIdElement = webDriver
				.findElement(By.cssSelector(".update select[name='deptId'] option[value='1']"));

		//検証
		assertEquals("社員変更入力画面", articleInputTitle.getText());
		assertEquals("鈴木太郎", inputedEmpNameElement.getAttribute("value"));
		assertTrue(inputedGenderElement.isSelected());
		assertEquals("東京都", inputedAddressElement.getAttribute("value"));
		assertEquals("1986/10/12", inputedBirthdayElement.getAttribute("value"));
		assertTrue(inputedAuthorityElement.isSelected());
		assertTrue(inputedDeptIdElement.isSelected());

		/*****社員入力から確認画面へ*****/
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.sendKeys("1111");

		inputedEmpNameElement.clear();
		inputedEmpNameElement.sendKeys("山田太郎");

		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileCheck = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileCheck, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更確認画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement articleCheckTitle = webDriver.findElement(By.cssSelector("article h3"));
		WebElement checkEmpNameElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(2) .input"));

		// 検証
		assertEquals("社員変更確認画面", articleCheckTitle.getText());
		assertEquals("山田太郎", checkEmpNameElement.getText());

		/*****社員確認から完了画面へ*****/
		webDriver.findElement(By.cssSelector(".update .input input[value='変更']")).submit();

		// スクリーンショット
		File tempFileComplete = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileComplete, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更完了.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement articleCompleteTitle = webDriver.findElement(By.cssSelector("article h3"));
		//検証
		assertEquals("社員変更完了画面", articleCompleteTitle.getText());

		/*****社員完了から一覧画面へ*****/

		WebElement backToEmpListElement = webDriver.findElement(By.linkText("一覧表示に戻る"));

		backToEmpListElement.click();

		WebElement empId = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(1)"));
		WebElement empName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(2)"));

		// 検証
		assertEquals("1", empId.getText());
		assertEquals("山田太郎", empName.getText());

	}

	@Test
	@Order(2)
	public void 正常系_社員変更操作_入力画面_戻るボタンを押下する() {

		doLogin();

		/*****社員一覧から入力画面へ*****/
		WebElement buttonUpdate = webDriver
				.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']"));
		buttonUpdate.submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFile, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		webDriver.findElement(By.cssSelector(".update .input input[value='戻る']")).submit();

		WebElement articleCompleteTitle = webDriver.findElement(By.cssSelector("article h3"));
		//検証
		assertEquals("社員一覧画面", articleCompleteTitle.getText());

	}

	@Test
	@Order(3)
	public void 正常系_社員変更操作_確認画面_戻るボタンを押下する() {

		doLogin();
		/*****社員一覧から入力画面へ*****/
		WebElement buttonUpdate = webDriver
				.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(8) input[type='submit']"));
		buttonUpdate.submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput1 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput1, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		WebElement inputedEmpNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));

		empPassElement.sendKeys("1111");

		inputedEmpNameElement.clear();
		inputedEmpNameElement.sendKeys("山田太郎");

		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileCheck = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileCheck, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*****社員確認から入力画面へ*****/
		webDriver.findElement(By.cssSelector(".update .input input[value='戻る']")).submit();

		// スクリーンショット
		File tempFileInput2 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput2, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement articleInputTitle = webDriver.findElement(By.cssSelector("article h3"));
		//検証
		assertEquals("社員変更入力画面", articleInputTitle.getText());

	}

	@Test
	@Order(4)
	public void 正常系_社員変更操作_変更完了_一般権限() {

		/*****一般権限ログイン******/
		// 指定したURLに遷移する
		webDriver.get("http://localhost:7779/spring_crud/");

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		WebElement loginIdElement = webDriver.findElement(By.name("empId"));
		loginIdElement.clear();
		loginIdElement.sendKeys("1");

		WebElement password = webDriver.findElement(By.name("empPass"));
		password.clear();
		password.sendKeys("1111");

		webDriver.findElement(By.cssSelector("input[type='submit']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileList1 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileList1, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員一覧画面（ログイン後）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		/*****社員一覧から入力画面へ*****/

		WebElement updateEmpNameElement = webDriver.findElement(By.linkText("山田太郎"));

		updateEmpNameElement.click();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileInput = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileInput, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更入力画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement articleInputTitle = webDriver.findElement(By.cssSelector("article h3"));

		WebElement inputedEmpNameElement = webDriver.findElement(By.cssSelector(".update input[name='empName']"));
		WebElement inputedGenderElement = webDriver
				.findElement(By.cssSelector(".update input[name='gender'][value='1']"));
		WebElement inputedAddressElement = webDriver.findElement(By.cssSelector(".update input[name='address']"));
		WebElement inputedBirthdayElement = webDriver.findElement(By.cssSelector(".update input[name='birthday']"));
		WebElement inputedDeptIdElement = webDriver
				.findElement(By.cssSelector(".update select[name='deptId'] option[value='1']"));

		//検証
		assertEquals("社員変更入力画面", articleInputTitle.getText());
		assertEquals("山田太郎", inputedEmpNameElement.getAttribute("value"));
		assertTrue(inputedGenderElement.isSelected());
		assertEquals("東京都", inputedAddressElement.getAttribute("value"));
		assertEquals("1986/10/12", inputedBirthdayElement.getAttribute("value"));
		assertTrue(inputedDeptIdElement.isSelected());

		/*****社員入力から確認画面へ*****/
		WebElement empPassElement = webDriver.findElement(By.name("empPass"));
		empPassElement.sendKeys("1111");

		inputedEmpNameElement.clear();
		inputedEmpNameElement.sendKeys("佐藤太郎");

		webDriver.findElement(By.cssSelector("input[value='変更']")).submit();

		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(5));

		// スクリーンショット
		File tempFileChange = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileChange, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更確認画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement articleCheckTitle = webDriver.findElement(By.cssSelector("article h3"));
		WebElement checkEmpNameElement = webDriver.findElement(By.cssSelector(".update .form:nth-of-type(2) .input"));

		// 検証
		assertEquals("社員変更確認画面", articleCheckTitle.getText());
		assertEquals("佐藤太郎", checkEmpNameElement.getText());

		/*****社員確認から完了画面へ*****/
		webDriver.findElement(By.cssSelector(".update .input input[value='変更']")).submit();

		// スクリーンショット
		File tempFileComplete = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileComplete, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員変更完了画面.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement articleCompleteTitle = webDriver.findElement(By.cssSelector("article h3"));
		//検証
		assertEquals("社員変更完了画面", articleCompleteTitle.getText());

		/*****社員完了から一覧画面へ*****/

		WebElement backToEmpListElement = webDriver.findElement(By.linkText("一覧表示に戻る"));

		backToEmpListElement.click();

		// スクリーンショット
		File tempFileList2 = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			Files.move(tempFileList2, new File("screenshots\\" + new Object() {
			}.getClass().getEnclosingMethod().getName() + "社員一覧画面（変更完了）.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		WebElement empId = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(1)"));
		WebElement empName = webDriver.findElement(By.cssSelector("table tr:nth-of-type(2) td:nth-of-type(2)"));
		WebElement headerEmpName = webDriver.findElement(By.linkText("佐藤太郎"));

		// 検証
		assertEquals("1", empId.getText());
		assertEquals("佐藤太郎", empName.getText());
		assertEquals("佐藤太郎", headerEmpName.getText());

	}

}
