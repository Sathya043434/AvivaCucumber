package StepDefinitions;

import PageObjects.AddnewLead;
import PageObjects.BaseClass;
import PageObjects.LoginPage;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assume;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

public class Steps extends BaseClass {

    @Before
    public void setup() throws IOException {
        configprop = new Properties();
        FileInputStream configpropfile = new FileInputStream("cofig.properties");
        configprop.load(configpropfile);

        String br = configprop.getProperty("browser");

        if (br.equals("CHROME")) {
            System.setProperty("webdriver.chrome.driver", configprop.getProperty("chromepath"));
            driver = new ChromeDriver();
        } else if (br.equals("FIREFOX")) {
            System.setProperty("webdriver.gecko.driver", configprop.getProperty("Firefoxpath"));
            FirefoxOptions options = new FirefoxOptions();
            options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
            driver = new FirefoxDriver(options);
        } else if (br.equals("IE")) {
            System.setProperty("webdriver.ie.driver", configprop.getProperty("IEpath"));
            driver = new InternetExplorerDriver();
        }

    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {

            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "FailedScenarioScreenshot");

//            String scenarioName = scenario.getName().replaceAll(" ", "_");
//            ScreenshotUtils.captureScreenshot(driver, scenarioName);
        }
    }

    @Given("Open Chrome browser")
    public void im_on_aviva_login_page() throws InterruptedException {
        lp = new LoginPage(driver);
        AN = new AddnewLead(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(35));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(35));
    }

    @When("Entered Aviva url")
    public void Open_Chrome_browser() throws IOException {
        configprop = new Properties();
        FileInputStream configpropfile = new FileInputStream("cofig.properties");
        configprop.load(configpropfile);

        String urL = configprop.getProperty("URL");

        if (urL.equals("DEV")) {
            driver.get(configprop.getProperty("DevUrl"));
        } else if (urL.equals("UAT")) {
            driver.get(configprop.getProperty("UatUrl"));
        } else if (urL.equals("PREPROD")) {
            driver.get(configprop.getProperty("PREPRODURL"));
        } else if (urL.equals("PROD")) {
            driver.get(configprop.getProperty("ProdUrl"));
        }
    }

    @Then("I Entered the EmployeeID")
    public void I_Entered_the_EmployeeID() throws IOException {
        lp.enterEmpid(configprop.getProperty("EMPCODE"));
    }

    @Then("I Entered the Password")
    public void i_entered_the_password() {
        lp.enterPassword(configprop.getProperty("PASSWORD"));
    }

    @Then("I click on Login button")
    public void i_click_on_login_button() throws InterruptedException {
        lp.ClickOnLogin();
        Thread.sleep(3000);
    }

    @Then("Home Page of the Aviva application is successfully opened")
    public void home_page_of_the_aviva_application_is_successfully_opened() {
        System.out.println("Home Page of the Aviva application is successfully opened");
    }

    @Then("I clicked on the Plus Icon")
    public void i_clicked_on_the_plus_icon() throws InterruptedException {
        AN.ClickOnplus();
        AN.ClickOnAbovePlus();
        Thread.sleep(1000);
    }

    @Then("I Entered Mobile No")
    public void i_entered_mobile_no() {
        AN.EnterMobileNo(configprop.getProperty("MOBILENO"));
    }

    @Then("I Entered random Mobile No")
    public void i_entered_random_mobile_no() {
        WebElement mobileNumberInput = driver.findElement(By.xpath("//input[@id='phone']")); // Replace with your locator
        String randomMobileNumber = generateRandomMobileNumberStartingWith9();
        mobileNumberInput.sendKeys(randomMobileNumber);
        mobileNumberInput.sendKeys(Keys.ENTER);
    }

    // Add assertion to verify mobile number field is populated if needed

    private String generateRandomMobileNumberStartingWith9() {
        Random random = new Random();
        int randomNumber = random.nextInt(900000000) + 100000000; // Generates a 9-digit number
        return "9" + randomNumber;
    }


    @Then("I Entered Email ID")
    public void i_entered_email_id() {
        AN.EnterEmail(configprop.getProperty("EMAIL"));
    }
    @Then("I Selected checkbox Proceed with eKYC")
    public void I_Selected_checkbox_Proceed_with_eKYC() throws InterruptedException {
        driver.findElement(By.xpath("(//input[@class='ant-checkbox-input'])[1]")).click();
        Thread.sleep(1000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,500)", "");
        Thread.sleep(1000);
    }
    @Then("I entered Adhar No")
    public void I_entered_Adhar_No() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//label[text()='Enter the Aadhar Number']/following::input)[1]");
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element1.sendKeys("643022730486");


//        driver.findElement(By.xpath("(//span[contains(@class,'ant-input-affix-wrapper email')])[1]")).sendKeys("643022730486");

        Thread.sleep(1000);
    }
    @Then("Cliked on Verify button")
    public void Cliked_on_Verify_button() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='otpBtn'])[1]")).click();
        Thread.sleep(40000);
    }

    @Then("Proposer and Life Insured Different")
    public void Proposer_and_Life_Insured_Different() throws InterruptedException {
        driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[2]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='No'])[2]")).click();

    }

    @Then("I Selected checkbox Proceed with e-KYC in Status tab -->About the Customer")
    public void i_selected_checkbox_proceed_with_e_kyc_in_status_tab_about_the_customer() throws InterruptedException {
        driver.findElement(By.id("checkBox")).click();
        Thread.sleep(1000);
    }

    @Then("I Selected checkbox Proceed without e-KYC")
    public void i_selected_checkbox_proceed_without_e_kyc() throws InterruptedException {
        AN.SelectWOEkycCheckbox();
        Thread.sleep(1000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,300)", "");
        Thread.sleep(500);
    }

    @Then("I clicked on GetOTP")
    public void I_clicked_on_GetOTP() throws InterruptedException {
        AN.ClickOnGetOTP();
        Thread.sleep(1000);
    }

    @Then("I Entered the Adhar number")
    public void I_Entered_the_Adhar_number() throws InterruptedException {
        driver.findElement(By.id("aadharNumber")).sendKeys("643022730486");
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,500)", "");
        Thread.sleep(500);
    }

    @Then("I clicked on Verify button")
    public void I_clicked_on_Verify_button() {
        driver.findElement(By.xpath("//span[text()='VERIFY']")).click();
    }

    @Then("I wait for {int} sec to enter the otp")
    public void i_wait_for_sec_to_enter_the_otp(Integer int1) throws InterruptedException {
        Thread.sleep(30000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,300)", "");
    }

    @Then("I selected the Title")
    public void I_selected_the_Title() {
        AN.SelectDropDownTitle();
    }

    @Then("I Selected the Title as MR")
    public void I_Selectedthe_Title_as_MR() {
        AN.SelectDropDownTitleMR();
    }

    @Then("Entered First name")
    public void Entered_First_name() {
        AN.EnterFirstName(configprop.getProperty("FIRESTNAME"));
    }

    @Then("Entered Last name")
    public void Entered_Last_name() throws InterruptedException {
        AN.EnterLastName(configprop.getProperty("LASTNAME"));
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,500)", "");
        Thread.sleep(500);

    }

    @Then("Selected DOB")
    public void Selected_DOB() throws InterruptedException {
        AN.EnterDOB(configprop.getProperty("DOB"));
        Thread.sleep(1000);
        AN.ClickDOB();
        Thread.sleep(500);
//        JavascriptExecutor js2 = (JavascriptExecutor) driver;
//        js2.executeScript("window.scrollTo(0,1000)", "");
        Thread.sleep(500);
    }
    @Then("Click radio button of Details Of The Life Insured")
    public void Click_radio_button_of_Details_Of_The_Life_Insured() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("(//label[text()='City']/following::input)[2]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
//        driver.findElement(By.xpath("(//label[text()='City']/following::input)[2]")).click();
        Thread.sleep(1000);
    }
    @Then("I Selected checkbox Proceed with eKYC LI")
    public void I_Selected_checkbox_Proceed_with_eKYC_LI() throws InterruptedException {
        driver.findElement(By.xpath("(//input[@class='ant-checkbox-input'])[3]")).click();
        Thread.sleep(1000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,500)", "");
        Thread.sleep(1000);
    }
    @Then("I entered Adhar No LI")
    public void I_entered_Adhar_No_LI() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//label[text()='Enter the Aadhar Number']/following::input)[1]");
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element1.sendKeys("643022730486");

