package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal findByIdAndUser_Id(int id, int userId);

    int deleteByIdAndUser_Id(int id, int userId);

    List<Meal> findAllByUser_IdEqualsOrderByDateTimeDesc(int id);

    List<Meal> findAllByUser_IdEqualsAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);
}
