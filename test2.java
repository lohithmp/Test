    private String encryptPaymentVerificationResponse(PaymentVerificationResponse paymentVerificationResponse, MerchantDto merchantDto) {
        try {
            return encryptionDecryptionUtil.encryptRequest(objectMapper.writeValueAsString(paymentVerificationResponse), merchantDto);
        } catch (JsonProcessingException e) {
            logger.error("Error in encryptPaymentVerificationData ", e);
            throw new TransactionException(ErrorConstants.INVALID_ERROR_CODE, MessageFormat.format(ErrorConstants.INVALID_ERROR_MESSAGE, "paymentVerificationResponse","Error in encrypting pay"));
        }
    }
