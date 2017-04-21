package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()){
            User user = em.getReference(User.class, userId);
            meal.setUser(user);
            em.persist(meal);
            return meal;
        } else {
            int userIdBeforeUpdate = em.createNamedQuery(Meal.GET_USER_ID, Integer.class)
                    .setParameter("id", meal.getId())
                    .getSingleResult();
            if (userIdBeforeUpdate == userId) {
                User user = em.getReference(User.class, userId);
                meal.setUser(user);
                return em.merge(meal);
            } else return null;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
       return em.createNamedQuery(Meal.DELETE)
               .setParameter("id", id)
               .setParameter("user_id", userId)
               .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> list = em.createNamedQuery(Meal.GET_BY_ID, Meal.class)
                .setParameter("id", id)
                .setParameter("user_id", userId)
                .getResultList();
        return DataAccessUtils.singleResult(list);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("user_id", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.ALL_BETWEEN_DATETIMES, Meal.class)
                .setParameter("user_id", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }
}