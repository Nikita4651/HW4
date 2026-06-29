package ru.netology.servic;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;



class CardTest {
    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void cardWithDeliveryTest() {
        String planningDate = generateDate(4, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Кострома");

        $("[data-test-id='date'] input")

                //.shouldBe(Condition.visible)
                //.shouldBe(Condition.enabled).setValue(planningDate)
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);

        $("[data-test-id='name'] input").setValue("Осепчук Никита");
        $("[data-test-id='phone'] input").setValue("+79110126430");
        $("[data-test-id='agreement']").click();
        //$("button").click();
       $(Selectors.withText("Забронировать")).click();
        $("div[data-test-id='notification']")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на"));

       // $(Selectors.withText("Встреча успешно забронирована на"))
        //        .should(Condition.visible, Duration.ofSeconds(15))
       //         .should(Condition.visible);
       // String expectedFragment = planningDate;
       // String actualText = $(Selectors.withText("Встреча успешно забронирована на")).getText();


    }
}
