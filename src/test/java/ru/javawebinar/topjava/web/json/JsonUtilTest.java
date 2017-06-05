package ru.javawebinar.topjava.web.json;

import org.junit.Test;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import static ru.javawebinar.topjava.util.MealsUtil.*;

import java.util.List;

public class JsonUtilTest {

    @Test
    public void testReadWriteValue() throws Exception {
        String json = JsonUtil.writeValue(MealTestData.ADMIN_MEAL1);
        System.out.println(json);
        Meal meal = JsonUtil.readValue(json, Meal.class);
        MealTestData.MATCHER.assertEquals(MealTestData.ADMIN_MEAL1, meal);
    }

    @Test
    public void testReadWriteValues() throws Exception {
        String json = JsonUtil.writeValue(MealTestData.MEALS);
        System.out.println(json);
        List<Meal> meals = JsonUtil.readValues(json, Meal.class);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, meals);
    }

    @Test
    public void testReadWriteMealsWithExceed() throws Exception {
        String json = JsonUtil.writeValue(getWithExceeded(MealTestData.MEALS, DEFAULT_CALORIES_PER_DAY));
        System.out.println(json);
        List<MealWithExceed> meals = JsonUtil.readValues(json, MealWithExceed.class);
        MealTestData.MATCHER_WITH_EXCEED.assertCollectionEquals(getWithExceeded(MealTestData.MEALS, DEFAULT_CALORIES_PER_DAY), meals);
    }
}