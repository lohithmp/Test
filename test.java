import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GatewayPoolingScheduler {
    private final Job paymentJob;
    private final JobLauncher jobLauncher;

    @Scheduled(fixedRate = 120000)
    public void gatewayOfflineProcess() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        jobLauncher.run(paymentJob, new JobParameters());
    }
}



package com.epay.gateway.config;

import com.epay.gateway.config.kafka.PaymentProducer;
import com.epay.gateway.entity.MerchantOrderPaymentEntity;
import com.epay.gateway.repository.GatewayPoolingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {
//    private final LoggerUtility logger = LoggerFactoryUtility.getLogger(this.getClass());
    private final GatewayPoolingRepository gatewayPoolingRepository;
    private final PaymentProducer paymentProducer;

    @Bean
    public ItemReader<MerchantOrderPaymentEntity> reader() {
        return () -> {
            List<MerchantOrderPaymentEntity> merchantOrderPaymentEntities = gatewayPoolingRepository.findMerchantOrderPaymentDetails();
            if(merchantOrderPaymentEntities.isEmpty()) {
//                logger.error("No Merchant Order Payments found");
            }

            return merchantOrderPaymentEntities.iterator().next();
        };
    }

    @Bean
    public ItemProcessor<MerchantOrderPaymentEntity, MerchantOrderPaymentEntity> processor() {
        return item -> {
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
        return new StepBuilder("step", jobRepository).<MerchantOrderPaymentEntity, MerchantOrderPaymentEntity>chunk(10, transactionManager)
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
    
