// package com.super_horizon.lemmein.converter;

// import org.springframework.core.convert.converter.*;
// import org.springframework.data.convert.WritingConverter;

// public class IntegerEnumConverters {
//     @WritingConverter
//     public static class EnumToIntegerConverter implements Converter<Enum<?>, Object> {
//         @Override
//         public Object convert(Enum<?> source) {
//             if(source instanceof IntEnumConvertable)
//             {
//                 return ((IntEnumConvertable)(source)).getValue();
//             }
//             else
//             {
//                 return source.name();
//             }               
//         }
//     }   
//  }