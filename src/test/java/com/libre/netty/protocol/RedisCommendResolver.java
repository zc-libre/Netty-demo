package com.libre.netty.protocol;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * @author ZC
 * @date 2021/7/25 1:52
 */
public class RedisCommendResolver {

    public static int getCommendLength(String commend){
        return getCommendArray(commend).length;
    }

    public static String[] getCommendArray(String commend) {
        if (StrUtil.isBlank(commend)) {
            throw new IllegalArgumentException("commend must not be blank");
        }

        List<String> list = StrUtil.splitTrim(commend, " ");
        String[] commendArray = ArrayUtil.toArray(list, String.class);
        if (commendArray.length < 2) {
            throw new IllegalArgumentException("commend error");
        }
        return commendArray;
    }

}
