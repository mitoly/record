import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springboot.SpringbootApplication;
import springboot.masterdata.user.entity.TsUser;
import springboot.masterdata.user.service.TsUserService;

/**
 * 测试单元测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplication.class)//这里的Application是springboot的启动类名
@WebAppConfiguration
public class HelloWorldControlerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Autowired
    private TsUserService userService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();

    }

    @Test
    public void test (){
        TsUser user = userService.findUserByAccount("admin");
        System.out.println(user.getUserName());
    }

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/login?account=admin&password=1") //HTTP请求可以选择post get...
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(MockMvcResultHandlers.print());
    }


//    public static void main(String[] args) {
//        SupplierInfoService_Service service = new SupplierInfoService_Service();
//        SupplierInfoService  infoService = service.getSupplierInfoServiceSOAP();
//        try {
//            int result = infoService.getProductionProcurementSupplierCount("", "",
//                    "", "","ccc");
//            System.out.println("显示返回值 ------ " + result);
//        } catch (GetProductionProcurementSupplierCountFault getProductionProcurementSupplierCountFault) {
//            getProductionProcurementSupplierCountFault.printStackTrace();
//            System.out.printf("报错catch");
//        }
//    }

}
