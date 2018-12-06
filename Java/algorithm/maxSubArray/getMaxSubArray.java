/**
 * 给定一个数组A[0,…,n-1]，求A的连续子数组，使得该子数组的和最大。
 * 例如：
 * 数组： 1, -2, 3, 10, -4, 7, 2, -5，
 * 最大子数组：3, 10, -4, 7, 2
 *
 * @author ReyLee
 */
public class MaxSubArray {
    public static void main(String[] args) {
        float[] floats = getFloats(100000);

        // 暴力法 method1
        System.out.println(method1(floats));

        // 分治法 method2
        System.out.println(method2(floats));

        // 分析法 method3
        System.out.println(method3(floats));
    }

    private static float[] getFloats(int length) {
        float[] floats = new float[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            floats[i]=round(random.nextDouble()*100)-50;
        }
        return floats;
    }




    /**
     * 暴力法求解，时间复杂度为 O（n^2）
     *
     * @param floats
     * @return
     */
    public static List<Float> method1(float[] floats) {
        LocalTime start = LocalTime.now();

        int from = 0;
        int to = 0;
        float maxValue = floats[0];
        float sum;
        for (int i = 0; i < floats.length; i++) {
            sum = 0;
            for (int j = i; j < floats.length; j++) {
                sum += floats[j];
                if (maxValue < sum) {
                    maxValue = sum;
                    from = i;
                    to = j;
                }
            }
        }

        List<Float> result = new ArrayList<>();
        for (int k = from; k <= to; k++) {
            result.add(floats[k]);
        }

        LocalTime end = LocalTime.now();
        long duration = start.until(end, ChronoUnit.NANOS);
        System.out.println(duration);
        return result;
    }

    /**
     * 分治法：时间复杂度为O（nlogn）
     * 将数据从中间分开两份，则最大子数组存在情况如下：
     * 1.在左侧子数组
     * 2.在右侧子数组
     * 3.跨立于左侧和右侧子数组中间分界点
     * 解决方法：
     * 1.完全存在于左侧或者右侧递归嵌套完成
     * 2.跨立于分界点的即为左侧最大后缀和右侧最小后缀
     *
     * 时间复杂度的计算：
     *      当n=2^k 时
     *      当2^k < n <2^(k+1) 时
     * @param floats
     */
    public static List<Float> method2(float[] floats) {
        LocalTime start = LocalTime.now();

        ArrayList<Float> result = new ArrayList<>();

        float[] floats1 = method2_1(floats, 0, floats.length - 1);
        for (int i = (int) floats1[1]; i <= floats1[2]; i++) {
            result.add(floats[i]);
        }

        LocalTime end = LocalTime.now();
        long duration = start.until(end, ChronoUnit.NANOS);
        System.out.println(duration);
        return result;
    }

    private static float[] method2_1(float[] floats, int from, int to) {
        int middle = (from + to) / 2;
        if (middle == to) {
            float[] result = {floats[middle], middle, to};
            return result;
        }

        // 左侧子数组
        float[] left = method2_1(floats, from, middle);
        // 右侧子数组
        float[] right = method2_1(floats, middle + 1, to);
        // 跨分界线
        float leftMax = floats[middle];
        float tmp = floats[middle];
        int crossFrom = middle;
        for (int i = middle - 1; i >= from; i--) {
            tmp += floats[i];
            if (leftMax < tmp) {
                leftMax = tmp;
                crossFrom = i;
            }
        }
        float rightMax = floats[middle + 1];
        tmp = floats[middle + 1];
        int crossTo = middle + 1;
        for (int i = middle + 2; i <= to; i++) {
            tmp += floats[i];
            if (rightMax < tmp) {
                rightMax = tmp;
                crossTo = i;
            }
        }
        float crossMax = leftMax + rightMax;

        float max = Math.max(Math.max(left[0], right[0]), crossMax);

        if (max == left[0]) {
            return left;
        }
        if (max == right[0]) {
            return right;
        }
        if (max == crossMax) {
            float[] cross = {crossMax, crossFrom, crossTo};
            return cross;
        }
        return null;
    }


    /**
     * 分析法：时间复杂度为O（n）
     *      遍历数组，获取以每个元素为后缀的最大值
     * @param floats
     * @return
     */
    public static List<Float> method3(float[] floats) {
        LocalTime start = LocalTime.now();

        class FromTo {
            int from;
            int to;
            public FromTo(int from, int to) {
                this.from = from;
                this.to = to;
            }

            public int getFrom() {
                return from;
            }

            public int getTo() {
                return to;
            }
        }
        ArrayList<FromTo> fromTos = new ArrayList<>();

        float[] sum = new float[floats.length];
        sum[0] = floats[0];
        fromTos.add(new FromTo(0, 0));
        for (int i = 1; i < floats.length; i++) {
            if (floats[i] < (floats[i] + sum[i - 1])) {
                sum[i] = (floats[i] + sum[i - 1]);
                fromTos.add(new FromTo(fromTos.get(i - 1).getFrom(), i));
            } else {
                sum[i] = floats[i];
                fromTos.add(new FromTo(i, i));
            }
        }

        float max = sum[0];
        int position = 0;
        for (int i = 1; i < sum.length; i++) {
            if (max < sum[i]) {
                max = sum[i];
                position = i;
            }
        }

        ArrayList<Float> result = new ArrayList<>();
        for (int i = fromTos.get(position).getFrom(); i <= fromTos.get(position).getTo(); i++) {
            result.add(floats[i]);
        }

        LocalTime end = LocalTime.now();
        long duration = start.until(end, ChronoUnit.NANOS);
        System.out.println(duration);
        return result;
    }
    
}
