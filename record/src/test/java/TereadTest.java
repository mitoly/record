public class TereadTest extends BaseTest {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i <= 10000; i++) {
                System.out.println("t1 = " + i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i <= 10000; i++) {
                Thread.yield(); // 降低线程优先度
                try {
                    t1.join(); // 当前线程挂起，让调用线程获得时间片
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2 = " + i);
            }
        });
        t2.start();
        t1.start();
    }
}
