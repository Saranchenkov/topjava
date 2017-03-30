package ru.javawebinar.topjava.crud;

import ru.javawebinar.topjava.model.Meal;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Ivan on 28.03.2017.
 */
public class Meal_CRUD implements CRUD_Interface<Meal>{

    private static AtomicInteger id = new AtomicInteger();
    private static ConcurrentNavigableMap<Integer, Meal> mealMap = new ConcurrentSkipListMap<>();

    @Override
    public void create(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public Meal read(int id) {
        return mealMap.get(id);
    }

    @Override
    public void update(int id, Meal meal) {
        try {
           Field f = Meal.class.getDeclaredField("id");
           f.setAccessible(true);
           f.setInt(meal, id);
        } catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
        mealMap.put(id, meal);

    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public List<Meal> findAll() {
        return mealMap.values().stream().collect(Collectors.toList());

    }

    public static int increaseByOneAndGet(){
        return id.incrementAndGet();
    }

    public static int getId(){
        return id.get();
    }
}
