import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class ComplexElementsTest {
    class AppCardDelivery {
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        String date;
        String month;
        String day;

        @BeforeEach
        void setUp() {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 31);
            date =  new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());
            month = monthNames[calendar.get(Calendar.MONTH)] + " " + calendar.get(Calendar.YEAR);
            day = Integer.toString(calendar.get(Calendar.DATE));
            open("http://localhost:9999");
        }

        @Test
        void shouldBookCardDelivery() {
            $("span[data-test-id='city'] input").setValue("Вл");
            $$("div.popup__content div").find(exactText("Владивосток")).click();
            $("span[data-test-id='date'] button").click();

            while (!$("div.calendar__name").getText().equals(month)) {
                $$("div.calendar__arrow.calendar__arrow_direction_right").get(1).click();
            }

            $$("table.calendar__layout td").find(text(day)).click();
            $("[data-test-id='name'] input").setValue("Кузьма Кузьмичев");
            $("[data-test-id='phone'] input").setValue("+79658888111");
            $("[data-test-id='agreement']").click();
            $$("button").find(exactText("Забронировать")).click();
            $("div.notification__content")
                    .shouldBe(Condition.visible, Duration.ofSeconds(15))
                    .shouldHave(Condition.exactText("Встреча успешно забронирована на " + date));
        }

    }

}
