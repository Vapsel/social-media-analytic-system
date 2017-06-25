package test;

import aima.core.logic.propositional.inference.WalkSAT;
import aima.core.logic.propositional.kb.KnowledgeBase;
import aima.core.logic.propositional.kb.data.Model;
import aima.core.logic.propositional.parsing.PLParser;
import aima.core.logic.propositional.parsing.ast.PropositionSymbol;
import aima.core.logic.propositional.parsing.ast.Sentence;
import aima.core.logic.propositional.visitors.ConvertToCNF;
import aima.core.logic.propositional.visitors.ConvertToConjunctionOfClauses;
import org.junit.Assert;
import org.junit.Test;

public class ConvertToCNFTest {
    private PLParser parser = new PLParser();

    @Test
    public void myTest(){
        Sentence nested = parser.parse("(A1 | A2 | A3) => (A1 & A4)");
        Sentence transformed = ConvertToCNF.convert(nested);
        System.out.println(transformed);

    }

    @Test
    public void testOfferUnsatUserPreferences(){
        KnowledgeBase kb = new KnowledgeBase();
        kb.tell("wspinaczka & gory & las & jezioro & ~morze & gory & morze");
        WalkSAT walkSAT = new WalkSAT();
        Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence())
                .getClauses(), 0.5, 1000);
        Assert.assertNull(m);
    }

    @Test
    public void multiOfferTest(){
//        KnowledgeBase kb = new KnowledgeBase();
//        kb.tell("((wspinaczka & gory & las & jezioro & ~morze & offer1) " +
//                "| (pływanie & gory & ~las & ~jezioro & morze & rower & offer2))" +
//                "& gory & morze" + // User preferences
//                "& ((offer1 & ~offer2) | (~offer1 & offer2))");  // Only one offer can satisfy the formula. Not our target!
//        WalkSAT walkSAT = new WalkSAT();
//        Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence())
//                .getClauses(), 0.5, 1000);
//        Assert.assertNotNull(m);
//        Assert.assertTrue(m.getValue(new PropositionSymbol("offer2")));
//        Assert.assertFalse(m.getValue(new PropositionSymbol("offer1")));
//        Assert.assertTrue(m.getValue(new PropositionSymbol("gory")));
//        Assert.assertTrue(m.getValue(new PropositionSymbol("morze")));
//        m.print();
    }

    @Test
    public void testOfferSatUserPreferences(){
        KnowledgeBase kb = new KnowledgeBase();
        kb.tell("wspinaczka & gory & las & jezioro & ~morze & gory");
        WalkSAT walkSAT = new WalkSAT();
        Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence())
                .getClauses(), 0.5, 1000);
        Assert.assertNotNull(m);
        Assert.assertTrue(m.getValue(new PropositionSymbol("gory")));
        m.print();

    }

    @Test
    public void testImplicationSATProblemOfAIMALibrary(){
//        KnowledgeBase kb = new KnowledgeBase();
//        kb.tell("(A1 | A2 | A3) => (A1 & A4)");
//        WalkSAT walkSAT = new WalkSAT();
//        Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence())
//                .getClauses(), 0.5, 1000);
//        Assert.assertNotNull(m);
//        Assert.assertTrue(m.getValue(new PropositionSymbol("A1")));
//        Assert.assertTrue(m.getValue(new PropositionSymbol("A4")));
//        m.print();
    }

    // Set proper encoding for polish characters
    @Test
    public void testPolishCharackters(){
//        KnowledgeBase kb = new KnowledgeBase();
//        kb.tell("(gory | las | jerziro) & (gory | morze)");
//        // Term should start with letter
//        WalkSAT walkSAT = new WalkSAT();
//        Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence())
//                .getClauses(), 0.5, 1000);
//        Assert.assertNotNull(m);
//        m.print();
    }

    @Test
    public void testForDocumentation(){
        KnowledgeBase kb = new KnowledgeBase();
        kb.tell("(wspinaczka) & (gory | las | jezioro) & (schronisko)&\n" +
                "(Zakopane) & (samochod | pieszo)&\n" +
                "(lato | jesien | zima | wiosna) & (camping | widoki) " +
                "=>" +
                " (rower | bieg) & (kawa) & (gory | morze)&\n" +
                "(Krakow | Katowice) & (pieszo)&\n" +
                "(pasja | kariera | satysfakcja) & (zima)");
        WalkSAT walkSAT = new WalkSAT();
        Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence())
                .getClauses(), 0.5, 1000);
        Assert.assertNotNull(m);
        m.print();
    }

    @Test
    public void testForNewConceptDocumentation(){
//        KnowledgeBase kb = new KnowledgeBase();
//        kb.tell("~rower & ~piłka & ~narty & ~koszykówka & ~siatkówka & ~boks " +
//                "& ~bieg & ~hokej & ~pływanie & ~kajaki & wspinaczka" +
//                "& ~kawa & ~herbata & ~sok & ~piwo & ~wino & ~woda & ~koktajl" +
//                "& ~chleb & ~jabłko & ~banan & ~wołowina & ~wieprzowina & ~cielęcina & ~boczek & ~barszcz " +
//                "& ~kaszanka & ~frytki & ~ciasteczka & ~kaczka & ~ryby & ~wegetarianskie" +
//                "& ~morze & góry & las & ~rzeka & jezioro" +
//                "& ~mieszkanie & ~hotel & ~hostel & ~apartamenty & schronisko" +
//                "& ~kawiarnia & ~restauracja & ~bar & ~pub & ~fastfood & ~karczma" +
//                "& ~samolot & samochód & ~rower & ~autobus & ~helikopter & ~motor & ~skuter & ~statek & pieszo" +
//                "& ~kino & ~bowling & ~bilard & ~snooker & ~teatr & ~taniec & ~muzeum & ~zoo" +
//                "& lato & jesien & zima & wiosna " +
//                "& camping & ~nurkowanie & ~jacht & ~wspinaczka & widoki" +
//                "& ~klimatyzacja & ~telefon & ~Internet & ~WiFi & ~komputer" +
//                "& rower & bieg & kawa & gory & morze & pieszo & zima");
//        WalkSAT walkSAT = new WalkSAT();
//        Model m = walkSAT.walkSAT(ConvertToConjunctionOfClauses.convert(kb.asSentence())
//                .getClauses(), 0.5, -1);
//        Assert.assertNotNull(m);
//        m.print();
    }
}
