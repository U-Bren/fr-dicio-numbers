package org.dicio.numbers.lang.it;

import org.dicio.numbers.NumberParserFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class NiceNumberTest {

    private static NumberParserFormatter pf;

    @BeforeClass
    public static void setup() {
        pf = new NumberParserFormatter(new ItalianFormatter(), null);
    }

    @Test
    public void speech() {
        assertEquals("trenta quattro e mezzo", pf.niceNumber(34.5).get());
        assertEquals("meno diciotto e tre quinti", pf.niceNumber(-18.6).get());
        assertEquals("novanta otto e diciotto diciannovesimi", pf.niceNumber(98.947368421).get());
        assertEquals("meno cinque e sei undiciesimi", pf.niceNumber(-5.5454545).get());
        assertEquals("sette noni", pf.niceNumber(7.0 / 9).get());
        assertEquals("meno due diciassettesimi", pf.niceNumber(-2.0 / 17).get());
        assertEquals("quattrocento sessanta cinque", pf.niceNumber(465).get());
        assertEquals("meno novanta uno", pf.niceNumber(-91).get());
        assertEquals("zero", pf.niceNumber(0).get());
    }

    @Test
    public void noSpeech() {
        assertEquals("34 1/2", pf.niceNumber(34.5).speech(false).get());
        assertEquals("-18 3/5", pf.niceNumber(-18.6).speech(false).get());
        assertEquals("98 18/19", pf.niceNumber(98.947368421).speech(false).get());
        assertEquals("-5 6/11", pf.niceNumber(-5.5454545).speech(false).get());
        assertEquals("7/9", pf.niceNumber(7.0 / 9).speech(false).get());
        assertEquals("-2/17", pf.niceNumber(-2.0 / 17).speech(false).get());
        assertEquals("465", pf.niceNumber(465).speech(false).get());
        assertEquals("-91", pf.niceNumber(-91).speech(false).get());
        assertEquals("0", pf.niceNumber(0).speech(false).get());
    }

    @Test
    public void customDenominators() {
        assertEquals("meno quattro e quattro decimi", pf.niceNumber(-4.4).denominators(Arrays.asList(2, 3, 4, 6, 7, 8, 9, 10, 11)).get());
        assertEquals("-64 6/12", pf.niceNumber(-64.5).speech(false).denominators(Collections.singletonList(12)).get());
        assertEquals("meno tre e cinque cento mila milionesimi", pf.niceNumber(-3.5).denominators(Arrays.asList(1000000, 2000000)).get());
        assertEquals("9 1000000/2000000", pf.niceNumber(9.5).speech(false).denominators(Arrays.asList(2000000, 1000000)).get());
        assertEquals("zero virgola otto", pf.niceNumber(4.0 / 5).denominators(Arrays.asList(2, 3, 4)).get());
    }

    @Test
    public void invalidFraction() {
        assertEquals("uno virgola otto quattro", pf.niceNumber(1.837).get());
        assertEquals("meno trenta otto virgola uno nove due", pf.niceNumber(-38.192).get());
        assertEquals("3829.48", pf.niceNumber(3829.47832).speech(false).get());
        assertEquals("-7.19", pf.niceNumber(-7.1928).speech(false).get());
        assertEquals("-9322.38", pf.niceNumber(-9322 - 8.0 / 21).speech(false).get());
    }
}