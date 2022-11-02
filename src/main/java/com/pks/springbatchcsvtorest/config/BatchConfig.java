package com.pks.springbatchcsvtorest.config;

import com.pks.springbatchcsvtorest.domain.Person;
import com.pks.springbatchcsvtorest.listener.JobListener;
import com.pks.springbatchcsvtorest.writer.RestItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FlatFileItemReader<Person> reader() {
        return new FlatFileItemReaderBuilder<Person>()
                .name("reader")
                .resource(new ClassPathResource("person-data.csv"))
                .delimited()
                .names(new String[]{"id", "name"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Person.class);
                }})
                .linesToSkip(1)
                .build();
    }

    @Bean
    public Job processJob(JobListener listener, Step step) {
        return jobBuilderFactory.get("importPersonJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end().build();
    }

    @Bean
    public Step step(RestItemWriter<Person> writer) {
        return stepBuilderFactory.get("step")
                .<Person, Person>chunk(10)
                .reader(reader())
                .writer(writer)
                .build();
    }
}
