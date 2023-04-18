import com.tao.commons.utils.IdUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author：cboss
 * @Date：2023/3/31
 */
@Slf4j
public class IDTest {

    @Test
    public void test() {
        log.info("UUID:{}", IdUtils.UUID());
        log.info("timeID:{}", IdUtils.timeID());
    }
}
