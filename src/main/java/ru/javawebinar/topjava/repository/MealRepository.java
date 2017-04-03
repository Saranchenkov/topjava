package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {

    Meal save(Meal meal);

    // false if not found
    boolean delete(int mealId);

    // null if not found
    Meal get(int mealId);

    Collection<Meal> getAll();
}