//        driver.findElement(By.xpath("(//span[contains(@class,'ant-input-affix-wrapper email')])[1]")).sendKeys("643022730486");

        Thread.sleep(1000);
    }
    @Then("Cliked on Verify button LI")
    public void Cliked_on_Verify_button_LI() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='otpBtn'])[1]")).click();
        Thread.sleep(40000);
    }
    @Then("I selected the Title LI")
    public void I_selected_the_Title_LI() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[6]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
//        driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[6]")).click();
        Thread.sleep(1000);

    }
    @Then("I Selected the Title as MR LI")
    public void I_Selected_the_Title_as_MR_LI() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("(//div[text()='Mr.'])[2]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();

//        driver.findElement(By.xpath("(//div[text()='Mr.'])[2]")).click();
        Thread.sleep(1000);
    }



    @Then("Entered First name LI")
    public void Entered_First_name_LI() throws InterruptedException {
        driver.findElement(By.xpath("(//input[contains(@class,'ant-input first-name')])[3]")).sendKeys("Anvesh");
        Thread.sleep(1000);
    }
    @Then("Entered Last name LI")
    public void Entered_Last_name_LI() throws InterruptedException {
        driver.findElement(By.xpath("((//label[text()='Last Name'])[2]/following::input)[1]")).sendKeys("S");
        Thread.sleep(1000);
    }
    @Then("Selected DOB LI")
    public void Selected_DOB_LI() throws InterruptedException {
        driver.findElement(By.xpath("(//label[text()='Date of Birth']/following::input)[10]")).sendKeys("01/10/1995");
        Thread.sleep(2000);

        WebElement element = driver.findElement(By.xpath("(//label[text()='Date of Birth']/following::input)[10]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("/html/body/div[7]/div/div/div/div/div[1]/div[2]/table/tbody/tr[1]/td[1]");
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element1.click();

    }
    @Then("Wait for Five sec")
    public void Wait_for_Five_sec() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Then("I Clicked on the Submit button Status tab --> Status")
    public void i_clicked_on_the_submit_button_status_tab_status() throws InterruptedException {
        AN.ClickOnSubmit();
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,0)", "");
        Thread.sleep(8000);
        WebElement element = driver.findElement(By.xpath("(//p[@class='lead-detail'])[1]"));
        String LeadID = element.getText();
        System.out.println("Your Lead ID is: " + LeadID);
    }

    @Then("Clicked on self or Reference item in Lead Cart - Aviva home page")
    public void clicked_on_self_reference_item_in_lead_cart_aviva_home_page() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/div[2]/div[2]/div/div[2]/div[1]/span")).click();
        Thread.sleep(1000);

    }

    @Then("Click on filter at the Top side")
    public void Click_on_fileter_at_the_Top_side() throws InterruptedException {
        driver.findElement(By.xpath("//figure[@class='round-cards43']//figcaption[1]")).click();
        Thread.sleep(1000);

    }

    @Then("Click on Leadid  tab")
    public void Click_on_Leadid_tab() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='ant-radio-button']/following-sibling::span)[3]")).click();
        Thread.sleep(1000);

    }

    @Then("Enter the lead id LiProSameW")
    public void Enter_the_lead_id_LiProSameW() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Enter the Lead’s ID']")).sendKeys(configprop.getProperty("LiProSameW"));
        Thread.sleep(1000);
    }

    @Then("Enter the lead id LiProSameWo")
    public void Enter_the_lead_id_LiProSameWo() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Enter the Lead’s ID']")).sendKeys(configprop.getProperty("LiProSameWo"));
        Thread.sleep(1000);
    }

    @Then("Enter the lead id LiProDiffW")
    public void Enter_the_lead_id_LiProDiffW() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Enter the Lead’s ID']")).sendKeys(configprop.getProperty("LiProDiffW"));
        Thread.sleep(1000);
    }

    @Then("Enter the lead id LiProDiffWo")
    public void Enter_the_lead_id_LiProDiffWo() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Enter the Lead’s ID']")).sendKeys(configprop.getProperty("LiProDiffWo"));
        Thread.sleep(1000);
    }


    @Then("Click on Apply button")
    public void Click_on_Apply_button() throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='offcanvas-body']//button[1]")).click();
        Thread.sleep(3000);
    }


    @Then("Click on Update button in Lead cart page")
    public void click_on_update_button_in_lead_cart_page() throws InterruptedException {
        driver.findElement(By.className("update-btn")).click();
        Thread.sleep(8000);
    }

    @Then("Clicked on lead details tab")
    public void clicked_on_lead_details_tab() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//div[text()='Lead Details ']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

//        driver.findElement(By.xpath("//div[text()='Lead Details ']")).click();
        Thread.sleep(3000);
    }


    @Then("Selected the title from the dropdown- Lead details tab- Personal details section")
    public void selected_the_title_from_the_dropdown_lead_details_tab_personal_details_section() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-select-selector'])[1]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();


