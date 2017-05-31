package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by Ivan on 31.05.2017.
 */
@Controller
public class MealController {

    @Autowired
    MealService service;

    @RequestMapping("/meals")
    public String all(Model model){
        model.addAttribute("meals", MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }

    @RequestMapping(value = "/meals/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model){
        model.addAttribute("meal", service.get(id, AuthorizedUser.id()));
        return "meal";
    }

    @RequestMapping(value = "/meals/update/{id}", method = RequestMethod.POST)
    public String update(Meal meal){
        System.out.println("\n\n" + meal + "\n\n\n");
        service.update(meal, AuthorizedUser.id());
        return "meals";
    }

    @RequestMapping(value = "/meals/create", method = RequestMethod.GET)
    public String create(Model model){
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals/create", method = RequestMethod.POST)
    public String create(Meal meal){
        service.save(meal, AuthorizedUser.id());
        return "meal";
    }

    @RequestMapping(value = "/meals/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id){
        service.delete(id, AuthorizedUser.id());
        return "redirect:/meals";
    }


    @RequestMapping(value = "/meals/filter", method = RequestMethod.POST)
    public String filter(@PathVariable ModelMap map){
        LocalDate startDate = DateTimeUtil.parseLocalDate((String)map.get("startDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime((String)map.get("startTime"));
        LocalDateTime start = LocalDateTime.of(startDate, startTime);

        LocalDate endDate = DateTimeUtil.parseLocalDate((String)map.get("endDate"));
        LocalTime endTime = DateTimeUtil.parseLocalTime((String)map.get("endTime"));
        LocalDateTime end = LocalDateTime.of(endDate, endTime);

        map.addAttribute("meals", MealsUtil.getWithExceeded(service.getBetweenDateTimes(start, end, AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay()));
        return "meals";
    }
}
