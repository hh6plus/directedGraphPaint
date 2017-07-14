package test.hh6plus.test;

import org.joda.time.DateTime;
import org.junit.Test;

/**
 * Created by huhong02 on 17/7/13.
 */
public class UtilTest {

    @Test
    public void test() {
        String a = new DateTime().toString("HHmmss");
        System.out.println(a);
    }
}
