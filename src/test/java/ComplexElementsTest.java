import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.temporal.ChronoField.*;

public class ComplexElementsTest {


    @BeforeEach

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }


    @Test
    void shouldBookCardDelivery() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Вл");
        $$("div.popup__content div").find(exactText("Владивосток")).click();
        $("span[data-test-id='date'] button").click();
        while ($("div.calendar__name").getText().equals(MONTH_OF_YEAR)) {
            $$("div.calendar__arrow.calendar__arrow_direction_right").get(1).click();
        }

        $$("table.calendar__layout td").find(text(DAY_OF_MONTH.name())).click();
        $("[data-test-id='name'] input").setValue("Кузьма Кузьмичев");
        $("[data-test-id='phone'] input").setValue("+79658888111");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("div.notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + LocalDate.now()
                        .plusDays(7).format(DateTimeFormatter.ofPattern(dd.mm.yyyy)));
    }

}

