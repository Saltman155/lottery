package com.superywd.library.restserver.transform;

import com.superywd.library.restserver.model.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * 基础类型转换器
 * @author 迷宫的中心
 * @date 2019/4/17 17:14
 */
class BasicTransformer {

    /**short转换器*/
    public static class ShortTransformer implements Transformer{

        private MethodParameter parameter;

        public ShortTransformer(MethodParameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public Object stringValueTransform(String value) {
            return new Short(value);
        }

        @Override
        public Object requestValueTransform(Map<String, List<String>> values) {
            return stringValueTransform(values.get(parameter.getName()).get(0));
        }
    }
    /**Integer转换器*/
    public static class IntegerTransformer implements Transformer{

        private MethodParameter parameter;

        public IntegerTransformer(MethodParameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public Object stringValueTransform(String value) {
            return new Integer(value);
        }

        @Override
        public Object requestValueTransform(Map<String, List<String>> values) {
            return stringValueTransform(values.get(parameter.getName()).get(0));
        }
    }

    /**Long转换器*/
    public static class LongTransformer implements Transformer{

        private MethodParameter parameter;

        public LongTransformer(MethodParameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public Object stringValueTransform(String value) {
            return new Long(value);
        }

        @Override
        public Object requestValueTransform(Map<String, List<String>> values) {
            return stringValueTransform(values.get(parameter.getName()).get(0));
        }
    }

    /**String转换器*/
    public static class StringTransformer implements Transformer{

        private MethodParameter parameter;

        public StringTransformer(MethodParameter parameter) {
            this.parameter = parameter;
        }

        @Override
        public Object stringValueTransform(String value) {
            return value;
        }

        @Override
        public Object requestValueTransform(Map<String, List<String>> values) {
            return stringValueTransform(values.get(parameter.getName()).get(0));
        }
    }

}
