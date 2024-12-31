    private PaymentOptionsDTO getPreferredPaymentOption(Merchant merchantDetailsOptional, Map<String, DownTimeDTO> downTimeDTOMap) {
        List<InbDTO> prefInbList = new ArrayList<>();
        List<CardDTO> prefCcCardList = new ArrayList<>();
        List<CardDTO> prefDcCardList = new ArrayList<>();
        List<CardDTO> prefPcCardList = new ArrayList<>();

        for (Map.Entry<String, DownTimeDTO> downTimeDTOEntry : downTimeDTOMap.entrySet() ) {
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_NETBANKING)) {
                prefInbList.add(InbDTO.builder().bankName(merchantDetailsOptional.getPreferredBank()).bankId("").payproc("").aggregatorGatewayMapId("").downtime(downTimeDTOEntry.getValue()).build());
                continue;
            }
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_CREDITCARD)) {
                prefCcCardList.add(CardDTO.builder().bankName(merchantDetailsOptional.getPreferredBank()).payproctype("").aggregatorGatewayMapId("").payproc("").downtime(downTimeDTOEntry.getValue()).build());
            }
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_DEBITCARD)) {
                prefDcCardList.add(CardDTO.builder().bankName(merchantDetailsOptional.getPreferredBank()).payproctype("").aggregatorGatewayMapId("").payproc("").downtime(downTimeDTOEntry.getValue()).build());
            }
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_PREPAIDCARD)) {
                prefPcCardList.add(CardDTO.builder().bankName(merchantDetailsOptional.getPreferredBank()).payproctype("").aggregatorGatewayMapId("").payproc("").downtime(downTimeDTOEntry.getValue()).build());
            }
        }
        
        CardsDTO prefCardsDTO = CardsDTO.builder().cc(prefCcCardList).dc(prefDcCardList).pc(prefPcCardList).build();

        UpiDTO prefUpiDTO = UpiDTO.builder().apps(List.of()).vpa(false).drawerIntent(false).build();

        return PaymentOptionsDTO.builder().upi(prefUpiDTO).cards(prefCardsDTO).inb(prefInbList).build();
    }

    private PaymentOptionsDTO getOtherPaymentOptions(List<MerchantPaymode> merchantPayModeList, Map<String, DownTimeDTO> downTimeDTOMap) {
        List<InbDTO> otherInbList = new ArrayList<>();
        List<CardDTO> otherCcCardList = new ArrayList<>();
        List<CardDTO> otherDcCardList = new ArrayList<>();
        List<CardDTO> otherPcPardList = new ArrayList<>();

        for(MerchantPaymode merchantPaymode : merchantPayModeList){

            String payModeCode = merchantPaymode.getPayModeCode();

            if(payModeCode.equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_NETBANKING)){
                otherInbList.add(InbDTO.builder().bankId(merchantPaymode.getPayGatewayId()).bankName(merchantPaymode.getPayGatewayName()).payproctype(merchantPaymode.getPayProc()).payproc(merchantPaymode.getPayProc()).aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId()).build());
                continue;
            }

            if(payModeCode.equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_CREDITCARD)){
                otherCcCardList.add(CardDTO.builder().bankName(merchantPaymode.getPayGatewayName()).payproctype(merchantPaymode.getPayProc()).aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId()).payproc(merchantPaymode.getPayProc()).build());
                continue;
            }

            if(payModeCode.equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_DEBITCARD)){
                otherDcCardList.add(CardDTO.builder().bankName(merchantPaymode.getPayGatewayName()).payproctype(merchantPaymode.getPayProc()).aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId()).payproc(merchantPaymode.getPayProc()).build());
                continue;
            }

            if(payModeCode.equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_PREPAIDCARD)){
                otherPcPardList.add(CardDTO.builder().bankName(merchantPaymode.getPayGatewayName()).payproctype(merchantPaymode.getPayProc()).aggregatorGatewayMapId(merchantPaymode.getAggregatorGatewayMapId()).payproc(merchantPaymode.getPayProc()).build());
            }

        }

        for (Map.Entry<String, DownTimeDTO> downTimeDTOEntry : downTimeDTOMap.entrySet() ) {
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_NETBANKING)) {
                otherInbList.add(InbDTO.builder().downtime(downTimeDTOEntry.getValue()).build());
                continue;
            }
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_CREDITCARD)) {
                otherCcCardList.add(CardDTO.builder().downtime(downTimeDTOEntry.getValue()).build());
            }
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_DEBITCARD)) {
                otherDcCardList.add(CardDTO.builder().downtime(downTimeDTOEntry.getValue()).build());
            }
            if(downTimeDTOEntry.getValue().getPayModeCode().equalsIgnoreCase(ApplicationConstants.PAY_MODE_CODE_PREPAIDCARD)) {
                otherPcPardList.add(CardDTO.builder().downtime(downTimeDTOEntry.getValue()).build());
            }
        }
        

        CardsDTO otherCardsDTO = CardsDTO.builder().cc(otherCcCardList).dc(otherDcCardList).pc(otherPcPardList).build();

        UpiDTO otherUpiDTO = UpiDTO.builder().apps(Arrays.asList(ApplicationConstants.PAY_MODE_DEFAULT_UPI_APPS)).vpa(true).drawerIntent(true).build();

        return PaymentOptionsDTO.builder().upi(otherUpiDTO).cards(otherCardsDTO).inb(otherInbList).build();
    }
