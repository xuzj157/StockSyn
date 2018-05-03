package personal.xuzj157.stocksyn.utils;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class JFreeUtilsTest {

    @Test
    public void allInOneTest() throws IOException {
        Map<String, Map<Double, Integer>> map = new HashMap<>();
        Random rand = new Random();

        for (Integer i = 1; i < 3; i++) {
            Map<Double, Integer> maps = new HashMap<>();
            for (Integer j = 0; j < 3000; j++) {
                maps.put(j.doubleValue(), rand.nextInt(100));
            }
            map.put(i.toString(), maps);
        }

        JFreeUtils.allInOne(map);

    }

}
