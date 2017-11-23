package smas.solver;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.IVecInt;
import org.sat4j.core.VecInt;
import org.sat4j.opt.MaxSatDecorator;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class MaxSatSolver {

    public int iloscDopasowan( int preferencjeUzytkownika[], int tagiOferty[]) {
        try {

            MaxSatDecorator maxsat = new MaxSatDecorator(SolverFactory.newLight());

            Arrays.sort(preferencjeUzytkownika);
            Arrays.sort(tagiOferty);

            int prefLen = preferencjeUzytkownika.length;
            int tagiLen = tagiOferty.length;

            maxsat.newVar(Math.max(preferencjeUzytkownika[prefLen-1], tagiOferty[tagiLen-1]));
            System.out.println("max: " + Math.max(preferencjeUzytkownika[prefLen-1], tagiOferty[tagiLen-1]));
            IVecInt literals = new VecInt();

            for( int i: tagiOferty) {
                literals.push(i);
                maxsat.addClause(literals);
                literals.clear();
            }

            VecInt nieWiemDoCzegoToAleBezTegoNieDziala = new VecInt();
            for(int i: preferencjeUzytkownika) {
                nieWiemDoCzegoToAleBezTegoNieDziala.push(-i);
            }

            maxsat.admitABetterSolution(nieWiemDoCzegoToAleBezTegoNieDziala);

            if (maxsat.isSatisfiable()) {
                System.out.println("jest");
                System.out.println(maxsat.getObjectiveValue());
            } else {
                System.out.print("nie ma");
            }

            return maxsat.getObjectiveValue().intValue();

        } catch (Exception e) {
            System.out.println("blad");
            System.out.print(e.getMessage());
        }



        return -1;
    }

    public static void main(String[] args) {
        int preferencje[] = {1,2,3,4,5,6,7,8,9,11,12,13,14};
        int tagi[] = {1,2,5,8,14,2200};
//        System.out.println(iloscDopasowan(preferencje,tagi));
    }
}
