import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
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


    private String generateDate( int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    int dayOfMonth = getDayOfMonth();
    private int getDayOfMonth() {
        return dayOfMonth;
    }


    @Test
    void shouldBookCardDelivery() {
        open("http://localhost:9999");
        $("span[data-test-id='city'] input").setValue("Вл");
        $$("div.popup__content div").find(exactText("Владивосток")).click();
        $("span[data-test-id='date'] button").click();
        String currentDate = generateDate(3, "MM.yyyy"); // дата заявки (=сегодня+3 дня)
        String bookingDate = generateDate(7,"MM.yyyy"); // дата бронирования встречи (=сегодня+7 дней)
        if (!currentDate.equals(bookingDate)) {// сравниваем даты
            $("[data-step='1']").click();// перелистнуть календарь, если даты не равны
        }
        $$("table.calendar__layout td").find (text (String.valueOf(dayOfMonth))).click();
        $("[data-test-id='name'] input").setValue("Кузьма Кузьмичев");
        $("[data-test-id='phone'] input").setValue("+79658888111");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("div.notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + LocalDate.now()
                        .plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
    }


}

