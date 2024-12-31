private PaymentOptionsDTO getOtherPaymentOptions(List<MerchantPaymode> merchantPayModeList, Map<String, DownTimeDTO> downTimeDTOMap) {
    // Create lists to hold different types of payment options
    List<InbDTO> otherInbList = new ArrayList<>();
    List<CardDTO> otherCcCardList = new ArrayList<>();
    List<CardDTO> otherDcCardList = new ArrayList<>();
    List<CardDTO> otherPcCardList = new ArrayList<>();

    // Helper method to add CardDTO to the respective list
    Consumer<MerchantPaymode> addCardDTO = merchantPaymode -> {
        CardDTO card = CardDTO.builder()
            .bankName(merchantPaymode.getPayGatewayName())
            .payproctype(merchantPaymode.getPayProc())
            .aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId())
            .payproc(merchantPaymode.getPayProc())
            .build();
        switch (merchantPaymode.getPayModeCode().toUpperCase()) {
            case ApplicationConstants.PAY_MODE_CODE_CREDITCARD -> otherCcCardList.add(card);
            case ApplicationConstants.PAY_MODE_CODE_DEBITCARD -> otherDcCardList.add(card);
            case ApplicationConstants.PAY_MODE_CODE_PREPAIDCARD -> otherPcCardList.add(card);
        }
    };

    // Process MerchantPaymode list
    for (MerchantPaymode merchantPaymode : merchantPayModeList) {
        if (merchantPaymode.getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_NETBANKING)) {
            otherInbList.add(InbDTO.builder()
                .bankId(merchantPaymode.getPayGatewayId())
                .bankName(merchantPaymode.getPayGatewayName())
                .payproctype(merchantPaymode.getPayProc())
                .payproc(merchantPaymode.getPayProc())
                .aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId())
                .build());
        } else {
            addCardDTO.accept(merchantPaymode);
        }
    }

    // Process DownTimeDTO map
    for (DownTimeDTO downtime : downTimeDTOMap.values()) {
        CardDTO downtimeCard = CardDTO.builder().downtime(downtime).build();
        switch (downtime.getPayModeCode().toUpperCase()) {
            case ApplicationConstants.PAY_MODE_CODE_NETBANKING -> 
                otherInbList.add(InbDTO.builder().downtime(downtime).build());
            case ApplicationConstants.PAY_MODE_CODE_CREDITCARD -> 
                otherCcCardList.add(downtimeCard);
            case ApplicationConstants.PAY_MODE_CODE_DEBITCARD -> 
                otherDcCardList.add(downtimeCard);
            case ApplicationConstants.PAY_MODE_CODE_PREPAIDCARD -> 
                otherPcCardList.add(downtimeCard);
        }
    }

    // Create CardsDTO
    CardsDTO otherCardsDTO = CardsDTO.builder()
        .cc(otherCcCardList)
        .dc(otherDcCardList)
        .pc(otherPcCardList)
        .build();

    // Create UpiDTO
    UpiDTO otherUpiDTO = UpiDTO.builder()
        .apps(Arrays.asList(ApplicationConstants.PAY_MODE_DEFAULT_UPI_APPS))
        .vpa(true)
        .drawerIntent(true)
        .build();

    // Build and return PaymentOptionsDTO
    return PaymentOptionsDTO.builder()
        .upi(otherUpiDTO)
        .cards(otherCardsDTO)
        .inb(otherInbList)
        .build();
}
