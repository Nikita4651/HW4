package ru.netology.servic;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;


import org.junit.jupiter.api.Test;


import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;


class CardTest {
    public String generateDate(int days, String pattern) {
        return LocalDate.now()
                .plusDays(days)
                .format(DateTimeFormatter.ofPattern(pattern));


    }

    public String generateDateMonthYear(int days, int month, int year, String pattern) {
        return LocalDate.now()
                .plusDays(days)
                .plusMonths(month)
                .plusYears(year)
                .format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void cardWithDeliveryTest() {

        String planningDate = generateDate(4, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кострома");

        $("[data-test-id='date'] input")

                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);

        $("[data-test-id='name'] input").setValue("Осепчук Никита");
        $("[data-test-id='phone'] input").setValue("+79110126430");
        $("[data-test-id='agreement']").click();

        $(Selectors.withText("Забронировать")).click();
        $("div[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на"));


    }

    @Test
    void shouldRegisterCardDelivery() {


        String planningDate = generateDateMonthYear(9, 7, 4, "dd.MM.yyyy");
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ко");
        $$("div.popup__content div").find(exactText("Кострома")).click();
        $("[data-test-id='date'] button").click();

        $("[data-test-id='date'] span.input__box [placeholder='Дата встречи']")
                .doubleClick().sendKeys(planningDate);
        $("[data-test-id='name'] input").setValue("Осепчук Никита");
        $("[data-test-id='phone'] input").setValue("+79110126430");
        $("[data-test-id='agreement']").click();

        $(Selectors.withText("Забронировать")).click();
        $("div[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на"));

    }
}
