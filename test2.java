2025-02-26 15:12:03 [KafkaConsumerDestination{consumerDestinationName='bankbranches.LOHITH_M.BANKBRANCHES', partitions=1, dlqName='null'}.container-0-C-1] ERROR o.s.k.listener.DefaultErrorHandler  - userId= - correlationId= - Backoff none exhausted for bankbranches.LOHITH_M.BANKBRANCHES-0@1635
org.springframework.kafka.listener.ListenerExecutionFailedException: Listener failed
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.decorateException(KafkaMessageListenerContainer.java:2873)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeOnMessage(KafkaMessageListenerContainer.java:2814)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeOnMessage(KafkaMessageListenerContainer.java:2778)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.lambda$doInvokeRecordListener$53(KafkaMessageListenerContainer.java:2701)
	at io.micrometer.observation.Observation.observe(Observation.java:565)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeRecordListener(KafkaMessageListenerContainer.java:2699)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeWithRecords(KafkaMessageListenerContainer.java:2541)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeRecordListener(KafkaMessageListenerContainer.java:2430)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeListener(KafkaMessageListenerContainer.java:2085)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeIfHaveRecords(KafkaMessageListenerContainer.java:1461)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.pollAndInvoke(KafkaMessageListenerContainer.java:1426)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.run(KafkaMessageListenerContainer.java:1296)
	at java.base/java.util.concurrent.CompletableFuture$AsyncRun.run$$$capture(CompletableFuture.java:1804)
	at java.base/java.util.concurrent.CompletableFuture$AsyncRun.run(CompletableFuture.java)
	at java.base/java.lang.Thread.run(Thread.java:1583)
Caused by: org.springframework.kafka.KafkaException: Failed to execute runnable
	at org.springframework.integration.kafka.inbound.KafkaInboundEndpoint.doWithRetry(KafkaInboundEndpoint.java:82)
	at org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter$IntegrationRecordMessageListener.onMessage(KafkaMessageDrivenChannelAdapter.java:457)
	at org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter$IntegrationRecordMessageListener.onMessage(KafkaMessageDrivenChannelAdapter.java:422)
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeOnMessage(KafkaMessageListenerContainer.java:2800)
	... 13 common frames omitted
Caused by: org.springframework.messaging.MessagingException: Failed to handle Message
	at org.springframework.integration.dispatcher.BroadcastingDispatcher.invokeHandler(BroadcastingDispatcher.java:241)
	at org.springframework.integration.dispatcher.BroadcastingDispatcher.dispatch(BroadcastingDispatcher.java:192)
	at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:72)
	at org.springframework.integration.channel.AbstractMessageChannel.sendInternal(AbstractMessageChannel.java:390)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:334)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:304)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:187)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:166)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:47)
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:109)
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:99)
	at org.springframework.integration.core.ErrorMessagePublisher.publish(ErrorMessagePublisher.java:168)
	at org.springframework.integration.handler.advice.ErrorMessageSendingRecoverer.recover(ErrorMessageSendingRecoverer.java:83)
	at org.springframework.retry.support.RetryTemplate.handleRetryExhausted(RetryTemplate.java:560)
	at org.springframework.retry.support.RetryTemplate.doExecute(RetryTemplate.java:405)
	at org.springframework.retry.support.RetryTemplate.execute(RetryTemplate.java:233)
	at org.springframework.integration.kafka.inbound.KafkaInboundEndpoint.doWithRetry(KafkaInboundEndpoint.java:70)
	... 16 common frames omitted
Caused by: org.springframework.messaging.MessagingException: 'org.springframework.integration.support.BaseMessageBuilder org.springframework.integration.support.MessageBuilder.removeHeader(java.lang.String)'
	at org.springframework.integration.core.ErrorMessagePublisher.determinePayload(ErrorMessagePublisher.java:186)
	at org.springframework.integration.core.ErrorMessagePublisher.publish(ErrorMessagePublisher.java:162)
	... 21 common frames omitted
