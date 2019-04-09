package com.common.util.spark;

//import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaRDD;
//import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * Created by zhoucg on 2019-01-30.
 *  本案例假设我们需要对某个省的人口 (10万) 性别还有身高进行统计，需要计算出男女人数，
 *  男性中的最高和最低身高，以及女性中的最高和最低身高。
 *  本案例中用到的源文件有以下格式, 三列分别是 ID，性别，身高 (cm)，格式如下：
 *
 */
public class PeopleInfoCalculator {


    /**
     * 对于这个案例，我们要分别统计男女的信息，那么很自然的想到首先需要对于男女信息从源文件的对应的 RDD
     * 中进行分离，这样会产生两个新的 RDD，分别包含男女信息；其次是分别对男女信息对应的 RDD 的数据进行
     * 进一步映射，使其只包含身高数据，这样我们又得到两个 RDD，分别对应男性身高和女性身高；最后需要对这两个
     * RDD 进行排序，进而得到最高和最低的男性或女性身高。
     *   第一步，先分离男女信息，使用 filter 算子过滤条件包含”M” 的行是男性，
     *   包含”F”的行是女性；第二步我们需要使用 map 算子把男女各自的身高数据从
     *   RDD 中分离出来；第三步我们需要使用 sortBy 算子对男女身高数据进行排序。
     * 特别注意：RDD 转化的过程中需要把身高数据转换成整数，
     * 否则 sortBy 算子会把它视为字符串，那么排序结果就会受到影响，
     * 例如 身高数据如果是：123,110,84,72,100，
     * 那么升序排序结果将会是 100,110,123,72,84，显然这是不对的。
     * ---------------------
     *作者：fengzhimohan
     *来源：CSDN
     *原文：https://blog.csdn.net/fengzhimohan/article/details/78564610
     *版权声明：本文为博主原创文章，转载请附上博文链接！
     *
     */
    public static void main(String[] args) {

//        SparkConf sparkConf = new SparkConf().setAppName("PeopleInfoCalculator").setMaster("local[2]");
//
//        JavaSparkContext sc = new JavaSparkContext(sparkConf);
//        JavaRDD<String> dataFile = sc.textFile("F:\\PeopleInfo.txt");
//
//
//        JavaRDD<String> maleFilterData = dataFile.filter(s -> s.contains("M"));
//        JavaRDD<String> femaleFilterData = dataFile.filter(s -> s.contains("F"));
//        JavaRDD<String> maleHeightData = maleFilterData.flatMap(s -> Arrays.asList(s.split(" ")[2]).iterator());
//        JavaRDD<String> femaleHeightData = femaleFilterData.flatMap(s -> Arrays.asList(s.split(" ")[2]).iterator());
//        JavaRDD<Integer> maleHeightDataInt = maleHeightData.map(s -> Integer.parseInt(String.valueOf(s)));
//        JavaRDD<Integer> femaleHeightDataInt = femaleHeightData.map(s -> Integer.parseInt(String.valueOf(s)));
//        //sortBy(<T>,ascending,numPartitions) 解释:
//        //第一个参数是一个函数，该函数的也有一个带T泛型的参数，返回类型和RDD中元素的类型是一致的；
//        //第二个参数是ascending，这参数决定排序后RDD中的元素是升序还是降序，默认是true，也就是升序；
//        //第三个参数是numPartitions，该参数决定排序后的RDD的分区个数，默认排序后的分区个数和排序之前的个数相等，即为this.partitions.size。
//        JavaRDD<Integer> maleHeightLowSort = maleHeightDataInt.sortBy(s -> s,true,3);
//        JavaRDD<Integer> femaleHeightLowSort = femaleHeightDataInt.sortBy(s -> s,true,3);
//        JavaRDD<Integer> maleHeightHightSort = maleHeightDataInt.sortBy(s -> s,false,3);
//        JavaRDD<Integer> femaleHeightHightSort = femaleHeightDataInt.sortBy(s -> s,false,3);
//        Integer lowestMale = maleHeightLowSort.first(); //求出升序的第一个数，即最小值
//        Integer lowestFemale = femaleHeightLowSort.first();//求出升序的第一个数，即最小值
//        Integer highestMale = maleHeightHightSort.first();//求出降序的第一个数，即最大值
//        Integer highestFemale = femaleHeightHightSort.first();//求出降序的第一个数，即最大值
//
//        System.out.println("Number of Female Peole:" + femaleHeightData.count());//求出女性的总个数
//        System.out.println("Number of Male Peole:" + maleHeightData.count());//求出男性的总个数
//        System.out.println("Lowest Male:" + lowestMale);//求出男性最矮身高
//        System.out.println("Lowest Female:" + lowestFemale);//求出女性最矮身高
//        System.out.println("Highest Male:" + highestMale);//求出男性最高身高
//        System.out.println("Highest Female:" + highestFemale);//求出女性最高身高




    }
}
