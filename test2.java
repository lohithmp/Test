package com.epay.gateway.config;

import com.epay.gateway.config.kafka.PaymentProducer;
import com.epay.gateway.entity.MerchantOrderPaymentEntity;
import com.epay.gateway.repository.GatewayPoolingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.batch.core.repository.support.JobExecutionDao;
import org.springframework.batch.core.repository.support.JobInstanceDao;
import org.springframework.batch.core.repository.support.StepExecutionDao;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {
    private final GatewayPoolingRepository gatewayPoolingRepository;
    private final PaymentProducer paymentProducer;

    @Bean
    public JobRepository jobRepository() {
        return new SimpleJobRepository(
                new JobInstanceDao() {}, 
                new JobExecutionDao() {}, 
                new StepExecutionDao() {}, 
                new StepExecutionDao() {}
        );
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        return jobLauncher;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    @StepScope
    public ItemReader<MerchantOrderPaymentEntity> reader() {
        List<MerchantOrderPaymentEntity> entities = gatewayPoolingRepository.findMerchantOrderPaymentDetails();
        return new ListItemReader<>(entities);
    }

    @Bean
    public ItemProcessor<MerchantOrderPaymentEntity, MerchantOrderPaymentEntity> processor() {
        return item -> {
            System.out.println("-----------------" + item);
            return item;
        };
    }

    @Bean
    public ItemWriter<MerchantOrderPaymentEntity> writer() {
        return items -> items.forEach(paymentProducer::publishToKafka);
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .<MerchantOrderPaymentEntity, MerchantOrderPaymentEntity>chunk(1, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("paymentJob", jobRepository)
                .start(step)
                .build();
    }
}
