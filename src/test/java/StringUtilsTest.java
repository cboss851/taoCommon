import com.tao.commons.utils.StringUtils;
import org.junit.Test;

/**
 * @Author：cboss
 * @Date：2023/4/3
 */
public class StringUtilsTest {
    @Test
    public void test() {
        System.out.printf("null:%s\n", StringUtils.lowerCamelCase(null));
        System.out.printf(":%s\n", StringUtils.lowerCamelCase(""));
        System.out.printf("abc     --> %s\n", StringUtils.lowerCamelCase("abc"));
        System.out.printf("abc_def --> %s\n", StringUtils.lowerCamelCase("abc_def"));
        System.out.printf("ABC_DEF --> %s\n", StringUtils.lowerCamelCase("ABC_DEF"));
        System.out.printf("abc     --> %s\n", StringUtils.upperCamelCase("abc"));
        System.out.printf("abc_def --> %s\n", StringUtils.upperCamelCase("abc_def"));
        System.out.printf("ABC_DEF --> %s\n", StringUtils.upperCamelCase("ABC_DEF"));
    }
}
