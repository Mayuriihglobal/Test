package strongroom;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import objects.DestroyPatientPagetest;
import objects.LoginPage;
import objects.NotificationPage;
import objects.SecondPage;
import objects.SignPage;


public class Destroypatient {
	
	private DestroyPatientPagetest destroyPatientPagetest;
	private SignPage signPage;
	private LoginPage loginPage;
	private SecondPage secondPage;
	private NotificationPage notificationPage;
	private WebDriverWait wait;
	private WebDriver driver;
	
	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		loginPage = new LoginPage(driver, wait);
		secondPage = new SecondPage(driver, wait);
		notificationPage = new NotificationPage(driver, wait);
		signPage = new SignPage(driver, wait);
		destroyPatientPagetest = new DestroyPatientPagetest(driver, wait);
		
		loginPage.openLoginPage("https://staging.strongroom.ai/login");
		LoginPage.login();
		loginPage.clickLoginButton();

		secondPage.selectLocationFromDropdown();
		secondPage.clickSecondPageButton();
		notificationPage.clickNotificationIcon();

	}
	
	@Test
	public void DestroyPatient() throws InterruptedException {

		destroyPatientPagetest.Destroy();
		destroyPatientPagetest.writenote();
		destroyPatientPagetest.MethodOFDestruction();
		destroyPatientPagetest.CourierNameandNotes();
		DestroyPatientPagetest.Resident(null);
		Thread.sleep(3000);
		signPage.performSignature("valeshan.naidoo@strongroom.ai", "1111");
		Thread.sleep(6000);
	}
}
