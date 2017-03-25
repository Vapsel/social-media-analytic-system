package com.snas.sample;

/**
 * Created by Vadym on 3/20/2017.
 */
public class Work {
    private final String name ;
    private final int recommendedVisitDuration ;
    public Work ( String name , int recommendedVisitDuration ) {
        this . name = name ;
        this . recommendedVisitDuration = recommendedVisitDuration ;
    }
    @Override
    public String toString () {
        return name +": "+ recommendedVisitDuration ;
    }
    public String getName () {
        return name ;
    }
    public int getRecommendedVisitDuration () {
        return recommendedVisitDuration ;
    }
}
