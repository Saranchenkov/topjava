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
import java.util.Enumeration;

/**
 * Created by Ivan on 27.03.2017.
 */
public class MealServlet extends HttpServlet {
    private final static Meal_CRUD CRUD = new Meal_CRUD();
    static {
        CRUD.create(new Meal(Meal_CRUD.increaseByOneAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        CRUD.create(new Meal(Meal_CRUD.increaseByOneAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        CRUD.create(new Meal(Meal_CRUD.increaseByOneAndGet(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        CRUD.create(new Meal(Meal_CRUD.increaseByOneAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        CRUD.create(new Meal(Meal_CRUD.increaseByOneAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        CRUD.create(new Meal(Meal_CRUD.increaseByOneAndGet(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("redirect to meals");
        String action = null;
        Enumeration<String> params = req.getParameterNames();
        while (params.hasMoreElements()){
            if("action".equalsIgnoreCase(params.nextElement())) {
                action = req.getParameter("action");
                break;
            }
        }
        String forward = "";
        req.setCharacterEncoding("UTF-8");

        if (action == null){
            forward = "meals.jsp";
            req.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(CRUD.findAll(), LocalTime.MIN, LocalTime.MAX, 2000));

        } else if ("delete".equalsIgnoreCase(action)){
            int id = Integer.valueOf(req.getParameter("mealId"));
            LOG.debug("REMOVING OF {} ...", CRUD.read(id).toString());
            CRUD.delete(id);
            LOG.debug("REMOVING WAS SUCCESSFUL!");
            forward = "meals.jsp";
            req.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(CRUD.findAll(), LocalTime.MIN, LocalTime.MAX, 2000));

        } else if("update".equalsIgnoreCase(action) || "new".equalsIgnoreCase(action)){
            forward = "mealPage.jsp";
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                Meal_CRUD.increaseByOneAndGet(),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));

        String refererURL = req.getHeader("referer");

        if (refererURL.contains("update")) {
            int id = Integer.valueOf(refererURL.substring(refererURL.lastIndexOf("=") + 1));
            LOG.debug("{} IS PREPARING TO UPDATE...", CRUD.read(id));
            CRUD.update(id, meal);
            LOG.debug("Updating was successfuly");

        } else if (refererURL.contains("new")){
            LOG.debug("CREATING NEW MEAL ...");
            CRUD.create(meal);
            LOG.debug("{} WAS CREATED SUCCESSFULY", meal);
        }
        req.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(CRUD.findAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
