package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;


public class MealTestData {

    public final static int MEAL_ID = START_SEQ;

    public final static List<Meal> MEALS = Arrays.asList(
            new Meal(MEAL_ID, LocalDateTime.parse("2017-10-19T10:23:54"), "breakfast", 500),
            new Meal(MEAL_ID + 1, LocalDateTime.parse("2017-04-10T20:23:00"), "lunch", 490)
    );

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>(
            (expected, actual) -> expected == actual ||
                    (Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDateTime(), actual.getDateTime())
                            && Objects.equals(expected.getDescription(), actual.getDescription())
                            && Objects.equals(expected.getCalories(), actual.getCalories())
                    )
    );

}
