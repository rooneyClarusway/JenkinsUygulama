package com.vytrack.pages;

import com.vytrack.utilities.BrowserUtils;
import com.vytrack.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class CreateCalendarEventPage extends BasePage {

    @FindBy(css = "[class='select2-chosen']")
    public WebElement owner;

    @FindBy(css = "[title='Create Calendar event']")
    public WebElement createCalendarEventBtn;

    @FindBy(css = "a[title='Grid Settings']")
    public WebElement gridSettingsElement;

    @FindBy(css = "a[title='Reset']")
    public WebElement resetBtnElement;

    @FindBy(css = ".grid-header-cell__label")
    public List<WebElement> headers;

    @FindBy(css = "[id^='date_selector_oro_calendar_event_form_start']")
    public WebElement startDate;
    @FindBy(css = "[id^='date_selector_oro_calendar_event_form_end']")
    public WebElement endDate;

    @FindBy(css = "[id^='time_selector_oro_calendar_event_form_start']")
    public WebElement startTime;

    @FindBy(css = "[id^='time_selector_oro_calendar_event_form_end']")
    public WebElement endTime;

    @FindBy(css = "select[class='ui-timepicker-wrapper']")
    public WebElement timeDropdown;

    @FindBy(css = "select[class='ui-datepicker-month']")
    public WebElement monthDropdown;

    @FindBy(css = "select[class='ui-datepicker-year']")
    public WebElement yearDropdown;

    @FindBy(css = "[id^='recurrence-repeat-view']")
    public WebElement repeatCheckbox;


    public void selectGridSetting(String name, boolean yesOrNo) {

        waitUntilLoaderMaskDisappear();
        BrowserUtils.clickWithWait(gridSettingsElement);

        String locator = "//td//label[text()='" + name + "']/../following-sibling::td//input";

        WebElement gridOption = Driver.get().findElement(By.xpath(locator));

        if ((yesOrNo && !gridOption.isSelected()) || (
                !yesOrNo && gridOption.isSelected())) {
            gridOption.click();
        }
    }

    public boolean verifyHeaderExists(String headerNameOrColumnName) {
        for (WebElement tableHeader : headers) {
            if (tableHeader.getText().equalsIgnoreCase(headerNameOrColumnName)) {
                return true;
            }
        }
        return false;
    }


    public void selectStartOrEndDate(String date, String startOrEnd) {
        waitUntilLoaderMaskDisappear();
        LocalDate ld = LocalDate.of(Integer.parseInt(date.substring(date.lastIndexOf("/") + 1)),
                Integer.parseInt(date.substring(0, date.indexOf("/"))),
                Integer.parseInt(date.substring(date.indexOf("/") + 1, date.lastIndexOf("/"))));

        String month = DateTimeFormatter.ofPattern("MMM").format(ld);
        int year = ld.getYear();
        int day = ld.getDayOfMonth();



        String dayLocator = "//a[@class='ui-state-default' and text()='" + day + "']";


        if (startOrEnd.equalsIgnoreCase("start")) {
            BrowserUtils.waitForVisibility(startDate, 5);
            startDate.click();
        } else {
            BrowserUtils.waitForVisibility(endDate, 5);
            endDate.click();
        }


        new Select(monthDropdown).selectByVisibleText(month);



        new Select(yearDropdown).selectByVisibleText(year + "");


        Driver.get().findElement(By.xpath(dayLocator)).click();
    }

    public void selectTomorrowDay() {
        int day = LocalDate.now().plusDays(1).getDayOfMonth();
        int month = LocalDate.now().plusDays(1).getMonth().getValue();
        try {
            waitUntilLoaderMaskDisappear();
            startDate.click();
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            BrowserUtils.clickWithWait(startDate);
        }
        Select monthSelect = new Select(monthDropdown);
        monthSelect.selectByIndex(month - 1);
        String dayLocator = "//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']";
        Driver.get().findElement(By.xpath(dayLocator)).click();
    }


    public void selectADay(int plusDays) {
        int day = LocalDate.now().plusDays(plusDays).getDayOfMonth();
        int month = LocalDate.now().plusDays(plusDays).getMonth().getValue();
        waitUntilLoaderMaskDisappear();
        startDate.click();
        Select monthSelect = new Select(monthDropdown);
        monthSelect.selectByIndex(month - 1);
        String dayLocator = "//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']";
        Driver.get().findElement(By.xpath(dayLocator)).click();
    }

    public void selectTodayDate() {
        int day = LocalDate.now().getDayOfMonth();
        startDate.click();
        String dayLocator = "//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']";
        try {
            Driver.get().findElement(By.xpath(dayLocator)).click();
        } catch (Exception e) {
            BrowserUtils.wait(1);
            Driver.get().findElement(By.xpath(dayLocator)).click();
        }
    }

    public void selectStartTime(int plusHours) {
        String time = LocalDateTime.now().plusHours(plusHours).format(DateTimeFormatter.ofPattern("h:00 a"));
        waitUntilLoaderMaskDisappear();
        String startTimeToSelect = "(//li[text()='" + time + "'])[1]";
        startTime.click();
        new WebDriverWait(Driver.get(), Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(startTimeToSelect)));
        Driver.get().findElement(By.xpath(startTimeToSelect)).click();
    }

    public void selectStartTime(String time) {
        waitUntilLoaderMaskDisappear();
        String startTimeToSelect = "(//li[text()='" + time + "'])[1]";
        try {
            startTime.click();
        } catch (WebDriverException e) {
            System.out.println(e.getMessage());
            BrowserUtils.clickWithWait(startTime);
        }
        new WebDriverWait(Driver.get(), Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(startTimeToSelect)));
        Driver.get().findElement(By.xpath(startTimeToSelect)).click();
    }

    public void selectEndTime(String time) {
        waitUntilLoaderMaskDisappear();
        String endTimeToSelect = "(//li[text()='" + time + "'])[2]";
        startTime.click();
        Driver.get().findElement(By.xpath(endTimeToSelect)).click();
    }



    public void clickOnCreateCalendarEvent() {
        waitUntilLoaderMaskDisappear();
        BrowserUtils.waitForStaleElement(createCalendarEventBtn);
        BrowserUtils.waitForClickablility(createCalendarEventBtn, 10);
        createCalendarEventBtn.click();
    }

    public void selectTodaysDate() {
        int day = LocalDate.now().getDayOfMonth();
        startDate.click();
        String dayLocator = "//table[@class='ui-datepicker-calendar']//a[text()='" + day + "']";
        try {
            Driver.get().findElement(By.xpath(dayLocator)).click();
        } catch (Exception e) {
            BrowserUtils.wait(1);
            Driver.get().findElement(By.xpath(dayLocator)).click();
        }
    }

    public String getStartDate() {
        return startDate.getAttribute("value");
    }

    public String getEndDate() {
        return endDate.getAttribute("value");
    }

    public String getStartTime() {
        return startTime.getAttribute("value");
    }

    public String getEndTime() {
        return endTime.getAttribute("value");
    }


}
