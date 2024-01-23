package objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DestroyPatientPage extends ExcelUtils {

	private static final By DESTROY_BUTTON_LOCATOR = By.xpath("//button[normalize-space()='Destroy']");
	private static final By NOTE_LOCATOR = By.xpath("//textarea[@name='notes' and @id='note-modal']");
	private static final By METHOD_LOCATOR = By.xpath("//input[@placeholder='Method of Destruction']");
	private static final By COURIER_NAME_LOCATOR = By.xpath("//input[@placeholder='Courier Name']");
	private static final By COURIER_NOTES_LOCATOR = By.xpath("//input[@placeholder='Courier Notes']");
	private static final By RESIDENT_LOCATOR = By.xpath("//p[normalize-space()='Resident Medication']");
	private static final By RESIDENT_INPUT_LOCATOR = By
			.xpath("//input[@placeholder='Enter Resident name or Medicare Number']");
	private static final By RESIDENT_SEARCH_LOCATOR = By.xpath("//p[@class='submit-button blue-button']");
	private static final By RESIDENT_DROPDOWN_LOCATOR = By.xpath("//p[starts-with(b, 'Name:')]");
	private static final By MEDICATION_LOCATOR = By.xpath("//select[@id='pom-select']");
//	private static final By MEDICATION_DROPDOWN_LOCATOR = By.xpath("//select[@id='pom-select']/option");
	private static final By QTY_LOCATOR = By.xpath("//input[@placeholder='Quantity']");
	private static final By ADD_LOCATOR = By.xpath("//p[@class='submit-button blue-button']");
	private static final By DESTROY_COMPLATE_LOCATOR = By.xpath("//p[@class='regular-button complete-button']");

	private WebDriverWait wait;
	private WebDriver driver;

	List<String> drugNames = readDrugNamesFromExcel("Agedcare.xlsx");
	List<String> innumbers = readInnumbersFromExcel("Agedcare.xlsx");
	List<String> resident = readResidentFromExcel("Agedcare.xlsx");

	private static int searchCount = -1; // drug
	private static int searchCount1 = -1; // quantity
	private static int searchCoun3 = 0; // resident
	private String QtyFromExcel;
	public Stocktakepage stocktakepage;

	public DestroyPatientPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.stocktakepage = new Stocktakepage(driver, wait); // Initialize the instance

	}

	public void Destroy() {

		WebElement Destroy = wait.until(ExpectedConditions.elementToBeClickable(DESTROY_BUTTON_LOCATOR));
		Destroy.click();

		// Wait for the modal to be invisible before proceeding
	}

	public void writenote() {

		WebElement writenote = wait.until(ExpectedConditions.elementToBeClickable(NOTE_LOCATOR));
		writenote.click();
		writenote.sendKeys("Destroy Resident");
	}

	public void MethodOFDestruction() {

		WebElement MethodOFDestruction = wait.until(ExpectedConditions.elementToBeClickable(METHOD_LOCATOR));
		MethodOFDestruction.click();
		MethodOFDestruction.sendKeys("MethodOFDestruction");
	}

	public void CourierNameandNotes() {

		WebElement CourierName = wait.until(ExpectedConditions.elementToBeClickable(COURIER_NAME_LOCATOR));
		CourierName.click();
		CourierName.sendKeys("CourierName");

		WebElement CourierNotes = wait.until(ExpectedConditions.elementToBeClickable(COURIER_NOTES_LOCATOR));
		CourierNotes.click();
		CourierNotes.sendKeys("CourierNotes");

	}

	public void Resident() throws InterruptedException {

		WebElement Resident = wait.until(ExpectedConditions.elementToBeClickable(RESIDENT_LOCATOR));
		Resident.click();

		WebElement Residentinput = wait.until(ExpectedConditions.presenceOfElementLocated(RESIDENT_INPUT_LOCATOR));
		Residentinput.click();

		Thread.sleep(1000);
		searchCoun3++;
		int residentIndex = searchCoun3 % resident.size();

		Residentinput.sendKeys(resident.get(residentIndex));

		// Click on the search button
		WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(RESIDENT_SEARCH_LOCATOR));
		searchButton.click();

		Thread.sleep(2000);

		List<WebElement> residentdropdown = driver.findElements(RESIDENT_DROPDOWN_LOCATOR);

		String Res = resident.get(searchCoun3 % resident.size());
		System.out.println("Clicked on the option: " + Res);

		// Iterate through the options to find a match with the entered drug name
		for (WebElement option : residentdropdown) {

			// option.getText();
			System.out.println("Residentdropdown: " + option.getText());

			if (option.getText().trim().equals("Name: " + Res) || option.getText().trim().equals(Res)) {
				Thread.sleep(1000);
				option.click();
				break;
			}

		}

		// ...

		WebElement medicationbox = wait.until(ExpectedConditions.elementToBeClickable(MEDICATION_LOCATOR));
		medicationbox.click();

		// Define the timeout (in milliseconds) for which you want to keep the dropdown
		// open
		long timeout = System.currentTimeMillis() + 1000; // 1 seconds

		while (System.currentTimeMillis() < timeout) {
			// Click on the dropdown repeatedly to keep it open
			medicationbox.click();

			// Add a small delay to control the loop speed
			Thread.sleep(1000);

			// Check if the dropdown is still open
			if (!isDropdownOpen()) {
				// Break the loop if the dropdown is closed
				break;
			}
		}

		List<WebElement> dropdownOptions = driver.findElements(By.xpath("//select[@id='pom-select']/option"));

		searchCount++;
		String currentDrugName = drugNames.get(searchCount % drugNames.size());

		// Extract only the drug name without additional text
		String trimmedDrugName = getTrimmedDrugName(currentDrugName);

		System.out.println(trimmedDrugName);

		// Check if the drugname is present in the dropdown options
		boolean drugFound = false;
		for (WebElement option : dropdownOptions) {
			System.out.println("Dropdown Option: " + option.getText());
			if (option.getText().contains(trimmedDrugName)) {
				drugFound = true;
				option.click();
				break;
			}
		}

		Thread.sleep(1000);

		if (!drugFound) {
			// If an exact match is not found, try to find an option that contains the
			// partial drug name
			for (WebElement option : dropdownOptions) {
				if (option.getText().contains(currentDrugName.split(" ")[1])) {
					drugFound = true;
					option.click();
					break;
				}
			}
		}

		Thread.sleep(1000);

		if (!drugFound) {
			System.out.println("Drug name '" + trimmedDrugName + "' not found in the dropdown options.");
			System.out.println("Available Options in Dropdown:");
			for (WebElement option : dropdownOptions) {
				System.out.println("- " + option.getText());
			}
		}

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(QTY_LOCATOR));
		quantityInput.clear();

		searchCount1++;

		// String drugqty = innumbers.get(searchCount1);
		String drugqty = innumbers.get(searchCount1 % innumbers.size());

		quantityInput.sendKeys(drugqty);
		QtyFromExcel = drugqty;
		
		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_LOCATOR));
		addButton.click();

		Thread.sleep(3000);

		WebElement DestroycompleteButton = wait
				.until(ExpectedConditions.elementToBeClickable(DESTROY_COMPLATE_LOCATOR));
		DestroycompleteButton.click();

	}

	private boolean isDropdownOpen() {
		// You can modify this method based on how you determine if the dropdown is open
		try {
			WebElement dropdown = driver.findElement(By.xpath("//select[@id='pom-select']"));
			return dropdown.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	private String getTrimmedDrugName(String drugName) {
		// Find the last set of parentheses and extract the content between them
		int lastOpenParenthesisIndex = drugName.lastIndexOf("(");
		int lastCloseParenthesisIndex = drugName.lastIndexOf(")");

		if (lastOpenParenthesisIndex != -1 && lastCloseParenthesisIndex != -1
				&& lastOpenParenthesisIndex < lastCloseParenthesisIndex) {
			return drugName.substring(lastOpenParenthesisIndex + 1, lastCloseParenthesisIndex).trim();
		} else {
			// If no parentheses found or the format is not as expected, return the original
			// drug name
			return drugName.trim();
		}
	}

	public String Getselectdestroyqty() {
		return QtyFromExcel;
	}

	public String GetselectMedication() {
		String selectedDrug = driver.findElement(By.xpath("//td[1]/p[1]")).getText();
		return selectedDrug;
	}

	public int GetselectQty() throws InterruptedException {
		String selectedQty = driver.findElement(By.xpath("//td[2]/p[1]")).getText().trim();
		String add1 =  selectedQty.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + add1);
		Thread.sleep(1000);
		String numericAdd = add1.replaceAll("[^0-9]", "");
		int abc = Integer.parseInt(numericAdd);
		return abc;
	}

}
