package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.crud.Meal_CRUD;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

/**
 * Created by Ivan on 27.03.2017.
 */
public class MealServlet extends HttpServlet {
    private final static Meal_CRUD CRUD = new Meal_CRUD();
    static {
        CRUD.save(new Meal(Meal_CRUD.getNewID(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        CRUD.save(new Meal(Meal_CRUD.getNewID(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        CRUD.save(new Meal(Meal_CRUD.getNewID(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        CRUD.save(new Meal(Meal_CRUD.getNewID(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        CRUD.save(new Meal(Meal_CRUD.getNewID(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        CRUD.save(new Meal(Meal_CRUD.getNewID(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        switch (action == null ? "all" : action){
            case "delete" :
                int id = Integer.valueOf(req.getParameter("mealId"));
                LOG.debug("REMOVING OF {} ...", CRUD.read(id).toString());
                CRUD.delete(id);
                LOG.debug("REMOVING WAS SUCCESSFUL!");
                resp.sendRedirect("meals");
                break;
            case "update" :
            case "new" :
                final Meal meal = "update".equalsIgnoreCase(action) ?
                        CRUD.read(Integer.valueOf(req.getParameter("mealId"))) :
                        new Meal(Meal_CRUD.getNewID(), LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 2000);
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/mealPage.jsp").forward(req, resp);
                break;
            case "all":
                default:
                    req.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(CRUD.findAll(), LocalTime.MIN, LocalTime.MAX, 2000));
                    req.getRequestDispatcher("meals.jsp").forward(req, resp);
                    break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id = Integer.valueOf(req.getParameter("id"));
        boolean isOld = Meal_CRUD.getMealMap().containsKey(id);
        final Meal meal = new Meal(id,
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));
        LOG.debug("PREPARING TO {} MEAL WITH ID = {}", isOld ? "UPDATE" : "CREATE", meal.getId());
        CRUD.save(meal);
        LOG.debug("OPERATION WAS SUCCESSFULY");

        resp.sendRedirect("meals");
    }
}
