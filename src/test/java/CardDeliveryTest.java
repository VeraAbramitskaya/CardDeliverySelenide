import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
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

public class CardDeliveryTest {
    private String generateDate( int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

        @Test
        void shouldBookSuccessfully() {
            Configuration.holdBrowserOpen = true;
            open("http://localhost:9999");
            $("[data-test-id='city'] input").setValue("Владивосток");
            String currentDate = generateDate(5, "dd.MM.yyyy");
            $("[data-test-id='date'] input]").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
            $("[data-test-id='date'] input]").sendKeys(currentDate);
            $("[data-test-id='name'] input]").sendKeys("Гаврилов Гаврила");
            $("[data-test-id='phone'] input]").sendKeys("+79638555741");
            $("[data-=test-id ='agreement']").click();
            $("[data-=test-id ='button.button']").click();
            $("notification__content")
                    .shouldBe(Condition.visible, Duration.ofSeconds(15))
                    .shouldHave(Condition.exactText("Встреча успешно забронирована на " + currentDate));
        }


}




