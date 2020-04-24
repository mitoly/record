import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.Random;

public class MathTest {

    public static void main(String[] args) {
        Random random = new Random();
        int[] arr = new int[100000];
        for (int i=0; i < arr.length; i++) {
            arr[i] = random.nextInt(1000);
        }
        long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        long nowl = System.currentTimeMillis();
//        arr = bubbleSoft(arr); // 冒泡排序
        arr = quickSoft(arr, 0, arr.length-1); // 快速排序
//        arr = mergeSort(arr, 0, arr.length-1); // 并归排序
        System.out.println(Arrays.toString(arr));
        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() - now + " ms");
        System.out.println(System.currentTimeMillis() - nowl + " ms");
    }

    /**
     * 快速排序
     */
    public static int[] quickSoft(int[] arr, int left, int right) {
        if (left >= right) {
            return arr;
        }
        int temp = arr[left]; // 基准，比基准大的放右边，比基准小的放左边
        int i = left;
        int j = right;
        while (i < j) {
            while (i < j && arr[j] >= temp) { // 右边存放大于等于 基准的数
                j --;
            }
            while (i < j && arr[i] <= temp) {  // 左边存放小于等于 基准的数
                i ++;
            }
            if (i < j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
            }
        }
        arr[left] = arr[i];
        arr[i] = temp;

        quickSoft(arr, left, i - 1);
        quickSoft(arr, i + 1, right);
        return arr;
    }

    /**
     * 冒泡排序
     */
    public static int[] bubbleSoft(int[] arr) {
        for (int i=1; i<arr.length; i++) {
            for (int j=0; j<arr.length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    /**
     * 归并排序
     */
    public static int[] mergeSort(int[] arr, int low, int high) {
        if (low >= high) {
            return arr;
        }
        int index = (low + high) / 2;
        mergeSort(arr, low, index);
        mergeSort(arr, index+1, high);
        merge(arr, low, index, high);

        return arr;
    }

    private static void merge(int[] arr, int low, int index, int high) {
        int[] tempArr = new int[high - low + 1];

        int i = low;
        int j = index + 1;
        int t = 0;

        while (i <= index && j <= high) {
            if (arr[i] < arr[j]) {
                tempArr[t++] = arr[i++];
            } else {
                tempArr[t++] = arr[j++];
            }
        }
        while (i <= index) {
            tempArr[t++] = arr[i++];
        }
        while (j <= high) {
            tempArr[t++] = arr[j++];
        }

        for (int temp : tempArr) {
            arr[low++] = temp;
        }
    }
}
