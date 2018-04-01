package com.zq.springcloud.application.service;

import com.zq.springcloud.application.beans.PCDDTO;
import com.zq.springcloud.application.beans.PcdData;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/***
 * @author 01367477
 */

@Service
public class PcdDataService {

    private static ArrayList<String[]> boundsNameArray = new ArrayList<String[]>();
    /***二维数组 对应的code */
    private static final Logger logger = Logger.getLogger(PcdDataService.class);
    /***二维数组 [纬度][经度] */
    private static ArrayList<long[]> boundsArray = new ArrayList<long[]>();
    /***hashmap对应的编码表*/
    private static Map pSDCodeMap = new HashMap<String, PCDDTO>();
    /***�?大的纬度*/
    private static long maxLatinFile = 1L;
    /***�?小的纬度*/
    private static long minLatinFile = 1L;

    /***
     *
     * @param lnt 经度
     * @param lat 纬度
     * @return 经纬度对应的地区代码
     */
    public  String otainPCDCode(Long lnt, Long lat) {

        if (lat >= maxLatinFile || lat <= minLatinFile) {
            return "UnKnown";
        } else {
            return binarySearch(lat, lnt);
        }
    }
    /***
     * 由pcdData中的经纬度查询并补全其中的数�?
     */
    public void otainPCDData(PcdData pcdData) {
        long lngi=(long)(pcdData.getLongitude()*1000);
        long lati=(long)(pcdData.getLatitude()*1000);
        logger.info(lngi+","+lati);
        String code=otainPCDCode(lngi,lati);
        logger.info(code);
        pcdData.setCode(code);
        if(!"900000".equals(code)){
            PCDDTO pcddto= (PCDDTO) pSDCodeMap.get(code);
            pcdData.setProvince(pcddto.getProvince());
            pcdData.setCity(pcddto.getCity());
            pcdData.setDistrict(pcddto.getDistrict());
            pcdData.setDetails(pcddto.toString());
        }else{
            pcdData.setDetails("UnKnown");
        }
    }
    /***
     *
     * @param lnt 经度
     * @param lat 纬度
     * @return 经纬度对应的地区名称
     */
    public  String otainPCDName(Long lnt, Long lat) {
       return pSDCodeMap.get(otainPCDCode(lnt,lat)).toString();
    }

    /**
     * @param latit  当前纬度纬度
     * @param longit 当前查询地区的经�?
     * @return 地区名称(省市�?)
     */
    public  String binarySearch(Long latit, Long longit) {
        //�?给的经度不在对应的边界�?�的�?�?/�?小范围内
        if (maxLatinFile < latit || minLatinFile > latit) {
            return "900000";
        } else {
            Long bounsArrNum = latit - minLatinFile;
            //补全的数�?,则其对应的下标�?�等于其纬度-�?小的纬度
            if (bounsArrNum == -1) {
                return "900000";
            }
            long[] bounsArr = boundsArray.get(bounsArrNum.intValue());
            //查找经度
            int low = 0;
            int high = bounsArr.length - 1;
            int middle = high;
            while (low < high) {
                if(bounsArr[low]>longit){
                    return "900000";
                }
                middle = (low + high) / 2;
                if(middle==low){
                    break;
                }
                Long middleValue = bounsArr[middle];
                if (longit < middleValue) {
                    high = middle;
                } else {
                    low = middle;
                }
            }
            return boundsNameArray.get(bounsArrNum.intValue())[low];
        }
    }

    /**
     * 读取文件并初始化相关变量的�??
     */
    @PostConstruct
    private  void init() throws IOException {
        /** *
         * BoundsCodeData为处理完成的边界值的数据文件
         * 每行由一个Latitude纬度 多个经度-右侧�?属区域编码构�? �?;分割
         * 行与行之间存在连续�??
         */
        String savepath = "D:/phpStudy/WWW/Bounds/BoundsCodeData.txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(savepath), "UTF-8"));
        String line = "";
        //读取边界数据至内�?
        while ((line = reader.readLine()) != null) {
            String latStr = line.split(";")[0];
            if (minLatinFile == 1.0) {
                minLatinFile = Long.valueOf(latStr).longValue();
            }
            maxLatinFile = Long.valueOf(latStr).longValue();
            String[] temp = line.split(";");
            long[] boundsArr = new long[temp.length - 1];
            String[] boundsNameArr = new String[temp.length - 1];
            for (int i = 1; i < temp.length; i++) {
                String [] longiCode=temp[i].split("-");
                boundsArr[i - 1] = Long.valueOf(longiCode[0]).longValue();
                boundsNameArr[i - 1] = longiCode[1];
            }
            boundsArray.add(boundsArr);
            boundsNameArray.add(boundsNameArr);
        }
        /***
         * 读取编码表至内存
         * psdcode为处理完成的编码对应表的文件
         * 每行为一个地区编码数�?
         * 包含 code,�?,�?,�? �?"," 分割
         */
        String psdcodepath = "D:/phpStudy/WWW/Bounds/psdcode.csv";
        BufferedReader psdcodereader = new BufferedReader(new InputStreamReader(new FileInputStream(psdcodepath), "UTF-8"));
        String psdcodereaderline = "";
        String[] data;
        String code;
        while ((psdcodereaderline = psdcodereader.readLine()) != null) {
            if (!"".equals(psdcodereaderline.trim())) {
                data = psdcodereaderline.split(",");
                code = data[0];
                pSDCodeMap.put(code, new PCDDTO(data[1],data[2],data[3]));
            }
        }
        reader.close();
        psdcodereader.close();
    }


//    public static void main(String[] args) throws IOException {
//
//        init();
//        System.out.println(otainPCDCode(113898L,22541L));
////        Long startTime = System.nanoTime();
////        Long slongitude = 110000L;
////        Long elongitude = 120000L;
////        Long slatitude = 30000L;
////        Long elatitude = 35000L;
////        Long times = (elatitude - slatitude) * (elongitude - slongitude);
////        for (Long i = slongitude; i <= elongitude; i++) {
////            for (Long j = slatitude; j <= elatitude; j++) {
////                String ccc = otainPCDCode(i, j);
////                System.out.println(i + "," + j + ":" + ccc + ":" + pSDCodeMap.get(ccc));
////            }
////        }
////        Long endTime = System.nanoTime();
////        System.out.println("all time spend as run " + times + "times:" + (endTime - startTime) + "ms");
//    }

}

