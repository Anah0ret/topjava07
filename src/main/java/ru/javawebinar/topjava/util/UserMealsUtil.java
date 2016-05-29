package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredMealsWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredMealsWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        List<UserMealWithExceed> userMealWithExceedList=new ArrayList<>();
        HashMap<LocalDate,Boolean> dateExceededMap=new HashMap<>();
        boolean exceed=false;
        if(mealList!=null){
            for(UserMeal userMeal: mealList){
                LocalDate localDate=userMeal.getDateTime().toLocalDate();
                if(dateExceededMap.containsKey(localDate)) exceed=dateExceededMap.get(localDate);
                else{
                    exceed=(mealList.stream().filter(date -> date.getDateTime().toLocalDate().isEqual(localDate)).mapToInt(UserMeal::getCalories).sum()>caloriesPerDay);
                    dateExceededMap.put(localDate,exceed);
                }
                if(userMeal.getDateTime().toLocalTime().isAfter(startTime)&&userMeal.getDateTime().toLocalTime().isBefore(endTime))
                    userMealWithExceedList.add(new UserMealWithExceed(userMeal.getDateTime(),userMeal.getDescription(),userMeal.getCalories(),exceed));
            }
        }
        return userMealWithExceedList;
    }
}
