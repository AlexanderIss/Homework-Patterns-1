import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldCardDeliveryTest() {
        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        $("[placeholder='Город']").setValue(validUser.getCity());
        $("[name='name']").setValue(validUser.getName());
        int daysFirstMeeting = 5;
        String firstMeetingDate = DataGenerator.generateDate(daysFirstMeeting);
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[name='phone']").setValue(validUser.getPhone());
        $(".checkbox__box").click();
        $(".button__content").click();
        $(".notification__content")
                .shouldBe((Condition.visible), Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));

        int daysSecondMeeting = 10;
        String secondMeetingDate = DataGenerator.generateDate(daysSecondMeeting);
        $("[placeholder='Дата встречи']").doubleClick();
        $("[placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        $(".button__content").click();
        $("[data-test-id='replan-notification'] button").click();
        $(".notification__content")
                .shouldBe((Condition.visible), Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
