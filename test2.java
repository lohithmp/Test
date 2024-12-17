    public TransactionResponse<PaymentVerificationResponse> getPaymentStatusVerification(PaymentVerificationRequest paymentVerificationRequest) {
        MerchantDto merchantDto = getMerchantDto();
        paymentVerificationValidator.validatePaymentStatusRequest(paymentVerificationRequest, merchantDto.getMID());
        logger.info("Fetching data from Transaction and Order table.");
        List<Object[]> transactionOrderList = transactionDao.getTransactionAndOrderDetails(paymentVerificationRequest, merchantDto.getMID());
        PaymentVerificationResponse paymentVerificationResponse = buildPaymentVerificationResponse(transactionOrderList);
        return TransactionResponse.<PaymentVerificationResponse>builder()
                .data(List.of(paymentVerificationResponse))
                .status(1)
                .count(transactionOrderList.stream().count())
                .total((long) transactionOrderList.size())
                .build();
    }


    private PaymentVerificationResponse buildPaymentVerificationResponse(List<Object[]> transactionOrderList) {
        logger.info("Mapping Transaction and Order data.");
        OrderInfoDto orderDto = objectMapper.convertValue(transactionOrderList.getFirst()[1], OrderInfoDto.class);
        List<PaymentVerificationDto> transactionsDTOs = transactionOrderList.stream().map(record -> objectMapper.convertValue(record[0], PaymentVerificationDto.class)).collect(Collectors.toList());
        logger.info("Transaction and Order data mapped.");
        return PaymentVerificationResponse.builder()
                .paymentInfo(transactionsDTOs)
                .orderInfo(orderDto)
                .build();
    }
