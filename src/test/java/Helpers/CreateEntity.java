package Helpers;

import entities.Festival;
import entities.Guest;
import entities.Show;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class CreateEntity {
    public Festival createTestFestival() {
        return new Festival(
                "TestFestival",
                "TestCity",
                createTestDate1(),
                createTestDate2()
        );
    }

    public Guest createTestGuest() {
        return new Guest(
                "TestGuest",
                "123456789",
                "test@guest.com",
                "Attending"
        );
    }

    public Show createTestShow() {
        return new Show(
                "TestShow",
                "TestLocation",
                createTestDate1(),
                createTestDate2()
        );
    }

    public LocalDateTime createTestDate1() {
        return LocalDateTime.of(2022, 7, 1, 14, 30);
    }

    public LocalDateTime createTestDate2() {
        return LocalDateTime.of(2022, 7, 2, 12, 15);
    }
}
