import com.tao.commons.utils.DateUtils;
import org.junit.Test;

import java.util.Date;

/**
 * @Author：cboss
 * @Date：2023/4/2
 */
public class DateUtilsTest {

    @Test
    public void test() {
        Date date = new Date();
        System.out.println("得到当前日期字符串:" + DateUtils.getDate(date));
        System.out.println("得到当前日期和时间字符串：" + DateUtils.getDateTime(date));
        System.out.println("获取当前年份字符串：" + DateUtils.getYear(date));
        System.out.println("获取当前月份字符串：" + DateUtils.getMonth(date));
        System.out.println("获取当前天数字符串：" + DateUtils.getDay(date));
        System.out.println("获取当前星期字符串：" + DateUtils.getWeek(date));
        System.out.println("加减年：" + DateUtils.getDate(DateUtils.addYears(date, 1), DateUtils.DATETIME_FORMAT));
        System.out.println("加减月：" + DateUtils.getDate(DateUtils.addMonths(date, 1), DateUtils.DATETIME_FORMAT));
        System.out.println("加减天：" + DateUtils.getDate(DateUtils.addDays(date, 1), DateUtils.DATETIME_FORMAT));
        System.out.println("加减分钟：" + DateUtils.getDate(DateUtils.addMinutes(date, 1), DateUtils.DATETIME_FORMAT));
        System.out.println("天开始时间：" + DateUtils.getStartDay(date));
        System.out.println("天结束时间：" + DateUtils.getEndDay(date));
        System.out.println("月开始时间：" + DateUtils.getStartMonth(date));
        System.out.println("月开始时间：" + DateUtils.getEndMonth(date));
        date = DateUtils.addDays(date, 1);
        System.out.println(DateUtils.getDate(date));
        System.out.println("周一开始时间：" + DateUtils.getStartWeek(date));
        System.out.println("周日结束时间：" + DateUtils.getEndWeek(date));
    }
}
