 @KafkaListener(topics = "Admin.LOHITH_M.AGGMERCHGTWFEESTRUCTURE", groupId = "bankbranches-group")
    public void processAggMerchGtwFeeStructure(Message<String> message) {
        try {
            logger.info("Received message: {}", message.getPayload());
            JsonNode event = objectMapper.readTree(message.getPayload());
            String operation = event.get("op").asText();
            JsonNode after = event.get("after");
            JsonNode before = event.get("before");

            if (("c".equals(operation) || "u".equals(operation) || "r".equals(operation)) && after != null) {
                AggMerchantGtwFeeStructureDto merchantGtwFeeStructureDto = objectMapper.treeToValue(after, AggMerchantGtwFeeStructureDto.class);
                Optional<MerchantPricing> merchantPricing = merchantPricingRepository.getPricingByMerchantGwtFeeId(merchantGtwFeeStructureDto.getMerchantGtwFeeId(),
                        merchantGtwFeeStructureDto.getMId(),
                        merchantGtwFeeStructureDto.getPayProcType(),
                        merchantGtwFeeStructureDto.getPayGtwId());

                if (merchantPricing.isEmpty()) {
                    logger.info("Incoming message AGGMERCHGTWFEESTRUCTURE record is not found in View, PK-MERCHGTWFEEID: {}", merchantGtwFeeStructureDto.getMerchantGtwFeeId());
                    return;
                }
                MerchantPricingEntity merchantPricingCacheEntity = new MerchantPricingEntity();
                BeanUtils.copyProperties(merchantPricing.get(), merchantPricingCacheEntity);

                Optional<MerchantPricingEntity> merchantPricingCache = merchantPricingService.getCacheMerchantPricing(merchantGtwFeeStructureDto.getMerchantGtwFeeId());
                if (merchantPricingCache.isEmpty()) {
                    logger.info("Incoming message inserted MerchantPricing record is saved cache, PK-MERCHGTWFEEID: {}", merchantGtwFeeStructureDto.getMerchantGtwFeeId());
                    merchantPricingService.saveMerchantPricing(merchantPricingCacheEntity);
                    return;
                }
                merchantPricingService.saveMerchantPricing(merchantPricingCacheEntity);
            } else if ("d".equals(operation)) {
                AggMerchantGtwFeeStructureDto aggMerchantGtwFeeStructureDto = objectMapper.treeToValue(before, AggMerchantGtwFeeStructureDto.class);
                Optional<MerchantPricingEntity> merchantPricingCache = merchantPricingService.getCacheMerchantPricing(aggMerchantGtwFeeStructureDto.getMerchantGtwFeeId());
                if (merchantPricingCache.isPresent()) {
                    logger.info("AGGMERCHGTWFEESTRUCTURE record is deleting from Cache, PK-MERCHGTWFEEID: {}", aggMerchantGtwFeeStructureDto.getMerchantGtwFeeId());
                    merchantPricingService.deleteCacheMerchantPricing(aggMerchantGtwFeeStructureDto.getMerchantGtwFeeId());
                } else {
                    logger.info("AGGMERCHGTWFEESTRUCTURE record is not found in Cache, PK-MERCHGTWFEEID: {}", aggMerchantGtwFeeStructureDto.getMerchantGtwFeeId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
