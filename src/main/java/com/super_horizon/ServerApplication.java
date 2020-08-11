package com.super_horizon;

// import com.super_horizon.lemmein.repositories.*;
// import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

// import org.springframework.data.convert.CustomConversions;
// import org.springframework.core.convert.converter.*;
// import java.util.*;
// import com.super_horizon.lemmein.converter.*;

// import org.springframework.data.mongodb.MongoDatabaseFactory;
// import org.springframework.data.mongodb.core.convert.*;
// import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
// import org.springframework.core.convert.ConversionService;
// import org.springframework.core.convert.support.GenericConversionService;
// import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@EnableMongoRepositories(basePackageClasses = {CustomerRepository.class, UserRepository.class})
public class ServerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ServerApplication.class);
    }

    // @Autowired
    // private MongoDatabaseFactory mongoDatabaseFactory;

    // @Autowired
    // private MongoMappingContext mongoMappingContext;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
    }

    // @Bean
    // public MongoCustomConversions mongoCustomConversions() {
    //     List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();
    //     converters.add(new IntegerEnumConverters.EnumToIntegerConverter());     
    //     // this is a dummy registration , actually it's a work-around because
    //     // spring-mongodb doesnt has the option to reg converter factory.
    //     // so we reg the converter that our factory uses. 
    //     converters.add(new IntegerToEnumConverterFactory.IntegerToEnum(null));      
    //     return new MongoCustomConversions(converters);
    // }

    // @Bean
    // public MappingMongoConverter mappingMongoConverter() throws Exception {
    //     // MongoMappingContext mappingContext = new MongoMappingContext();
    //     // mappingContext.setApplicationContext(appContext);
    //     DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
    //     MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);        
    //     mongoConverter.setCustomConversions(mongoCustomConversions());       
    //     ConversionService convService = mongoConverter.getConversionService();
    //     ((GenericConversionService)convService).addConverterFactory(new IntegerToEnumConverterFactory());                  
    //     mongoConverter.afterPropertiesSet();
    //     return mongoConverter;
    // } 

}
