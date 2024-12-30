 List<MerchantPaymode> merchantPaymodeList = new ArrayList<>();
        if(merchantPayModeCacheList.isEmpty()) {

            merchantPaymodeList = merchantPayModeDao.getMerchantPaymodeById(mId);
            if (merchantPaymodeList.isEmpty()) {
                return ResponseDto.<MerchantPaymodeDTO>builder().status(Status.FAIL.getValue()).total(0L).count(0L).data(List.of()).errors(List.of(ErrorDto.builder().errorCode(ErrorConstants.NOT_FOUND_ERROR_CODE).errorMessage(MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Mid")).reason(MessageFormat.format(ErrorConstants.NOT_FOUND_ERROR_MESSAGE, "Mid")).build())).build();
            }

            List<MerchantPayModeCacheEntity> merchantCacheEntity = new ArrayList<MerchantPayModeCacheEntity>();
            BeanUtils.copyProperties(merchantPaymodeList, merchantCacheEntity);
//            merchantPaymodeList.stream().map(merchantPaymode -> ob);
//            merchantCacheRepository.save(merchantCacheEntity);

        }
