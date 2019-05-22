import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.SpringbootApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)//这里的Application是springboot的启动类名
public abstract class BaseTest {

}
