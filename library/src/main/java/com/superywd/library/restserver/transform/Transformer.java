package com.superywd.library.restserver.transform;

import java.util.List;
import java.util.Map;

/**
 * 字符串与值转换器
 * @author 迷宫的中心
 * @date 2019/4/17 17:12
 */
public interface Transformer {

    /**
     * 字符串值设置的转换器
     * @param value     字符串形式表示的参数
     * @return          实际值
     */
    Object stringValueTransform(String value);

    /**
     * 请求值类型转换
     * @param values   请求携带的参数
     * @return         转换后的值
     */
    Object requestValueTransform(Map<String, List<String>> values);

}
