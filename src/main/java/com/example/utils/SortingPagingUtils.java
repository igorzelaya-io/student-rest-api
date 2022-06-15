package com.example.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils for Paging and Sorting.
 * @author Igor A. Zelaya
 */
public class SortingPagingUtils {

    public static SortingPagingUtils create(){
        return new SortingPagingUtils();
    }

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
                sortingOrders.add(new Sort.Order(getSortDirection(_sort[1].trim()), _sort[0].trim()));
            }
        }
        else{
            sortingOrders.add(new Sort.Order(getSortDirection(sort[1].trim()), sort[0].trim()));
        }
        return sortingOrders;
    }

    /**
     * Return sortDirection given string.
     * @param sortDirection String
     * @return Direction
     */
    private Sort.Direction getSortDirection(final String sortDirection){
        if(sortDirection.equals("desc")){
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }

}
