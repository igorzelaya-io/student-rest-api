package com.example.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils for Paging and Sorting.
 * @author Igor A. Zelaya
 */
@Component
public class SortingPagingUtils {

    /**
     * Return a List of Order to sort by.
     * @param sort String[] sort
     * @return List Order
     */
    public List<Sort.Order> getSortOrders(String[] sort){
        List<Sort.Order> sortingOrders = new ArrayList<>();
        //Evaluate if we should sort by more than one field.
        if(sort[0].contains(",")){
            //sortOrder = "field, direction"
            for(String sortOrder: sort){
                String[] _sort = sortOrder.split(",");
                sortingOrders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        }
        else{
            sortingOrders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return sortingOrders;
    }

    /**
     * Return sortDirection given string.
     * @param sortDirection String
     * @return Direction
     */
    private Sort.Direction getSortDirection(String sortDirection){
        if(sortDirection.equals("desc")){
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
