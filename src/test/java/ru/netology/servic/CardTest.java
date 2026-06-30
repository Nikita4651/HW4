package ru.netology.servic;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;


import com.codeborne.selenide.Selenide;
;
import org.junit.jupiter.api.Test;


import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
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
                .shouldHave(text("Встреча успешно забронирована на"));


    }


    @Test
    void shouldRegisterCardDelivery() {


        LocalDate target = LocalDate.now().plusWeeks(1);
        String dayText = String.format("%01d", target.getDayOfMonth());
        int monthsToScroll = 0;


        System.out.println("DEBUG: dayText = '" + dayText + "' (length=" + dayText.length() + ")");
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ко");
        $$("div.popup__content div").find(exactText("Кострома")).click();
        // $("[data-test-id='date'] button").click();
        // Открытие календаря (только один клик по кнопке)
        $("[data-test-id='date'] button").click();

        for (int i = 0; i < monthsToScroll; i++) {
            var arrows = $$("div.calendar__arrow.calendar__arrow_direction_right");
            if (arrows.isEmpty()) {
                throw new IllegalStateException("Не найдены стрелки календаря. Проверь селектор.");
            }

            arrows.get(arrows.size() - 1).click();
            Selenide.sleep(500);
        }


        $$("table.calendar__layout td")
                .find(Condition.text(dayText))
                .click();

        /*$("span[data-test-id='date'] button span.input__box [placeholder='Дата встречи']")
                .doubleClick().sendKeys(planningDate);*/
        $("[data-test-id='name'] input").setValue("Осепчук Никита");
        $("[data-test-id='phone'] input").setValue("+79110126430");
        $("[data-test-id='agreement']").click();

        $(Selectors.withText("Забронировать")).click();
        $("div[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована на"));

    }

    @Test
    void shouldRegisterCardDeliveryDay() {


        LocalDate target = LocalDate.now().plusDays(15);
        String dayText = String.format("%02d", target.getDayOfMonth());
        int monthOffSet = 3;


        System.out.println("DEBUG: dayText = '" + dayText + "' (length=" + dayText.length() + ")");
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ко");
        $$("div.popup__content div").find(exactText("Кострома")).click();

        $("[data-test-id='date'] button").click();

        for (int i = 0; i < monthOffSet; i++) {
            var arrows = $$("div.calendar__arrow.calendar__arrow_direction_right");
            if (arrows.isEmpty()) {
                throw new IllegalStateException(" ");
            }

            arrows.get(arrows.size() - 1).click();
            Selenide.sleep(500);
        }


        $$("table.calendar__layout td")
                .find(Condition.text(dayText))
                .click();


        $("[data-test-id='name'] input").setValue("Осепчук Никита");
        $("[data-test-id='phone'] input").setValue("+79110126430");
        $("[data-test-id='agreement']").click();

        $(Selectors.withText("Забронировать")).click();
        $("div[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(text("Встреча успешно забронирована на"));

    }
}
