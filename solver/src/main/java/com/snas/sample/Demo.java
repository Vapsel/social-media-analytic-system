package com.snas.sample;

import org.sat4j .pb. IPBSolver ;
import org.sat4j .pb. OptToPBSATAdapter ;
import org.sat4j .pb. PseudoOptDecorator ;
import org. sat4j .pb. SolverFactory ;

import org.sat4j .pb. tools . DependencyHelper ;
import org.sat4j .pb. tools . WeightedObject ;
import org.sat4j . specs . ContradictionException ;
import org.sat4j . specs . TimeoutException ;
public class Demo {
    public static void main ( String [] args ) {
        int [] utilities = {100 , 500 , 10000 , 700 , 800};
        int [] durations = {5 , 10 , 100 , 15 , 10};
        String [] names = {" Work1 ", " Work2 ", " Work3 ", " Work4 ", " Work5 "};
        Work [] works = new Work [ names . length ];

        for (int i = 0; i < names . length ; i++) {
            works [i] = new Work ( names [i] , durations [i]);
        }
        IPBSolver solver = new OptToPBSATAdapter (new PseudoOptDecorator ( SolverFactory . newDefault ()));
        DependencyHelper h = new DependencyHelper(solver);
        WeightedObject [] wWorks = new WeightedObject [ works . length ];

        Work[] woeuvres = new Work[works.length];
        for (int i = 0; i < works . length ; i++) {
            final Work w = works [i];
            final int duration = w. getRecommendedVisitDuration ();
//            woeuvres [i] = WeightedObject . newWO (w, duration );
        }
        try {
            h.atMost ("C1", 20 , wWorks );
            for (int i = 0; i < works . length ; i++) {
                h. addToObjectiveFunction ( works [i] , -utilities [i]);
            }
            if (h. hasASolution ()) {
                System .out . println (" Optimal solution :"+h. getSolution ());
            } else {
                System .out . println ("No solution !");
            }
        } catch ( ContradictionException e) {
            System . out. println (" Inconsistent problem !");
        } catch ( TimeoutException e) {
            System . out. println (" Timeout !");
        }
    }
}