//        driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[1]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[text()='Mr.']")).click();
        Thread.sleep(500);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,500)", "");
        Thread.sleep(1000);
    }

    @Then("Entered the Father name")
    public void Entered_the_Father_name() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Enter Father Name']")).sendKeys("Pullaiah J");
        Thread.sleep(1000);
    }

    @Then("Selected the Gender")
    public void Selected_the_Gender() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-select-selector'])[2]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        //driver.findElement(By.xpath("(//label[text()='Gender']/following::input)[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Male']")).click();
    }

    @Then("Selected the Marital Status- Lead details tab- Personal details section")
    public void selected_the_marital_status_lead_details_tab_personal_details_section() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-select-selector'])[3]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

        //driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[3]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("(//div[text()='Single'])[2]")).click();
    }

    @Then("I Clicked on Proceed button in Lead details tab- Personal details section")
    public void i_clicked_on_proceed_button_in_lead_details_tab_personal_details_section() throws InterruptedException {
        driver.findElement(By.xpath("(//div[@class='child']//button)[2]")).click();
        Thread.sleep(1000);
    }

    @Then("Address line One")
    public void Address_line_One() {
        driver.findElement(By.id("addline1")).sendKeys("Hyderabad");
    }

    @Then("Selected the State in Lead details tab- Contact details section")
    public void selected_the_state_in_lead_details_tab_contact_details_section() {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-item'])[2]")).click();
        driver.findElement(By.xpath("//div[text()='Andhra Pradesh']")).click();

        JavascriptExecutor js = (JavascriptExecutor) driver;

        long lastHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            try {
                Thread.sleep(1000);  // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long newHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

            if (newHeight == lastHeight) {
                break;  // Reached the bottom
            }
            lastHeight = newHeight;
        }
    }

    @Then("Clicked on Proceed button in Lead details tab- Contact details section")
    public void clicked_on_proceed_button_in_lead_details_tab_contact_details_section() throws InterruptedException {
        driver.findElement(By.xpath("//span[text()='Previous']/following::span[text()='Proceed']")).click();
        Thread.sleep(4000);
    }

    @Then("Selected the Educational Qualification in Lead details tab- Professional details section")
    public void selected_the_educational_qualification_in_lead_details_tab_professional_details_section() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class,'ant-select ant-select-borderless')]//div)[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[@title='Graduate']")).click();
    }

    @Then("Selected the Profession Type in Lead details tab- Professional details section")
    public void selected_the_profession_type_in_lead_details_tab_professional_details_section() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class,'ant-select ant-select-borderless')]//div)[2]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Salaried']")).click();
    }

    @Then("Selected the Income Group in Lead details tab- Professional details section")
    public void selected_the_income_group_in_lead_details_tab_professional_details_section() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class,'ant-select ant-select-borderless')]//div)[3]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Between INR 2.5 Lakh and INR 5 Lakh'])[2]")).click();
    }

    @Then("Clicked on Proceed button in Lead details tab- Professional details section")
    public void clicked_on_proceed_button_in_lead_details_tab_professional_details_section() throws InterruptedException {
        driver.findElement(By.xpath("//span[text()='Proceed']")).click();
        Thread.sleep(1000);
    }

    @Then("Selected the Smoking Status in Lead details tab- Additional details section")
    public void selected_the_smoking_status_in_lead_details_tab_additional_details_section() {
        driver.findElement(By.xpath("(//div[@class='ant-select-selector']//span)[2]")).click();
        driver.findElement(By.xpath("(//div[text()='Non-Smoker'])[2]")).click();
    }

    @Then("Clicked on Proceed button in Lead details tab- Additional details section")
    public void clicked_on_proceed_button_in_lead_details_tab_additional_details_section() {
        driver.findElement(By.xpath("//span[text()='Proceed']")).click();
    }

    @Then("Selected the Age of Life Insured or Policy Holder in Need Analysis tab")
    public void selected_the_age_of_life_insured_policy_holder_in_need_analysis_tab() {
        driver.findElement(By.className("ant-select-selection-item")).click();
        driver.findElement(By.xpath("//div[@title='26-35 years']//div[1]")).click();
    }

    @Then("Selected the Occupation of Life Insured or Policy Holder in Need analysis tab")
    public void selected_the_occupation_of_life_insured_policy_holder_in_need_analysis_tab() {
        driver.findElement(By.id("Occupation")).click();
        driver.findElement(By.xpath("(//div[text()='Salaried'])[2]")).click();

    }

    @Then("Selected the Priority {int} in Need Analysis tab")
    public void selected_the_priority_in_need_analysis_tab(Integer int1) {
        driver.findElement(By.id("keygoals1")).click();
        driver.findElement(By.xpath("//div[@title='Life Cover']//div[1]")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        long lastHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            try {
                Thread.sleep(1000);  // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long newHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

            if (newHeight == lastHeight) {
                break;  // Reached the bottom
            }
            lastHeight = newHeight;
        }
    }

    @Then("Selected the Risk Profile in Need analysis tab")
    public void selected_the_risk_profile_in_need_analysis_tab() {
        driver.findElement(By.id("riskProfile")).click();
        driver.findElement(By.xpath("//div[text()='Medium Risk: Balanced, Moderate Growth']")).click();
    }

    @Then("Clicked on Submit in Need analysis tab")
    public void clicked_on_submit_in_need_analysis_tab() throws InterruptedException {
        driver.findElement(By.xpath("//span[text()='Submit']")).click();
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,700)", "");
        Thread.sleep(12000);
    }

    @Then("Select the product ASIP")
    public void Select_the_product_ASIP() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='Aviva SIP-Signature Investment Plan']")).click();
        Thread.sleep(12000);
    }

    @Then("Select the product FortunePlus")
    public void Select_the_product_FortunePlus() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='Aviva Fortune Plus']")).click();
        Thread.sleep(12000);
    }

    @Then("Select the product Pos Dhansuraksha")
    public void Select_the_product_Pos_Dhansuraksha() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='POS Aviva Dhan Suraksha']")).click();
        Thread.sleep(12000);
    }

    @Then("Select the product ANWB")
    public void Select_the_product_ANWB() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='Aviva New Wealth Builder']")).click();
        Thread.sleep(12000);
    }

    @Then("Select the product GIP")
    public void Select_the_product_GIP() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='Aviva Signature Guaranteed Income Plan']")).click();
        Thread.sleep(10000);
    }
    @Then("Select the product MIP")
    public void Select_the_product_MIP() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='Aviva Signature Monthly Income Plan']")).click();
        Thread.sleep(10000);
    }

    @Then("Select the product ANFIB")
    public void Select_the_product_ANFIB() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='Aviva New Family Income Builder']")).click();
        Thread.sleep(10000);
    }

    @Then("Select the product 3DTerm")
    public void Select_the_product_3DTerm() throws InterruptedException {
        driver.findElement(By.xpath("//p[text()='Aviva Signature 3D Term Plan']")).click();
        Thread.sleep(10000);
    }


    @Then("CLick on BI button")
    public void CLick_on_BI_button() throws InterruptedException {
        driver.findElement(By.xpath("//span[text()='Benefit Illustration']")).click();
        Thread.sleep(15000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,800)", "");
        Thread.sleep(3000);
    }
    @Then("Select the Gender")
    public void Select_the_Gender() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[1]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);

        WebElement element1 = driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element1).click().perform();
        Thread.sleep(1000);

        WebElement element2 = driver.findElement(By.xpath("//div[text()='Male']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element2).click().perform();
        Thread.sleep(1000);
    }


    @Then("CLick on BI button Select the gender in Bi Page")
    public void Select_the_gender_in_Bi_Page () throws InterruptedException {
        driver.findElement(By.xpath("//span[text()='Benefit Illustration']")).click();
        Thread.sleep(15000);

        WebElement element = driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[1]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

        WebElement element1 = driver.findElement(By.xpath("//div[@title='Male']//div[1]"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
        Thread.sleep(1000);

    }
    @Then("Payment Frequency MIP")
    public void Payment_Frequency_MIP () throws InterruptedException {
        driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[3]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Annual'])[2]")).click();
    }
    @Then("Plan Option MIP")
    public void Plan_Option_MIP () throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[4]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='A'])[2]")).click();
    }

    @Then("Premium Payment Term MIP")
    public void Premium_Payment_Term_MIP() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[5]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='10'])[2]")).click();
    }
    @Then("Annual Premium MIP")
    public void Annual_Premium_MIP() throws InterruptedException {
        driver.findElement(By.xpath("//input[@placeholder='Enter Annual Premium']")).sendKeys("48000");
        Thread.sleep(1000);
    }

    @Then("POSPs or Non POSPs Channel")
    public void POSPs_or_Non_POSPs_Channel() {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search'])[3]")).click();
        driver.findElement(By.xpath("(//div[text()='Non-POS'])[2]")).click();
    }

    @Then("POSPs or Non POSPs Channel GIP")
    public void POSPs_or_Non_POSPs_Channel_GIP() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search'])[3]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='POSPs'])[2]")).click();
    }
    @Then("POSPs or Non POSPs Channel GIP NON")
    public void POSPs_or_Non_POSPs_Channel_GIP_NON() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search'])[3]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Other Than POSPs'])[2]")).click();
    }

    @Then("POSPs or Non POSPs Channel 3D NON")
    public void POSPs_or_Non_POSPs_Channel_3D_NON() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search'])[3]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Non-POS'])[2]")).click();
    }

    @Then("POSPs or Non POSPs Channel 3D POS")
    public void POSPs_or_Non_POSPs_Channel_3D_POS() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search'])[3]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='POS'])[2]")).click();
    }

    @Then("Policy Term 3DTerm")
    public void Policy_Term_3DTerm() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[4]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='10'])[2]")).click();
    }

    @Then("Payment Frequency 3DTerm")
    public void Payment_Frequency_3DTerm() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[5]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Annual'])[2]")).click();
    }

    @Then("Type of Occupation")
    public void Type_of_Occupation() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[6]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Salaried'])[2]")).click();
    }

    @Then("Do You Smoke")
    public void Do_You_Smoke() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[7]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='No'])[2]")).click();
    }

    @Then("SA Type")
    public void SA_Type() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[8]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Low SA(5-50 Lacs)']")).click();
    }

    @Then("Options of the product")
    public void Options_of_the_product() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[10]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='3D- Family Plus']")).click();
    }

    @Then("Premium Option 3DTerm")
    public void Premium_Option_3DTerm() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[11]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Regular Premium']")).click();
    }

    @Then("Sum Assured")
    public void Sum_Assured() {
        driver.findElement(By.xpath("//input[@placeholder='Enter Sum Assured']")).sendKeys("500000");
    }

    @Then("Plan Options Type")
    public void Plan_Options_Type() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[4]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Signature Investor'])[2]")).click();
    }

    @Then("Policy Term one")
    public void Policy_Term_one() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[5]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='10'])[2]")).click();
    }

    @Then("Payment Frequency")
    public void Payment_Frequency() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[7]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Annual'])[2]")).click();
    }

    @Then("Annual Premium one")
    public void Annual_Premium_one() {
        driver.findElement(By.xpath("//label[text()='Annual Premium']/following::input")).sendKeys("75000");

    }

    @Then("Down to Bottom")
    public void Down_to_Bottom() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        long lastHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

            try {
                Thread.sleep(1000);  // Adjust the sleep time as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long newHeight = (long) js.executeScript("return Math.max( document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight );");

            if (newHeight == lastHeight) {
                break;  // Reached the bottom
            }
            lastHeight = newHeight;
        }
        Thread.sleep(1000);
    }

    @Then("Annual premium")
    public void Annual_pemium() {
        driver.findElement(By.xpath("(//input[@class='ant-select-selection-search-input'])[3]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-select-item-option-content'])[1]")).click();
    }

    @Then("Annual premium Dhansuraksha")
    public void Annual_premium_Dhansuraksha() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//label[text()='Annual Premium']/following::input");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.sendKeys("40000");
    }

    @Then("Scroll little up visible policy term dropdown")
    public void Scroll_little_up_visible_policy_term() throws InterruptedException {
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,1200)", "");
        Thread.sleep(1000);
    }


    @Then("Selected the Policy term dropdown")
    public void Selected_the_Policy_term_dropdown() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[3]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-select-selector'])[3]");
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element1.click();
        Thread.sleep(1000);
       // driver.findElement(By.xpath("(//input[@class='ant-select-selection-search-input'])[3]")).click();
        driver.findElement(By.xpath("(//div[@class='ant-select-item-option-content'])[1]")).click();
    }

    @Then("Selected the Plan type")
    public void Selected_the_Plan_type() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//span[@class='ant-select-selection-search'])[3]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        Thread.sleep(1000);

        driver.findElement(By.xpath("//div[text()='Signature GenX']")).click();
    }

    @Then("Premium Payment Option")
    public void Premium_Payment_Option() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[4]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("(//div[text()='Regular Premium'])[2]")).click();
    }

    @Then("PolicyTerm")
    public void Policy_Term() {
        driver.findElement(By.xpath("(//label[text()='PolicyTerm']/following::input)[1]")).click();
        driver.findElement(By.xpath("(//div[text()='20'])[2]")).click();
    }

    @Then("Down to 500")
    public void Down_to_500() throws InterruptedException {
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,500)", "");
        Thread.sleep(1000);
    }

    @Then("PolicyTerm FortunePlus")
    public void Policy_Term_FortunePlus() {
        driver.findElement(By.xpath("(//label[text()='PolicyTerm']/following::input)[1]")).click();
        driver.findElement(By.xpath("(//div[text()='15'])[2]")).click();
    }

    @Then("Premium Paying Term")
    public void Premium_Paying_Term() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class, 'ant-select-selector')])[4]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator1 = By.xpath("(//div[text()='5'])[2]");
        WebElement element1 = wait1.until(ExpectedConditions.elementToBeClickable(elementLocator1));
        element1.click();

       // driver.findElement(By.xpath("(//div[@title='15']//div)[1]")).click();
    }

    @Then("Payment Frequency FortunePlus")
    public void Payment_Frequency_FortunePlus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class, 'ant-select-selector')])[5]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

        //driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/div/div[1]/form/div[4]/div[3]/div/div[2]/div/div/div/div/span[1]")).click();
        driver.findElement(By.xpath("//div[text()='Annual']")).click();
    }

    @Then("Annual Premium FortunePlus")
    public void Annual_Premium_FortunePlus() {
        driver.findElement(By.xpath("(//label[text()='Annual Premium']/following::input)[1]")).sendKeys("250000");
    }

    @Then("Sum Assured Option")
    public void Sum_Assured_Option() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[8]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Min']")).click();
    }

    @Then("Balance Fund FortunePlus")
    public void Balance_Fund_FortunePlus() throws InterruptedException {
        driver.findElement(By.xpath("(//label[text()='Sum Assured Option']/following::input)[2]")).sendKeys("100");
        Thread.sleep(1000);
    }


    @Then("Cover Level")
    public void Cover_Level() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class, 'ant-select-selector')])[6]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        driver.findElement(By.xpath("(//div[text()='10'])[2]")).click();
    }

    @Then("Selected Payment frequency SIP")
    public void Selected_Payment_frequency_SIP() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class, 'ant-select-selector')])[7]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        driver.findElement(By.xpath("//div[@title='Annual']//div[1]")).click();
    }

    @Then("Selected Payment frequency ANWB")
    public void Selected_Payment_frequency_ANWB() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class, 'ant-select-selector')])[5]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        //driver.findElement(By.xpath("(//label[text()='Payment Frequency']/following::input)[1]")).click();
        driver.findElement(By.xpath("//div[@title='Annual']//div[1]")).click();
    }

    @Then("Selected Payment frequency ANFIB")
    public void Selected_Payment_frequency_ANFIB() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class, 'ant-select-selector')])[5]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        driver.findElement(By.xpath("(//div[text()='Annual'])[2]")).click();
    }

    @Then("Annual Premium")
    public void Annual_Premium() {
        driver.findElement(By.xpath("(//label[text()='Annual Premium']/following::input)[1]")).sendKeys("60000");
    }

    @Then("Entered Balance Fund")
    public void Entered_Balance_Fund() {
        driver.findElement(By.xpath("(//label[text()='Sum Assured']/following::input)[2]")).sendKeys("100");
    }


    @Then("Entered Annualized premium ANWB")
    public void Entered_Annualized_premium_ANWB() {
        driver.findElement(By.xpath("//input[@placeholder='Enter Annual Premium']")).sendKeys("75000");
    }

    @Then("Annual Premium ANFIB")
    public void Annual_Premium_ANFIB() {
        driver.findElement(By.xpath("//label[text()='Annual Premium']/following::input")).sendKeys("40000");
    }

    @Then("Clicked on BI Generate Button ANFIB")
    public void Clicked_on_BI_Generate_Button_ANFIB() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[1]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
    }

    @Then("Scroll to TOP")
    public void Scroll_to_TOP() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        By elementLocator = By.xpath("//div[@class='ant-message']//div[1]");
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
        if (element.isDisplayed()) {
            JavascriptExecutor js2 = (JavascriptExecutor) driver;
            js2.executeScript("window.scrollTo(0,0)", "");
        }
        Thread.sleep(1000);
