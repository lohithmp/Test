private PaymentOptionsDTO getOtherPaymentOptions(List<MerchantPaymode> merchantPayModeList, 
                                                 Map<String, DownTimeDTO> downTimeDTOMap) {
    // Initialize collections for different payment options
    List<InbDTO> otherInbList = new ArrayList<>();
    List<CardDTO> otherCcCardList = new ArrayList<>();
    List<CardDTO> otherDcCardList = new ArrayList<>();
    List<CardDTO> otherPcCardList = new ArrayList<>();

    // Process merchant payment modes
    for (MerchantPaymode merchantPaymode : merchantPayModeList) {
        String payModeCode = merchantPaymode.getPayModeCode();
        DownTimeDTO downTimeDTO = downTimeDTOMap.get(merchantPaymode.getPayModeCode());

        switch (payModeCode.toUpperCase()) {
            case ApplicationConstants.PAY_MODE_CODE_NETBANKING:
                otherInbList.add(buildInbDTO(merchantPaymode, downTimeDTO));
                break;
            case ApplicationConstants.PAY_MODE_CODE_CREDITCARD:
                otherCcCardList.add(buildCardDTO(merchantPaymode, downTimeDTO));
                break;
            case ApplicationConstants.PAY_MODE_CODE_DEBITCARD:
                otherDcCardList.add(buildCardDTO(merchantPaymode, downTimeDTO));
                break;
            case ApplicationConstants.PAY_MODE_CODE_PREPAIDCARD:
                otherPcCardList.add(buildCardDTO(merchantPaymode, downTimeDTO));
                break;
            default:
                // Handle unexpected pay mode codes if necessary
                break;
        }
    }

    // Build nested DTOs
    CardsDTO otherCardsDTO = CardsDTO.builder()
        .cc(otherCcCardList)
        .dc(otherDcCardList)
        .pc(otherPcCardList)
        .build();

    UpiDTO otherUpiDTO = UpiDTO.builder()
        .apps(Arrays.asList(ApplicationConstants.PAY_MODE_DEFAULT_UPI_APPS))
        .vpa(true)
        .drawerIntent(true)
        .build();

    // Build and return the PaymentOptionsDTO
    return PaymentOptionsDTO.builder()
        .upi(otherUpiDTO)
        .cards(otherCardsDTO)
        .inb(otherInbList)
        .build();
}

private InbDTO buildInbDTO(MerchantPaymode merchantPaymode, DownTimeDTO downTimeDTO) {
    return InbDTO.builder()
        .bankId(merchantPaymode.getPayGatewayId())
        .bankName(merchantPaymode.getPayGatewayName())
        .payproctype(merchantPaymode.getPayProc())
        .payproc(merchantPaymode.getPayProc())
        .aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId())
        .downtime(downTimeDTO) // Include downtime information if available
        .build();
}

private CardDTO buildCardDTO(MerchantPaymode merchantPaymode, DownTimeDTO downTimeDTO) {
    return CardDTO.builder()
        .bankName(merchantPaymode.getPayGatewayName())
        .payproctype(merchantPaymode.getPayProc())
        .aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId())
        .payproc(merchantPaymode.getPayProc())
        .downtime(downTimeDTO) // Include downtime information if available
        .build();
}
