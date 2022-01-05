package com.chickenfarms.chickenfarms.utils;

public class PageLimitUtils {
    
    private final static int PAGE_RECORDS_LIMIT=5;
    
    public  static int getStartPageLimit(int pageNumber){
        return PAGE_RECORDS_LIMIT*pageNumber-PAGE_RECORDS_LIMIT;
    }
    
    public static int getEndPageLimit(int pageNumber){
        return PAGE_RECORDS_LIMIT*pageNumber;
    }
}
