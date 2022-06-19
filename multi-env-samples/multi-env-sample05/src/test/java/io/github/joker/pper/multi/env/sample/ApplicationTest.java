package io.github.joker.pper.multi.env.sample;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    /**
     * 取ak值,此时key需存在
     * 注: 若key可以不存在并默认值为空可调整为 ${sys.env.ak:}
     */
    @Value("${sys.env.ak}")
    private String ak;

    /**
     * 取sk值,此时key需存在
     */
    @Value("${sys.env.sk}")
    private String sk;

    /**
     * 取token值,此时key需存在
     */
    @Value("${sys.env.token}")
    private String token;

    /**
     * 取other值,并在key不存在时使用缺省值-1
     */
    @Value("${sys.env.other:-1}")
    private String other;


    @Test
    public void test() {
        Assert.assertEquals("222", ak);
        Assert.assertEquals("zxc", sk);
        Assert.assertEquals("12345", token);
        Assert.assertEquals("", other);
    }

}
