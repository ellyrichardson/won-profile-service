package com.wantornot.profileservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import com.wantornot.profileservice.utility.ZonedDateTimeReadConverter;
import com.wantornot.profileservice.utility.ZonedDateTimeWriteConverter;

@Configuration
public class MongoConfig {
	@Bean
    @Primary
    public CustomConversions customConversions(){
        List<Converter<?,?>> converters = new ArrayList<Converter<?,?>>();
        converters.add(new ZonedDateTimeReadConverter());
        converters.add(new ZonedDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
