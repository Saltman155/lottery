package com.superywd.library.properties.transformers;


import com.superywd.library.properties.PropertyTransformer;

import java.lang.reflect.Field;

/**
 * @author 迷宫的中心
 * @date 2019/2/28 23:41
 */

public class ShortTransformer implements PropertyTransformer {

    /**维护一个实例*/
    public static final ShortTransformer INSTANCE = new ShortTransformer();

    @Override
    public Object transform(String value, Field field) {
        return null;
    }

}
