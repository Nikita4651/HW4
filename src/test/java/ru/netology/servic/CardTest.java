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
    public String generateDate(int day, String pattern) {
        return LocalDate.now()
                .plusDays(day)
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
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.text(" Встреча успешно забронирована на " + planningDate));

    }

    @Test
    void cardWithDeliveryTest1() {

        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Ко");
        $$("div.popup__content div").find(exactText("Кострома")).click();
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").click();
        if (!generateDate(3, "MM").equals(generateDate(10, "MM"))) {
            $("[data-step='1']").doubleClick();
        }
        $$(".calendar__day")
                .find(Condition.text(generateDate(7, "d")))
                .click();

        $("[data-test-id='name'] input").setValue("Осепчук Никита");
        $("[data-test-id='phone'] input").setValue("+79110126430");
        $("[data-test-id='agreement']").click();
        $(Selectors.withText("Забронировать")).click();
        $("div.notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на "));


    }
}

