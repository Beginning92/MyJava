import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author: beginningwang
 * @version: 1.0.0
 * @createTime: 2019/5/8
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = {MyBatisConfig.class })
@ComponentScan(basePackages = {
        "com.dshamc.cia.clearing.manager",
        "com.dshamc.cia.clearing.service.impl",
        "com.dshamc.cia.clearing.aop",
        "com.fenqile"})
//@EnableTransactionManagement
@ImportResource({
        "classpath:/META-INF/spring/dubbo-consumer.xml",
        "classpath:/META-INF/spring/dubbo-test.xml",
})
@PropertySources({
        @PropertySource("classpath:dubbo_common_config.properties"),
        @PropertySource("classpath:app.properties")
})
@EnableAspectJAutoProxy
public class BaseTestCase {
}
