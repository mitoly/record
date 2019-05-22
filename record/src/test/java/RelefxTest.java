import springboot.masterdata.user.entity.TsUser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RelefxTest extends BaseTest{

    public static void main(String[] args) throws Exception {
        Class userClass = TsUser.class;
        Constructor co = userClass.getConstructor();
        Object userObj = co.newInstance();

        Field account = userClass.getDeclaredField("account");
        account.setAccessible(true); // 如果是私有的属性或者方法，需要开启可用才行，否则会报错
        account.set(userObj, "admin");

        Method method = userClass.getDeclaredMethod("setPhone", String.class);
        method.setAccessible(true);
        method.invoke(userObj, "13816773702");
        TsUser user = (TsUser) userObj;
        System.out.println("account: "+ user.getAccount() +"  phone: " + user.getPhone());
    }


}
