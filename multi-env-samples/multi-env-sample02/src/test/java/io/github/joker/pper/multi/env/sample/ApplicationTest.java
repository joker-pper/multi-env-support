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
     * 取ak值,通过环境动态获取,此时key需存在
     * 注: 若key可以不存在并默认值为空可调整为 ${sys.${sys.env}.ak:}
     */
    @Value("${sys.${sys.env}.ak}")
    private String ak;

    /**
     * 取sk值,通过环境动态获取,此时key需存在
     */
    @Value("${sys.${sys.env}.sk}")
    private String sk;

    /**
     * 取token值,通过环境动态获取,此时key需存在
     */
    @Value("${sys.${sys.env}.token}")
    private String token;

    /**
     * 取other值,通过环境动态获取,并在key不存在时使用缺省值-1
     */
    @Value("${sys.${sys.env}.other:-1}")
    private String other;

    /**
     * 取otherWithDefault值,先通过环境动态获取再通过default环境获取值最后则为设置的缺省值
     */
    @Value("${sys.${sys.env}.other:${sys.default.other:???}}")
    private String otherWithDefault;

    @Test
    public void test() {
        Assert.assertEquals("222", ak);
        Assert.assertEquals("zxc", sk);
        Assert.assertEquals("12345", token);
        Assert.assertEquals("-1", other);
        Assert.assertEquals("ssss", otherWithDefault);
    }

}
