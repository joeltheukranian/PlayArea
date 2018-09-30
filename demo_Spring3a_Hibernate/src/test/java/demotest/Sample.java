package demotest;

import org.junit.Test;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Sample {

    @Test
    public void testAcreate() {
        System.out.println("first");
    }
    @Test
    public void testBupdate() {
        System.out.println("second");
    }
    @Test
    public void testCdelete() {
        System.out.println("third");
    }
}