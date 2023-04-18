import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.tao.commons.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author：cboss
 * @Date：2023/3/31
 */
@Slf4j
public class JWTUtilsTest {

    @Test
    public void testJWTUtils() {
        String userId = "1234566789";
        String token = JWTUtils.getTokenByUserId(userId);
        log.info("token获取成功:{}", token);

        try {
            JWTUtils.verify(token);
        } catch (Exception e) {
            log.error("token验证失败:{}", e.getMessage());
        }
        log.info("token验证成功:{}", token);

        String userID = JWTUtils.getUserIdByToken(token);
        log.info("根据token获取信息成功:{}", userID);
    }

    @Test
    public void verify() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODAyMjU3NjgsInVzZXJJZCI6IjEyMzQ1NjY3ODkifQ.nWnv-0FU7bDv8EqrvYPu67skm2YdsKbiW-7ILZnVXmE";
        try {
            JWTUtils.verify(token);
        } catch (SignatureVerificationException e) {
            log.error("token验证失败,无效签名,请求头中的token为{}");
            e.printStackTrace();
        } catch (TokenExpiredException e) {
            log.error("token验证失败,token过期,请求头中的token为{}");
            e.printStackTrace();
        } catch (AlgorithmMismatchException e) {
            log.error("token验证失败,token算法不一致,请求头中的token为{}");
            e.printStackTrace();
        } catch (Exception e) {
            log.error("token验证失败,token无效,请求头中的token为{}");
            e.printStackTrace();
        }
        log.info("token验证成功");
    }

    @Test
    public void getInfo() {

    }
}
