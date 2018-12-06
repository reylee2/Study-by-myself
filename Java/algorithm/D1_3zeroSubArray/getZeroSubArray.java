/**
 * 求对于长度为N的数组A， 求子数组的和接近于0的子数组，要求时间复杂度为O（NlogN）
 * 例如：
 * 数组： 1, -2, 3, 10, -4, 7, 2, -5，
 * 零子数组：-4, 7, 2, -5
 * <p>
 * 思路：即字串和绝对值最小
 *
 * @author jdli
 */
public class D1_3ZeroSubArray {
    public static void main(String[] args) {
        float[] floats = {1, -2, 3, 10, -4, 7, 2, -5};

        // 暴力法：
//        System.out.println(method1(floats));

        // 分析法：
        System.out.println(method2(floats));
    }


    /**
     * 暴力法： 时间复杂度 O（n^2）
     *
     * @param floats
     * @return
     */
    public static List<Float> method1(float[] floats) {
        LocalTime start = LocalTime.now();

        float[] minVal = {abs(floats[0]), 0, 0};
        float tmp;
        for (int i = 1; i < floats.length; i++) {
            tmp = 0;
            for (int j = i; j >= 0; j--) {
                tmp += floats[j];
                if (minVal[0] > abs(tmp)) {
                    minVal[0] = abs(tmp);
                    minVal[1] = j;
                    minVal[2] = i;
                }
            }
        }

        ArrayList<Float> result = new ArrayList<>();

        for (int i = (int) minVal[1]; i <= minVal[2]; i++) {
            result.add(floats[i]);
        }

        LocalTime end = LocalTime.now();
        long duration = start.until(end, ChronoUnit.NANOS);
        System.out.println(duration);
        return result;
    }


    /**
     * 分析法：时间复杂度为O（nlogn）
     * <p>
     * 思路：计算前N项和，数组中的每个元素的前N项和分别相减的最小值为零子数组和，可以求出子数组
     * 时间复杂度的计算：
     *      前N项和的计算时间复杂度都为O（n） 快速排序时间复杂度为O（nlogn），由于是线性，所以该方法的时间复杂度为O（nlogn）
     *
     * @param floats
     * @return
     */
    public static List<Float> method2(float[] floats) {
        float[] sum = new float[floats.length];
        int[] position = new int[floats.length];
        sum[0] = floats[0];
        position[0] = 0;

        // 计算每个元素的前N项和
        for (int i = 1; i < floats.length; i++) {
            sum[i] = sum[i - 1] + floats[i];
            position[i] = i;
        }

        // 对前N项和的数组进行排序
        //      快速排序： 时间复杂度为O(nlogn)
        quickSort(sum,position);

        // 计算相邻前N项和的差，找出最小值
        float[] floats1 = {abs(sum[0]), 0, position[0]};
        for (int i = 1; i < floats.length; i++) {
            if (floats1[0] > (sum[i] - sum[i - 1])) {
                floats1[0] = (sum[i] - sum[i - 1]);
                floats1[1] = position[i - 1];
                floats1[2] = position[i];
            }
        }

        int little = (int) floats1[1]+1;
        int big = (int) floats1[2];
        if (floats1[1] > floats1[2]) {
            little = (int) floats1[2]+1;
            big = (int) floats1[1];
        }

        ArrayList<Float> result = new ArrayList<>();

        for (int i = little; i <= big; i++) {
            result.add(floats[i]);
        }

        return result;
    }
    
    private static void quickSort(float[] floats, int[] position) {
        quickSort_1(floats, position, 0, floats.length - 1);

        for (int i = 0; i < floats.length; i++) {
            System.out.println(floats[i]);
        }
    }

    private static void quickSort_1(float[] floats, int[] position, int from, int to) {
        int low = from;
        int high = to;
        float axis = floats[low];
        while (low < high) {
            while (low < high && floats[high]>axis) {
                high--;
            }
            if (low < high) {
                float tmp;
                tmp = floats[low];
                floats[low] = floats[high];
                floats[high] = tmp;

                int tmpPosition;
                tmpPosition = position[low];
                position[low] = position[high];
                position[high] = tmpPosition;
                low++;
            }
            while (low<high && floats[low]<axis) {
                low++;
            }
            if (low < high) {
                floats[high] = floats[low];
                floats[low] = axis;

                int tmpPosition;
                tmpPosition = position[low];
                position[low] = position[high];
                position[high] = tmpPosition;
                high--;
            }
        }

        if (low > from) {
            quickSort_1(floats,position,from,low-1);
        }
        if (high < to) {
            quickSort_1(floats,position,low+1,to);
        }
    }
}