//        driver.quit();

    }

    @Then("Clicked on BI Generate Button SIP")
    public void Clicked_on_BI_Generate_Button() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class,'ant-col ant-col-xs-24')]//button)[3]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        //driver.findElement(By.xpath("(//div[contains(@class,'ant-col ant-col-xs-24')]//button)[3]")).click();
    }

    @Then("Clicked on BI Generate Button FortunePlus")
    public void Clicked_on_BI_Generate_Button_FortunePlus() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class,'ant-col ant-col-xs-24')]//button)[3]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        //driver.findElement(By.xpath("(//div[contains(@class,'ant-col ant-col-xs-24')]//button)[3]")).click();
    }

    @Then("Clicked on BI Generate Button Dhansuraksha")
    public void Clicked_on_BI_Generate_Button_Dhansuraksha() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[1]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        //driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[1]")).click();
    }

    @Then("Clicked on BI Generate Button GIP")
    public void Clicked_on_BI_Generate_Button_GIP() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[2]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

        //driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[2]")).click();
    }
    @Then("Clicked on BI Generate Button MIP")
    public void Clicked_on_BI_Generate_Button_MIP() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//button[@class='ant-btn ant-btn-default']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

        //driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[2]")).click();
    }



    @Then("Clicked on BI Generate Button ANWB")
    public void Clicked_on_BI_Generate_Button_ANWB() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[1]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        //driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[1]")).click();

    }

    @Then("Clicked on BI Generate Button 3Dterm")
    public void Clicked_on_BI_Generate_Button_GIP_3Dterm() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[2]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        //driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//button)[2]")).click();
    }


    @Then("Click on the WIP button on home page")
    public void click_on_the_wip_button_on_home_page() throws InterruptedException {
        driver.findElement(By.xpath("(//div[@bordered='false']//span)[3]")).click();
        Thread.sleep(2000);
    }

    @Then("CLicked on Resume on WIP")
    public void clicked_on_resume_on_wip() throws InterruptedException {
        driver.findElement(By.xpath("(//button[@class='show_more_less'])[1]")).click();
        Thread.sleep(6000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(300,0)", "");
        Thread.sleep(1000);
    }

    @Then("Click on Mandatory document upload button")
    public void Click_on_Mandatory_document_upload_button() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-tabs-tab-btn'])[2]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

//        driver.findElement(By.xpath("(//div[@class='ant-tabs-tab-btn'])[2]")).click();
//        Thread.sleep(1000);
    }

    @Then("Select Document Type")
    public void Select_Document_Type() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//*[@id=\"Select Document Type\"]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

        //driver.findElement(By.className("select-input")).click();
    }

    @Then("Selected the Doc as Recent photo")
    public void Selected_the_Doc_as_Recent_photo() {
        driver.findElement(By.xpath("//div[@title='Recent Photo']//div[1]")).click();
    }

    @Then("Selected the Doc as ID Address proof")
    public void Selected_the_Doc_as_ID_Address_proof() throws InterruptedException {
        driver.findElement(By.xpath("//div[@title='ID/Address proof']//div[1]")).click();
        Thread.sleep(2000);
    }

    @Then("Selected the Doc as ID Address proof two")
    public void Selected_the_Doc_as_ID_Address_proof_two() throws InterruptedException {
        driver.findElement(By.xpath("(//select[@class='select-input']//option)[2]")).click();
        Thread.sleep(500);
    }

    @Then("Selected the Doc as Bank AC Proof")
    public void Selected_the_Doc_as_Bank_AC_Proof() throws InterruptedException {
        driver.findElement(By.xpath("(//select[@id='Select Document Type']//option)[2]")).click();
        Thread.sleep(500);
    }

    @Then("Selected the Doc as Joint photo")
    public void Selected_the_Doc_as_Joint_photo() throws InterruptedException {
        driver.findElement(By.xpath("//option[text()='Joint Photo']")).click();
        Thread.sleep(500);
    }


    @Then("Upload the Document")
    public void Upload_the_Document() throws InterruptedException {
        WebElement browse = driver.findElement(By.xpath("//span[@class='ant-upload']//input[1]"));
        browse.sendKeys("C:\\Users\\User\\Desktop\\Sample Passport.png");
        Thread.sleep(3000);
    }

    @Then("Click on OK button after uploading the document")
    public void Click_on_OK_button_after_uploading_the_document() throws InterruptedException {

        WebElement slider = driver.findElement(By.xpath("(//div[@class='ant-slider-handle'])[3]"));
        int width = slider.getSize().getWidth();
        // Initialize Actions class
        Actions action = new Actions(driver);
        // Move the slider from left end to right
        action.clickAndHold(slider).moveByOffset(width, 0).release().perform();

        Thread.sleep(2000);
        driver.findElement(By.xpath("(//div[@class='ant-modal-footer']//button)[2]")).click();
        Thread.sleep(10000);
    }

    @Then("Click Next")
    public void Click_Next_on_Phot_ID_Proof() throws InterruptedException {
        WebElement tabElement = driver.findElement(By.xpath("(//button[contains(@class,'ant-btn ant-btn-default')])[3]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).click().perform();
        Thread.sleep(14000);
        //driver.findElement(By.xpath("(//button[contains(@class,'ant-btn ant-btn-default')])[3]")).click();
    }


    @Then("Click on Photo Id Proof")
    public void click_on_photo_id_proof() throws InterruptedException {
        WebElement tabElement = driver.findElement(By.xpath("//div[text()='Photo ID Proof']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).click().perform();
        //driver.findElement(By.xpath("//div[text()='Photo ID Proof']")).click();
        Thread.sleep(3000);
    }
    @Then("Upload the Document Sample adhar")
    public void Upload_the_Document_Sample_adhar() throws InterruptedException {
        WebElement browse = driver.findElement(By.xpath("//span[@class='ant-upload']//input[1]"));
        browse.sendKeys("C:\\Users\\User\\Downloads\\Adhar.png");
        Thread.sleep(1000);
    }


    @Then("Click on Address Proof")
    public void click_on_address_proof() throws InterruptedException {
        WebElement tabElement = driver.findElement(By.xpath("//div[text()='Address Proof']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).click().perform();
//        driver.findElement(By.xpath("//div[text()='Address Proof']")).click();
        Thread.sleep(2000);
    }

    @Then("CLick on Owner bank AC Proof")
    public void CLick_on_Owner_bank_AC_Proof() throws InterruptedException {
        WebElement tabElement = driver.findElement(By.xpath("//div[text()='Owner Bank A/c Proof']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).click().perform();
        //driver.findElement(By.xpath("//div[text()='Owner Bank A/c Proof']")).click();
        Thread.sleep(2000);
    }

    @Then("CLick on Joint photo")
    public void c_lick_on_joint_photo() throws InterruptedException {
        WebElement tabElement = driver.findElement(By.xpath("//div[text()='Joint Photo - FOS and Customer']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).click().perform();
        //driver.findElement(By.xpath("//div[text()='Joint Photo - FOS and Customer']")).click();
        Thread.sleep(3000);
    }

    @Then("Click on Proceed button")
    public void click_on_Proceed_button() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-col']//button)[2]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

//        driver.findElement(By.xpath("(//div[@class='ant-col']//button)[2]")).click();
        Thread.sleep(8000);
    }

    @Then("Click on Continue - E-Insurance Account Details")
    public void click_on_continue_e_insurance_account_details() throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='mb-2 einsuranceFooterChild']//button[1]")).click();
        Thread.sleep(8000);
    }

    @Then("Select title")
    public void Select_title() throws InterruptedException {
        driver.findElement(By.xpath("(//div[@class='ant-select-selector']//span)[2]")).click();
        driver.findElement(By.xpath("//div[text()='Mr.']")).click();
    }



    @Then("Click on Next Button")
    public void click_on_next_button() throws InterruptedException {
        driver.findElement(By.xpath("(//button[@class='ant-btn ant-btn-default'])[2]")).click();
        Thread.sleep(8000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,500)", "");
        Thread.sleep(4000);
    }

    @Then("Select age proof on About Customer")
    public void select_age_proof_on_about_customer() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//input[@id='AgeProof']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

       // driver.findElement(By.xpath("//input[@id='AgeProof']")).click();
        driver.findElement(By.xpath("//div[text()='Aadhar Card']")).click();
        Thread.sleep(500);
    }
    @Then("Selected the Marital Status")
    public void selected_the_marital_status() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-select-selector'])[4]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

        //driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[3]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("(//div[text()='Single'])[2]")).click();
    }

    @Then("Select Educational Qualification")
    public void Select_Educational_Qualification() {

        WebElement tabElement = driver.findElement(By.xpath("(//span[@class='ant-select-selection-item']/following::div[@class='ant-select-selector'])[8]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).click().perform();

        driver.findElement(By.xpath("//div[text()='SSE']")).click();
    }

    @Then("Select Occupation")
    public void Select_Occupation() throws InterruptedException {
        WebElement tabElement = driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[10]"));
        Actions actions = new Actions(driver);
        actions.moveToElement(tabElement).click().perform();

        //driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[3]/form/div/div[2]/div[1]/div[18]/div/div[2]/div/div/div/div/span[2]")).click();
        driver.findElement(By.xpath("//div[text()='Business Owners/ Self Employed']")).click();
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,950)", "");
        Thread.sleep(1500);
    }

    @Then("Tax Purposes in Jurisdiction")
    public void Tax_Purposes_in_Jurisdiction() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='ant-radio']//input)[1]")).click();
        driver.findElement(By.xpath("(//div[contains(@class,'ant-row mb-2')]//p)[1]")).click();
        Thread.sleep(1000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,950)", "");
        Thread.sleep(1500);
    }

    @Then("Select the City one")
    public void select_the_city_one() throws InterruptedException {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[13]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[text()='A.I.Area']")).click();
        Thread.sleep(500);
    }

    @Then("Enter Pincode one")
    public void enter_pincode_one() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//input[@id='Pincode']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.sendKeys("500000");
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,1200)", "");
        Thread.sleep(1000);
    }

    @Then("Enter Email no in About cutomer page")
    public void Enter_Email_Id_in_About_cutomer_page() throws InterruptedException {
         driver.findElement(By.xpath("//input[@id='EmailID']")).sendKeys("jogu.satyanarayana@iorta.in");
         Thread.sleep(1000);
    }
    @Then("Enter Mobile in About cutomer page")
    public void Enter_Mobile_no_in_About_cutomer_page() throws InterruptedException {
        driver.findElement(By.xpath("//input[contains(@class,'ant-input phone-no')]")).sendKeys("9666191927");
        Thread.sleep(1000);
    }

    @Then("Permanent Address same as Current Address")
    public void Permanent_Address_same_as_Current_Address() throws InterruptedException {
       // driver.findElement(By.xpath("(//span[@class='ant-checkbox']//input)[1]")).click();
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,2000)", "");
        Thread.sleep(1000);
    }

    @Then("Enter Name of the Business")
    public void enter_name_of_the_business() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='Nameofthebusiness']")).sendKeys("Iorta");
        Thread.sleep(1000);
    }

    @Then("Enter Business Address Line")
    public void Enter_Business_Address_Line() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='BusinessAddressLine1']")).sendKeys("Hyd");
        Thread.sleep(1000);
    }

    @Then("Select State")
    public void select_state() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//input[@id='BusinessState']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        Thread.sleep(1000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//input[@id='BusinessState']");
        WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element1.click();
        Thread.sleep(1000);

        WebElement element2 = driver.findElement(By.xpath("//div[text()='Andhra Pradesh']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element2).click().perform();

    }


    @Then("Select the City")
    public void select_the_city() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[contains(@class, 'ant-select-selector')])[16]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();
        Thread.sleep(1000);

        WebElement element1 = driver.findElement(By.xpath("(//div[text()='A P S E Board Bella Vista'])[2]"));
        Boolean isVisible = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].offsetParent !== null;", element1);
        if (isVisible) {
            element1.click(); // Or perform any other action you need
        } else {
            // Handle the case where the element is not visible
        }

