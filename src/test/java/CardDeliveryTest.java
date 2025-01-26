import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardDeliveryTest {

    public String generateDate(int days, String pattern) {
       return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999");
    }

    @Test
    void shouldReturnSuccessForm() {
        $("[data-test-id='city'] input").setValue("Москва");
        String date = generateDate(5,"dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT,Keys.HOME),
                Keys.BACK_SPACE).setValue(date);
        $("[data-test-id='name'] input").setValue("Захаров Даниил");
        $("[data-test-id='phone'] input").setValue("+79154230549");
        $("[data-test-id='agreement']").click();
        $$("button").find(Condition.exactText("Забронировать")).click();
        $(By.cssSelector(".notification__content"))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date), Duration.ofSeconds(15))
                .should(Condition.visible);
    }
}
