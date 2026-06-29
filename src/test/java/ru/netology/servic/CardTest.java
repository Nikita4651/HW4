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
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.remote.tracing.EventAttribute.setValue;


class CardTest {
    public String generateDate(int days, String pattern){
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void cardWithDeliveryTest() {
        String planningDate = generateDate(3, "dd.MM.yyyy");
        Selenide.open("http://localhost:9999");
        SelenideElement city = $("[data-test-id='city']");
        Selenide.actions()
                .click(city)
                .sendKeys(Keys.END)          // опционально
                .sendKeys("Кострома")
                .perform();
        /*SelenideElement form = $("form").find(Condition.visible);*/

        $("[type='tel']")

                //.shouldBe(Condition.visible)
                //.shouldBe(Condition.enabled).setValue(planningDate)
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue("02.07.2026");
        $("[name='name']").setValue("Осепчук Никита");
        $("[name='phone']").setValue("+79110126430");
        $("[data-test-id='agreement']").click();
        //$("button").click();
        $(Selectors.withText("Забронировать")).click();

        $(Selectors.withText("Встреча успешно забронирована на"))
                .should(Condition.visible, Duration.ofSeconds(15))
                .should(Condition.visible);
        String expectedFragment = planningDate;
        String actualText = $(Selectors.withText("Встреча успешно забронирована на"))
                .getText();


    }
}
