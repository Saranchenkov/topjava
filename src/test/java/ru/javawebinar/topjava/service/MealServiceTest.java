package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.MealTestData.MATCHER;
import static ru.javawebinar.topjava.MealTestData.MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by Ivan on 11.04.2017.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    private List<Meal> MEALS;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
        MEALS = new ArrayList<>(MealTestData.MEALS);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID, USER_ID);
        Assert.assertEquals(MEALS.get(0), meal);
    }

    @Test(expected = NotFoundException.class)
    public void getFromAnotherUser(){
        service.get(MEAL_ID, USER_ID + 2);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        MEALS.remove(0);
        MATCHER.assertCollectionEquals(service.getAll(USER_ID), MEALS);
    }

    @Test(expected = NotFoundException.class)
    public void deleteFromAnotherUser(){
        service.delete(MEAL_ID, USER_ID + 2);
    }

    @Test
    public void getBetweenDates() throws Exception {
        LocalDate start = LocalDate.parse("2017-10-10");
        LocalDate end = LocalDate.parse("2017-10-20");
        List<Meal> result = MEALS.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .filter(um -> DateTimeUtil.isBetween(um.getDate(), start, end))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(service.getBetweenDates(start, end, USER_ID), result);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        LocalDateTime start = LocalDateTime.parse("2017-10-10T10:23:54");
        LocalDateTime end = LocalDateTime.parse("2017-10-20T10:23:54");
        List<Meal> result = MEALS.stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .filter(um -> DateTimeUtil.isBetween(um.getDateTime(), start, end))
                .collect(Collectors.toList());
        MATCHER.assertCollectionEquals(service.getBetweenDateTimes(start, end, USER_ID), result);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(MEALS, service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEALS.get(0));
        updated.setCalories(777);
        updated.setDescription("Updated meal");
        Assert.assertEquals(updated, service.update(updated, USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateFromAnotherUser(){
        Meal updated = new Meal(MEALS.get(0));
        updated.setCalories(777);
        updated.setDescription("Updated meal");
        service.update(updated, USER_ID + 2);
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.parse("2007-11-23T10:13:54"), "Ужин", 790);
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MEALS.add(newMeal);
        MATCHER.assertCollectionEquals(MEALS, service.getAll(USER_ID));
    }
}