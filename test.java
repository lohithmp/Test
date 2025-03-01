package com.example.gatewaypooling.config;

import com.example.gatewaypooling.model.MerchantOrderPayment;
import com.example.gatewaypooling.repository.MerchantOrderPaymentRepository;
import com.example.gatewaypooling.producer.PaymentProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableBatchProcessing
public class BatchConfig {

    private final MerchantOrderPaymentRepository paymentRepository;
    private final PaymentProducer paymentProducer;

    @Bean
    public RepositoryItemReader<MerchantOrderPayment> reader() {
        RepositoryItemReader<MerchantOrderPayment> reader = new RepositoryItemReader<>();
        reader.setRepository(paymentRepository);
        reader.setMethodName("findByTransactionStatusInAndPaymentStatusIn");
        reader.setArguments(Collections.singletonList(List.of("BOOKED", "PENDING"), List.of("PAYMENT_INITIATION_START", "PENDING")));
        reader.setPageSize(10);
        reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
        return reader;
    }

    @Bean
    public ListItemWriter<MerchantOrderPayment> writer() {
        return new ListItemWriter<>();
    }

    @Bean
    public Step step(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step")
            .<MerchantOrderPayment, MerchantOrderPayment>chunk(10)
            .reader(reader())
            .writer(items -> items.forEach(paymentProducer::publishToKafka))
            .build();
    }

    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, Step step) {
        return jobBuilderFactory.get("paymentJob")
            .incrementer(new RunIdIncrementer())
            .flow(step)
            .end()
            .build();
    }
}





______________&&_



package com.example.gatewaypooling.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobLauncher;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class GatewayPoolingService {

    private final JobLauncher jobLauncher;
    private final Job paymentJob;

    @Scheduled(fixedRate = 120000) // Runs every 2 minutes
    public void processMerchantOrders() {
        try {
            log.info("Starting batch job for merchant order payments...");
            JobParameters params = new JobParametersBuilder()
                    .addDate("startTime", new Date())
                    .toJobParameters();
            jobLauncher.run(paymentJob, params);
        } catch (Exception e) {
            log.error("Error running batch job: ", e);
        }
    }
}





111111111111111111111


        package com.example.gatewaypooling.service;

import com.example.gatewaypooling.model.MerchantOrderPayment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PaymentProcessingService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Retryable(value = Exception.class, maxAttempts = 480, backoff = @Backoff(delay = 180000)) // Retry every 3 mins for 24 hours
    public void processPayment(MerchantOrderPayment payment) {
        log.info("Processing ATRN: {} with PayMode: {}", payment.getAtrn(), payment.getPayMode());

        String url = switch (payment.getPayMode()) {
            case "INB" -> "http://payment-service/api/inb";
            case "OtherINB" -> "http://payment-service/api/otherinb";
            default -> throw new IllegalArgumentException("Unsupported PayMode: " + payment.getPayMode());
        };

        restTemplate.postForObject(url, payment, String.class);
    }
}
        