//        WebElement element1 = driver.findElement(By.xpath("//div[@title='A P S E Board Bella Vista']//div[1]"));
//        Actions actions = new Actions(driver);
//        actions.moveToElement(element1).click().perform();

        //driver.findElement(By.xpath("//div[text()='80 Feet Road']")).click();
    }

    @Then("Enter Pincode")
    public void enter_pincode() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='BusinessPincode']")).sendKeys("500000");
        Thread.sleep(1000);
    }

    @Then("Enter Tel")
    public void Enter_Tel() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='BusinessTelResi']")).sendKeys("0998656456767");
        Thread.sleep(1000);
    }

    @Then("Enter Email Id")
    public void enter_email_id() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='BusinessEmailID']")).sendKeys("jogu.satyanarayana@iorta.in");
        Thread.sleep(1000);
    }

    @Then("Enter Annual Income")
    public void enter_annual_income() throws InterruptedException {
        driver.findElement(By.xpath("//input[@id='yourInnualIncome']")).sendKeys("50000");
        Thread.sleep(1000);
    }

    @Then("Select Income Tax PAN")
    public void select_income_tax_pan() {
        driver.findElement(By.xpath("(//div[contains(@class, 'ant-select-selector')])[17]")).click();
        driver.findElement(By.xpath("//div[text()='Form 60/61']")).click();
    }

    @Then("Entered Name as per Bank Account - Finacial Details tab")
    public void entered_name_as_per_bank_account_finacial_details_tab() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("(//div[@class='ant-form-item-control-input-content']//input)[1]");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.sendKeys("Satya");

