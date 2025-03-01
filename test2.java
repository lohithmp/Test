package com.epay.gateway.config;

import com.epay.gateway.config.kafka.PaymentProducer;
import com.epay.gateway.entity.MerchantOrderPaymentEntity;
import com.epay.gateway.repository.GatewayPoolingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Iterator;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {
//    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(this.getClass());
    private final GatewayPoolingRepository gatewayPoolingRepository;
    private final PaymentProducer paymentProducer;

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher taskExecutorJobLauncher = new TaskExecutorJobLauncher();
        taskExecutorJobLauncher.setJobRepository(jobRepository);
        taskExecutorJobLauncher.afterPropertiesSet();
        return taskExecutorJobLauncher;
    }

//    @Bean
//    @StepScope
//    public ItemReader<MerchantOrderPaymentEntity> reader() {
//        return new ItemReader<>() {
//            private Iterator<MerchantOrderPaymentEntity> iterator;
//            @Override
//            public MerchantOrderPaymentEntity read() {
//                if(iterator == null || !iterator.hasNext()) {
//                   iterator = gatewayPoolingRepository.findMerchantOrderPaymentDetails().iterator();
//                }
//                return iterator.hasNext() ? iterator.next() : null;
//            }
//
//        };
//    }

    @Bean
    @StepScope
    public ItemReader<MerchantOrderPaymentEntity> reader() {
        List<MerchantOrderPaymentEntity> entities = gatewayPoolingRepository.findMerchantOrderPaymentDetails();
        return new ListItemReader<>(entities);
    }

    @Bean
    public ItemProcessor<MerchantOrderPaymentEntity, MerchantOrderPaymentEntity> processor() {
        return item -> {
            System.out.println("-----------------"+ item);
//            logger.info("Processing ATRN: {}", item.getAtrnNumber());
            return item;
        };
    }


    @Bean
    public ItemWriter<MerchantOrderPaymentEntity> writer() {
        return items -> items.forEach(paymentProducer::publishToKafka);
    }

    @Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository).<MerchantOrderPaymentEntity, MerchantOrderPaymentEntity>chunk(1, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("paymentJob", jobRepository).start(step).build();
    }



}
