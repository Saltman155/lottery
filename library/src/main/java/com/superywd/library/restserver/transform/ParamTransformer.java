package com.superywd.library.restserver.transform;

import com.superywd.library.restserver.model.MethodParameter;

/**
 * @author 迷宫的中心
 * @date 2019/4/17 17:08
 */
public class ParamTransformer {

    public static Transformer getCurrentTransformer(MethodParameter parameter){
        if(parameter.getType() == Short.class){ return new BasicTransformer.ShortTransformer(parameter); }
        if(parameter.getType() == Integer.class){ return new BasicTransformer.IntegerTransformer(parameter); }
        if(parameter.getType() == Long.class){ return new BasicTransformer.LongTransformer(parameter); }
        if(parameter.getType() == String.class){ return new BasicTransformer.StringTransformer(parameter); }
        throw new Error(String.format("不受支持的参数类型！对于类型 %s",parameter.getType().getName()));
    }
}