//        driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//input)[1]")).sendKeys("Satya");
    }

    @Then("Entered Bank Account number")
    public void entered_bank_account_number() {
        driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//input)[2]")).sendKeys("9786437944766");
    }

    @Then("Select Bank Name")
    public void select_bank_name() {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search']//input)[1]")).click();
        driver.findElement(By.xpath("(//div[contains(@class,'ant-select-item ant-select-item-option')]//div)[1]")).click();
    }

    @Then("Select Bank Account Type")
    public void select_bank_account_type() {
        driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[2]")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        By elementLocator = By.xpath("//div[text()='Savings Account']");
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(elementLocator));
        element.click();

//        driver.findElement(By.xpath("//div[text()='Savings Account']")).click();
    }

    @Then("Entered MICR Code")
    public void Entered_MICR_Code() {
        driver.findElement(By.xpath("(//input[@placeholder='Please Enter'])[3]")).sendKeys("987645323");
    }

    @Then("Entered IFSC Code")
    public void Entered_IFSC_Code() {
        driver.findElement(By.xpath("//input[@id='IFSCCode']")).sendKeys("ADCB0DJEY5T");
    }

    @Then("Entered Customer Height in Health Details DGH section")
    public void Entered_Customer_Height_in_Health_Details_DGH_section() {
        driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//input)[1]")).sendKeys("165");
    }

    @Then("Entered Customer Weight")
    public void entered_customer_weight() throws InterruptedException {
        driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//input)[2]")).sendKeys("50");
        Thread.sleep(1000);
    }

    @Then("Entered First Name in Nomination Details")
    public void entered_first_name_in_nomination_details() {
        driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//input)[1]")).sendKeys("Anvesh");
    }

    @Then("Entered Last Name")
    public void entered_last_name() throws InterruptedException {
        driver.findElement(By.xpath("(//div[@class='ant-form-item-control-input-content']//input)[2]")).sendKeys("s");
        Thread.sleep(500);
    }

    @Then("Selected Relationship With Life To Be Insured")
    public void selected_relationship_with_life_to_be_insured() throws InterruptedException {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search'])[1]")).click();
        driver.findElement(By.xpath("//div[text()='Brother']")).click();
        Thread.sleep(500);
    }

    @Then("Entered Reason")
    public void entered_reason() {
        driver.findElement(By.xpath("(//label[text()='Reason']/following::input)[1]")).sendKeys("Ok");
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,200)", "");
    }

    @Then("Enterd Date Of Birth")
    public void enterd_date_of_birth() {
        driver.findElement(By.xpath("//div[@class='ant-picker-input']//input[1]")).sendKeys("03/01/1998");
        driver.findElement(By.xpath("(//div[text()='3'])[1]")).click();
    }

    @Then("Select Gender")
    public void select_gender() {
        driver.findElement(By.xpath("(//span[@class='ant-select-selection-search']//input)[2]")).click();
        driver.findElement(By.xpath("//div[text()='Male']")).click();
    }

    @Then("Select Address same as LI Check box")
    public void select_address_same_as_li_check_box() {
        driver.findElement(By.xpath("//span[@class='ant-checkbox']//input[1]")).click();
    }

    @Then("Wait for few sec and click Next")
    public void Wait_for_few_sec_and_click_Next() throws InterruptedException {
        Thread.sleep(4000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(12000);
    }

    @Then("Select Relationship with Life to be Insured in Proposer details")
    public void Select_Relationship_with_Life_to_be_Insured_in_Proposer_details () throws InterruptedException {

        driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//div[text()='Brother']")).click();

    }
    @Then("Select Age Proof in Proposer details")
    public void Select_Age_Proof_in_Proposer_details () throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[1]/div[29]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[1]/div[29]/div/div/div[2]/div[1]/div/div/div")).click();
        WebElement element1 = driver.findElement(By.xpath("//div[text()='Aadhar Card']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
//        driver.findElement(By.xpath("//div[text()='Aadhar Card']")).click();
    }
    @Then("Select the City in Proposer details")
    public void Select_the_City_in_Proposer_details () throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[7]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[7]/div/div/div[2]/div[1]/div/div/div")).click();
        WebElement element1 = driver.findElement(By.xpath("//div[text()='A P S E Board Bella Vista']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
//        driver.findElement(By.xpath("//div[text()='A P S E Board Bella Vista']")).click();

    }
    @Then("Entered the Pincode in Proposer details")
    public void Entered_the_Pincode_in_Proposer_details(){
        driver.findElement(By.xpath("//input[@id='Pincode']")).sendKeys("500009");

    }
    @Then("Select the Educational Qualification in Proposer details")
    public void Select_the_Educational_Qualification_in_Proposer_details() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[14]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[14]/div/div/div[2]/div[1]/div/div/div")).click();
        WebElement element1 = driver.findElement(By.xpath("(//div[text()='Graduate'])[2]"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
//        driver.findElement(By.xpath("(//div[text()='Graduate'])[2]")).click();

    }
    @Then("Select Occupation in Proposer details")
    public void Select_Occupation_in_Proposer_details () throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[15]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[15]/div/div/div[2]/div[1]/div/div/div")).click();
        WebElement element1 = driver.findElement(By.xpath("//div[text()='Salaried']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
//        driver.findElement(By.xpath("//div[text()='Salaried']")).click();

    }
    @Then("Select the Source Of Income in Proposer details")
    public void Select_the_Source_Of_Income_in_Proposer_details() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[16]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[16]/div/div/div[2]/div[1]/div/div/div")).click();

        WebElement element1 = driver.findElement(By.xpath("(//div[text()='Salary'])[2]"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
        Thread.sleep(1000);
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,2500)", "");
//        driver.findElement(By.xpath("(//div[text()='Salary'])[2]")).click();

    }
    @Then("Enter Name of the Organisation")
    public void Enter_Name_of_the_Organisation(){


        driver.findElement(By.xpath("//*[@id=\"nameOfOrganisation\"]")).sendKeys("Iorta");

    }
    @Then("Enter Address Line one")
    public void Enter_Address_Line_one(){

        driver.findElement(By.xpath("//*[@id=\"AddressLine1Bussiness\"]")).sendKeys("HYD");


    }
    @Then("Select the State")
    public void Select_the_State() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[28]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

        WebElement element1 = driver.findElement(By.xpath("//div[text()='Andhra Pradesh']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
        Thread.sleep(1000);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[28]/div/div/div[2]/div[1]/div/div/div")).click();
//        driver.findElement(By.xpath("//div[text()='Andhra Pradesh']")).click();

    }
    @Then("Select the City in Proposer details two")
    public void Select_the_City_in_Proposer_details_two() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[29]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

        WebElement element1 = driver.findElement(By.xpath("(//div[text()='A P S E Board Bella Vista'])[2]"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
        Thread.sleep(1000);
//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[29]/div/div/div[2]/div[1]/div/div/div")).click();
//        driver.findElement(By.xpath("(//div[text()='A P S E Board Bella Vista'])[2]")).click();

    }
    @Then("Enter Pincode in Proposer details two")
    public void Enter_Pincode_in_Proposer_details_two(){
        driver.findElement(By.xpath("((//label[text()='Pincode'])[2]/following::input)[1]")).sendKeys("500004");

    }
    @Then("Enter Work phone number")
    public void Enter_Work_phone_number(){
        driver.findElement(By.xpath("(//label[text()='Work phone number']/following::input)[1]")).sendKeys("9666191927");

    }
    @Then("Enter Proposers Annual Income")
    public void Enter_Proposers_Annual_Income(){
        driver.findElement(By.xpath("//input[@id='Proposer']")).sendKeys("40000");
        JavascriptExecutor js2 = (JavascriptExecutor) driver;
        js2.executeScript("window.scrollTo(0,3200)", "");

    }
    @Then("Enter Name as per Bank Account in Proposer details")
    public void Enter_Name_as_per_Bank_Account_in_Proposer_details(){
        driver.findElement(By.xpath("(//input[@name='nameasperBankaccount'])[2]")).sendKeys("Sathya");

    }
    @Then("Enter Bank Account Number in proposer details")
    public void Enter_Bank_Account_Number_in_proposer_details(){
        driver.findElement(By.xpath("(//input[@name='bankAccountnumber'])[1]")).sendKeys("1234567654");

    }
    @Then("Select Bank Name in Proposer details")
    public void Select_Bank_Name_in_Proposer_details() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[44]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

        WebElement element1 = driver.findElement(By.xpath("(//div[text()='Akhand Anand Coop Bank'])[2]"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
        Thread.sleep(1000);


//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[44]/div/div/div[2]/div[1]/div/div/div")).click();
//        driver.findElement(By.xpath("(//div[text()='Akhand Anand Coop Bank'])[2]")).click();

    }
    @Then("Select Bank Account Type in proposer details")
    public void Select_Bank_Account_Type_in_proposer_details() throws InterruptedException {
        WebElement element = driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[45]/div/div/div[2]/div[1]/div/div/div"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().perform();
        Thread.sleep(1000);

        WebElement element1 = driver.findElement(By.xpath("//div[text()='Savings Account']"));
        Actions actions1 = new Actions(driver);
        actions1.moveToElement(element1).click().perform();
        Thread.sleep(1000);

//        driver.findElement(By.xpath("//*[@id=\"root\"]/div[1]/div/div/div[2]/div[2]/form/div/div[2]/div[3]/div[45]/div/div/div[2]/div[1]/div/div/div")).click();
//        driver.findElement(By.xpath("//div[text()='Savings Account']")).click();

    }
    @Then("Enter MICR in Proposer details")
    public void Enter_MICR_in_Proposer_details(){
        driver.findElement(By.xpath("(//label[text()='MICR Code(9 Digits)']/following::input)[1]")).sendKeys("12345645636");

    }
    @Then("Enter IFSC Code in Proposer details")
    public void Enter_IFSC_Code_in_Proposer_details(){
        driver.findElement(By.xpath("(//input[@name='bankAccountnumber'])[2]")).sendKeys("ANDD0IJHDFD");

    }
    @Then("Click on Proceed button in Proposer details")
    public void Click_on_Proceed_button_in_Proposer_details() throws InterruptedException {
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(5000);

    }







    @Then("Selected the Doc as Self Addendum")
    public void Selected_the_Doc_as_Income_Proof() throws InterruptedException {
        driver.findElement(By.xpath("//option[text()='Self employed addendum']")).click();
        Thread.sleep(500);
    }
    @Then("Selected the Doc as IncomeProof")
    public void Selected_the_Doc_as_IncomeProof() throws InterruptedException {
        driver.findElement(By.xpath("//option[text()='Income proof']")).click();
        Thread.sleep(500);
    }

    @Then("Click on OTP Authentication Tab")
    public void click_on_otp_authentication_tab() throws InterruptedException {
        driver.findElement(By.xpath("//div[text()='OTP Authentication ']")).click();
        Thread.sleep(4000);
    }

    @Then("Select the Check box on otp auth")
    public void select_the_check_bo_and_click_on_get_otp() throws InterruptedException {
        driver.findElement(By.xpath("//span[@class='ant-checkbox']//input[1]")).click();
        Thread.sleep(1000);
    }
    @Then("Wait for some time")
    public void Wait_for_some_time() throws InterruptedException {
        Thread.sleep(5000);
    }


    @Then("Click on get otp on otp auth")
    public void Click_on_get_otp_on_otp_auth () throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='ant-card-body']//button[1]")).click();
    }

    @Then("Wait for some time to enter the otp")
    public void wait_for_sec_for_entering_otp() throws InterruptedException {
        Thread.sleep(40000);
    }
    @Then("Click on payment mode")
    public void Click_on_payment_mode () throws InterruptedException {
        driver.findElement(By.xpath("(//div[@class='ant-select-selector']//span)[2]")).click();
        Thread.sleep(1000);
    }
    @Then("Select the option Payment done through Aviva Website")
    public void Select_the_option_Payment_done_through_Aviva_Website (){
        driver.findElement(By.xpath("//div[text()='Payment Done through AVIVA Website']")).click();
    }
    @Then("Click on Proceed")
    public void Click_on_Proceed () throws InterruptedException {
        driver.findElement(By.xpath("//div[@class='paymentProceedDiv']//button[1]")).click();
        Thread.sleep(4000);

    }
    @Then("CLick on close popo-up")
    public void CLick_on_close_popo_up () throws InterruptedException {
        driver.findElement(By.xpath("//button[text()='Close']")).click();
        Thread.sleep(2000);
    }
    @Then("proceed to Esir")
    public void proceed_to_Esir (){
        driver.findElement(By.xpath("(//div[@class='paymentProceedDiv']//button)[3]")).click();

    }
    @Then("Select the SP agent")
    public void Select_the_SP_agent(){

        WebElement dropdownElement = driver.findElement(By.xpath("(//label[text()='Select Agent / SP']/following::input)[1]"));
        dropdownElement.click();
        driver.findElement(By.xpath("")).click();
    }
    @Then("Select Zone")
    public void Select_Zone (){

        WebElement dropdownElement = driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[2]"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(1);
    }
    @Then("Select Branch")
    public void Select_Branch(){
        WebElement dropdownElement = driver.findElement(By.xpath("(//div[@class='ant-select-selector'])[3]"));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByIndex(1);
    }
    @Then("Select Check box")
    public void Select_Check_box (){
        driver.findElement(By.xpath("Select Check box and click on Submit")).click();
    }

    @Then("Click on Submit button in Final page")
    public void Click_on_Submit_button_in_Final_page (){
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

//    @Then("")
//    public void (){
//
//    }
//    @Then("")
//    public void (){
//
//    }
//    @Then("")
//    public void (){
//
//    }
//    @Then("")
//    public void (){
//
//    }

}