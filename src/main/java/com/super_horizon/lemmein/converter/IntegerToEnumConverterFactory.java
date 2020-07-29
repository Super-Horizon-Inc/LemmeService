// package com.super_horizon.lemmein.converter;

// import org.springframework.core.convert.converter.*;
// import org.springframework.data.convert.ReadingConverter;
// import org.springframework.core.convert.converter.ConverterFactory;
// import java.lang.Enum;

// public class IntegerToEnumConverterFactory implements ConverterFactory<Integer, Enum> {
      
//     @Override
//     public <T extends Enum> Converter<Integer, T> getConverter(Class<T> targetType) {
//         Class<?> enumType = targetType;
//         while (enumType != null && !enumType.isEnum()) {
//             enumType = enumType.getSuperclass();
//         }
//         if (enumType == null) {
//             throw new IllegalArgumentException(
//                     "The target type " + targetType.getName() + " does not refer to an enum");
//         }
//         return new IntegerToEnum(enumType);
//     }
    
//     @ReadingConverter
//     public static class IntegerToEnum<T extends Enum>  implements Converter<Integer, Enum> {
//         private final Class<T> enumType;
//         public IntegerToEnum(Class<T> enumType) {
//             this.enumType = enumType;
//         }
//         @Override
//         public Enum convert(Integer source) {
//                 for(T t : enumType.getEnumConstants()) {
//                     if(t instanceof IntEnumConvertable)
//                     {
//                         if(((IntEnumConvertable)t).getValue() == source.intValue()) {
//                             return t;
//                         }                         
//                     }                     
//                 }
//                 return null;   
//         }
//     }
// }