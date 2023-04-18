import com.tao.commons.utils.FileUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author：cboss
 * @Date：2023/4/2
 */
public class FileUtilsTest {
    @Test
    public void testFileUtils() throws IOException {
        FileUtils.createNewFile("D:\\bak\\code\\aa\\bb\\abc.txt");
    }
}
