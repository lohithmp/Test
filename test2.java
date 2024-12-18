 @Test
    public void getPaymentStatusVerificationValidationFailure() {
        try(MockedStatic<EPayIdentityUtil> epayMock = mockStatic(EPayIdentityUtil.class)) {
            EPayPrincipal ePayPrincipal = new EPayPrincipal();
            ePayPrincipal.setMid("123");

            epayMock.when(EPayIdentityUtil::getUserPrincipal).thenReturn(ePayPrincipal);
            when(orderDao.getActiveMerchantByMID(ePayPrincipal.getMid())).thenReturn(Optional.ofNullable(buildMerchantData()));

            PaymentVerificationRequest request = new PaymentVerificationRequest();
            request.setOrderRefNumber("");
            request.setSbiOrderRefNumber("");
            request.setAtrnNumber("");
            request.setOrderAmount(new BigDecimal("100.0"));

            List<ErrorDto> errorDtoList = new ArrayList<>();
            errorDtoList.add(ErrorDto.builder().errorCode(ErrorConstants.MANDATORY_FOUND_ERROR_CODE).errorMessage(MessageFormat.format(ErrorConstants.MANDATORY_ERROR_MESSAGE, "Order Reference Number")).build());

            doThrow(new ValidationException(errorDtoList)).when(validator).validatePaymentStatusRequest(request,buildOrder().getMID());

            ValidationException expection = assertThrows(ValidationException.class,() -> {
                paymentVerificationService.getPaymentStatusVerification(request);
            });
            assertEquals("Order Reference Number is mandatory.", expection.getErrorMessage());
        }
    }
