private PaymentOptionsDTO getOtherPaymentOptions(List<MerchantPaymode> merchantPayModeList, Map<String, DownTimeDTO> downTimeDTOMap) {
    // Initializing the lists for different payment modes
    List<InbDTO> otherInbList = new ArrayList<>();
    List<CardDTO> otherCcCardList = new ArrayList<>();
    List<CardDTO> otherDcCardList = new ArrayList<>();
    List<CardDTO> otherPcCardList = new ArrayList<>();

    // Lambda to map payModeCode to its corresponding list
    Map<String, List<CardDTO>> payModeToCardListMap = Map.of(
            ApplicationConstants.PAY_MODE_CODE_CREDITCARD, otherCcCardList,
            ApplicationConstants.PAY_MODE_CODE_DEBITCARD, otherDcCardList,
            ApplicationConstants.PAY_MODE_CODE_PREPAIDCARD, otherPcCardList
    );

    // Process merchantPayModeList
    for (MerchantPaymode merchantPaymode : merchantPayModeList) {
        String payModeCode = merchantPaymode.getPayModeCode();

        if (payModeCode.equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_NETBANKING)) {
            otherInbList.add(InbDTO.builder()
                    .bankId(merchantPaymode.getPayGatewayId())
                    .bankName(merchantPaymode.getPayGatewayName())
                    .payproctype(merchantPaymode.getPayProc())
                    .payproc(merchantPaymode.getPayProc())
                    .aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId())
                    .build());
        } else {
            List<CardDTO> cardList = payModeToCardListMap.get(payModeCode.toUpperCase());
            if (cardList != null) {
                cardList.add(CardDTO.builder()
                        .bankName(merchantPaymode.getPayGatewayName())
                        .payproctype(merchantPaymode.getPayProc())
                        .aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId())
                        .payproc(merchantPaymode.getPayProc())
                        .build());
            }
        }
    }

    // Process downTimeDTOMap
    downTimeDTOMap.values().forEach(downTimeDTO -> {
        String payModeCode = downTimeDTO.getPayModeCode();
        if (payModeCode.equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_NETBANKING)) {
            otherInbList.add(InbDTO.builder().downtime(downTimeDTO).build());
        } else {
            List<CardDTO> cardList = payModeToCardListMap.get(payModeCode.toUpperCase());
            if (cardList != null) {
                cardList.add(CardDTO.builder().downtime(downTimeDTO).build());
            }
        }
    });

    // Construct CardsDTO
    CardsDTO otherCardsDTO = CardsDTO.builder()
            .cc(otherCcCardList)
            .dc(otherDcCardList)
            .pc(otherPcCardList)
            .build();

    // Construct UpiDTO
    UpiDTO otherUpiDTO = UpiDTO.builder()
            .apps(Arrays.asList(ApplicationConstants.PAY_MODE_DEFAULT_UPI_APPS))
            .vpa(true)
            .drawerIntent(true)
            .build();

    // Construct and return PaymentOptionsDTO
    return PaymentOptionsDTO.builder()
            .upi(otherUpiDTO)
            .cards(otherCardsDTO)
            .inb(otherInbList)
            .build();
        }
