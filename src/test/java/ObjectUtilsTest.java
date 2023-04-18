import com.tao.commons.result.ResponseResult;
import com.tao.commons.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.HashMap;

/**
 * @Author：cboss
 */
@Slf4j
public class ObjectUtilsTest {
    @Test
    public void test() throws IllegalAccessException {
        ResponseResult result = ResponseResult.success();
        HashMap<String, Object> hashMap = ObjectUtils.convertPojoToHashMap(result);
        log.info("hashMap:{}", hashMap);
    }
}