Caused by: java.lang.NoSuchMethodError: 'org.springframework.integration.support.BaseMessageBuilder org.springframework.integration.support.MessageBuilder.removeHeader(java.lang.String)'
	at org.springframework.cloud.stream.function.FunctionConfiguration.sanitize(FunctionConfiguration.java:395)
	at org.springframework.cloud.stream.function.FunctionConfiguration$FunctionWrapper.apply(FunctionConfiguration.java:817)
	at org.springframework.cloud.stream.function.FunctionConfiguration$FunctionToDestinationBinder$1.handleMessageInternal(FunctionConfiguration.java:657)
	at org.springframework.integration.handler.AbstractMessageHandler.doHandleMessage(AbstractMessageHandler.java:105)
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:73)
	at org.springframework.integration.dispatcher.AbstractDispatcher.tryOptimizedDispatch(AbstractDispatcher.java:132)
	at org.springframework.integration.dispatcher.UnicastingDispatcher.doDispatch(UnicastingDispatcher.java:148)
	at org.springframework.integration.dispatcher.UnicastingDispatcher.dispatch(UnicastingDispatcher.java:121)
	at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:72)
	at org.springframework.integration.channel.AbstractMessageChannel.sendInternal(AbstractMessageChannel.java:390)
	at org.springframework.integration.channel.AbstractMessageChannel.sendWithMetrics(AbstractMessageChannel.java:361)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:331)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:304)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:187)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:166)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:47)
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:109)
	at org.springframework.integration.endpoint.MessageProducerSupport.lambda$sendMessage$1(MessageProducerSupport.java:262)
	at io.micrometer.observation.Observation.observe(Observation.java:499)
	at org.springframework.integration.endpoint.MessageProducerSupport.sendMessage(MessageProducerSupport.java:262)
	at org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter.sendMessageIfAny(KafkaMessageDrivenChannelAdapter.java:391)
	at org.springframework.integration.kafka.inbound.KafkaMessageDrivenChannelAdapter$IntegrationRecordMessageListener.lambda$onMessage$0(KafkaMessageDrivenChannelAdapter.java:460)
	at org.springframework.integration.kafka.inbound.KafkaInboundEndpoint.lambda$doWithRetry$0(KafkaInboundEndpoint.java:77)
	at org.springframework.retry.support.RetryTemplate.doExecute(RetryTemplate.java:344)
	... 18 common frames omitted




package com.epay.admin.config;

import com.epay.admin.entity.cache.BankBankEntity;
import com.epay.admin.service.BankDetailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Service
public class GemFireProcessor {
    private final BankDetailService bankDetailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GemFireProcessor(BankDetailService bankDetailService) {
        this.bankDetailService = bankDetailService;
    }

    @Bean
    public Consumer<Message<String>> processBankBranch() {
        return message -> {
            try {
                System.out.println("Received message: "+ message.getPayload());
                JsonNode event = objectMapper.readTree(message.getPayload());
                String operation = event.get("op").asText();
                JsonNode after = event.get("after");

                if(("c".equals(operation) || "u".equals(operation)) && after !=null) {
                    BankBankEntity bankBankEntity = objectMapper.treeToValue(after, BankBankEntity.class);
                    bankDetailService.saveBankBranchDetails(bankBankEntity);
                } else if ("d".equals(operation)) {
                    Long branchId = after.get("branchId").asLong();
                    bankDetailService.deleteBankBranchByBranchId(branchId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}



#Spring Cloud Stream-Kafka Binder Configuration
spring.cloud.stream.bindings.processBankBranch-in-0.destination=bankbranches.LOHITH_M.BANKBRANCHES
spring.cloud.stream.bindings.processBankBranch-in-0.group=bankbranches-group
spring.cloud.stream.kafka.binder.brokers=localhost:9092

spring.cloud.stream.bindings.processBankBranch-out-0.destination=output
