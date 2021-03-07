import com.xiaoying.credit.asset.date.DateUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class TestDateUtils {

    @Test
    public  void testParseStrDate6()
    {
        Assert.assertEquals(DateUtils.parseStrDate6("201901"),1546272000);
        Assert.assertEquals(DateUtils.parseStrDate8("20190101"),1546272000);
        Assert.assertEquals(DateUtils.parseStrDate14("20190101000000"),1546272000);
        Assert.assertEquals(DateUtils.parseStrDate6("201901"),1546272000);

        Assert.assertEquals(DateUtils.parseStrDate7("2019-01"),1546272000);
        Assert.assertEquals(DateUtils.parseStrDate10("2019-01-01"),1546272000);
        Assert.assertEquals(DateUtils.parseStrDate19("2019-01-01 00:00:00"),1546272000);

        Assert.assertEquals(DateUtils.parseStrDate7ToDate("2019-01"),DateUtils.timeStamp2Date(1546272000));
        Assert.assertEquals(DateUtils.parseStrDate10ToDate("2019-01-01"),DateUtils.timeStamp2Date(1546272000));

        Assert.assertEquals(DateUtils.strDate6(1546272000),"201901");
        Assert.assertEquals(DateUtils.strDate8(1546272000),"20190101");
        Assert.assertEquals(DateUtils.strDate14(1546272000),"20190101000000");
        Assert.assertEquals(DateUtils.strDate14(DateUtils.timeStamp2Date(1546272000)),"20190101000000");


        Assert.assertEquals(DateUtils.strDate7(1546272000),"2019-01");
        Assert.assertEquals(DateUtils.strDate7(DateUtils.timeStamp2Date(1546272000)),"2019-01");
        Assert.assertEquals(DateUtils.strDate10(1546272000),"2019-01-01");
        Assert.assertEquals(DateUtils.strDate10(DateUtils.timeStamp2Date(1546272000)),"2019-01-01");
        Assert.assertEquals(DateUtils.strDate19(1546272000),"2019-01-01 00:00:00");
        Assert.assertEquals(DateUtils.strDate19(DateUtils.timeStamp2Date(1546272000)),"2019-01-01 00:00:00");

    }

    @Test
    public void testStrTime2Second()
    {
        Assert.assertEquals(DateUtils.strTime2Second("09:24:10"),9*3600+24*60+10);
        Assert.assertEquals(DateUtils.strTime2Second("23:59:59"),23*3600+59*60+59);
        Assert.assertEquals(DateUtils.strTime2Second("00:00:00"),0*3600+0*60+0);
        Assert.assertEquals(DateUtils.strTime2Second("00:00:01"),0*3600+0*60+1);
    }

    @Test
    public  void testSecond2StrTime()
    {
        Assert.assertEquals(DateUtils.second2StrTime8(9*3600+24*60+10),"09:24:10");
        Assert.assertEquals(DateUtils.second2StrTime8(23*3600+59*60+59),"23:59:59");
        Assert.assertEquals(DateUtils.second2StrTime8(0),"00:00:00");
        Assert.assertEquals(DateUtils.second2StrTime8(1),"00:00:01");

        Assert.assertEquals(DateUtils.second2StrTime6(9*3600+24*60+10),"092410");
        Assert.assertEquals(DateUtils.second2StrTime6(23*3600+59*60+59),"235959");
        Assert.assertEquals(DateUtils.second2StrTime6(0),"000000");
        Assert.assertEquals(DateUtils.second2StrTime6(1),"000001");
    }

    @Test
    public  void testNowSecondOfToday()
    {
        LocalTime tmp = LocalTime.parse("14:43:34");
        Assert.assertEquals(tmp.getHour()*3600+tmp.getMinute()*60+tmp.getSecond(),14*3600+43*60+34);
        LocalTime now = LocalTime.now();
        Assert.assertEquals(DateUtils.nowSecondOfToday(),now.getHour()*3600+now.getMinute()*60+now.getSecond());
    }

    @Test
    public  void testSpanMonths()
    {
        Assert.assertEquals(DateUtils.spanMonths(DateUtils.parseStrDate8("20191128"),DateUtils.parseStrDate8("20200103")),3);
        Assert.assertEquals(DateUtils.spanMonthsBeginExclude(DateUtils.parseStrDate8("20191128"),DateUtils.parseStrDate8("20200103")),2);

        Assert.assertEquals(DateUtils.spanMonths(DateUtils.parseStrDate8ToDate("20191128"),DateUtils.parseStrDate8ToDate("20200103")),3);
        Assert.assertEquals(DateUtils.spanMonthsBeginExclude(DateUtils.parseStrDate8ToDate("20191128"),DateUtils.parseStrDate8ToDate("20200103")),2);
    }

    @Test
    public void testTruncDay()
    {
        Assert.assertEquals(DateUtils.truncDay(DateUtils.parseStrDate14("20191128142344")),DateUtils.parseStrDate8("20191128"));
        Assert.assertEquals(DateUtils.truncDay(DateUtils.parseStrDate14ToDate("20191128142344")),DateUtils.parseStrDate8ToDate("20191128"));

    }

    @Test
    public void testSpanDays()
    {
        Assert.assertEquals(DateUtils.spanDays(DateUtils.parseStrDate14("20191228142344"),DateUtils.parseStrDate14("20200102093012")),6);
        Assert.assertEquals(DateUtils.spanDaysBeginExclude(DateUtils.parseStrDate14("20191228142344"),DateUtils.parseStrDate14("20200102093012")),5);
        Assert.assertEquals(DateUtils.spanDays(DateUtils.parseStrDate14ToDate("20191228142344"),DateUtils.parseStrDate14ToDate("20200102093012")),6);
        Assert.assertEquals(DateUtils.spanDaysBeginExclude(DateUtils.parseStrDate14ToDate("20191228142344"),DateUtils.parseStrDate14ToDate("20200102093012")),5);
    }

    @Test
    public  void testAddDays()
    {
        Assert.assertEquals(DateUtils.addDays(DateUtils.parseStrDate14("20191228233012"),4),DateUtils.parseStrDate14("20200101233012"));
        Assert.assertEquals(DateUtils.addDays(DateUtils.parseStrDate14("20191228233012"),-29),DateUtils.parseStrDate14("20191129233012"));
        Assert.assertEquals(DateUtils.addDays(DateUtils.parseStrDate14("20191228233012"),0),DateUtils.parseStrDate14("20191228233012"));

        Assert.assertEquals(DateUtils.addDays(DateUtils.parseStrDate14ToDate("20191228233012"),4),DateUtils.parseStrDate14ToDate("20200101233012"));
        Assert.assertEquals(DateUtils.addDays(DateUtils.parseStrDate14ToDate("20191228233012"),-29),DateUtils.parseStrDate14ToDate("20191129233012"));
        Assert.assertEquals(DateUtils.addDays(DateUtils.parseStrDate14ToDate("20191228233012"),0),DateUtils.parseStrDate14ToDate("20191228233012"));
    }

    @Test
    public void testAddMonths()
    {
        Assert.assertEquals(DateUtils.addMonths(DateUtils.parseStrDate14("20191228233012"),2),DateUtils.parseStrDate14("20200228233012"));
        Assert.assertEquals(DateUtils.addMonths(DateUtils.parseStrDate14("20191228233012"),-5),DateUtils.parseStrDate14("20190728233012"));

        Assert.assertEquals(DateUtils.addMonths(DateUtils.parseStrDate14ToDate("20191228233012"),2),DateUtils.parseStrDate14ToDate("20200228233012"));
        Assert.assertEquals(DateUtils.addMonths(DateUtils.parseStrDate14ToDate("20191228233012"),-5),DateUtils.parseStrDate14ToDate("20190728233012"));
    }

    @Test
    public void testMonthDay()
    {
        Assert.assertEquals(DateUtils.monthDay(DateUtils.parseStrDate14("20191228233012")),28);
        Assert.assertEquals(DateUtils.monthDay(DateUtils.parseStrDate14ToDate("20191228233012")),28);
    }

    @Test
    public void testAddYears()
    {
        Assert.assertEquals(DateUtils.addYears(DateUtils.parseStrDate14("20191228233012"),2),DateUtils.parseStrDate14("20211228233012"));
        Assert.assertEquals(DateUtils.addYears(DateUtils.parseStrDate14("20191228233012"),-1),DateUtils.parseStrDate14("20181228233012"));

        Assert.assertEquals(DateUtils.addYears(DateUtils.parseStrDate14ToDate("20191228233012"),2),DateUtils.parseStrDate14ToDate("20211228233012"));
        Assert.assertEquals(DateUtils.addYears(DateUtils.parseStrDate14ToDate("20191228233012"),-1),DateUtils.parseStrDate14ToDate("20181228233012"));
    }

    @Test
    public void testTruncMonth()
    {
        Assert.assertEquals(DateUtils.truncMonth(DateUtils.parseStrDate19("2020-01-03 16:49:11")),DateUtils.parseStrDate19("2020-01-01 00:00:00"));
        Assert.assertEquals(DateUtils.truncMonth(DateUtils.parseStrDate19ToDate("2020-01-03 16:49:11")),DateUtils.parseStrDate19ToDate("2020-01-01 00:00:00"));
    }

    @Test
    public void testIsLeapYear()
    {
        Assert.assertEquals(DateUtils.isLeapYear(2020),true);
    }
}
