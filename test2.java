PdxType[dsid=0,typenum=12009724,name=com.epay.admin.entity.cache.MerchantPayModeDownTimeCacheEntity,fields=[id:String:identity:0:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=-1, startTimestamp:String:1:1:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=1, endTimestamp:String:2:2:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=2, errorMessage:String:3:3:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=3, status:String:4:4:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=4, recordStatus:String:5:5:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=5, payModeCode:String:6:6:idx0(relativeOffset)=0:idx1(vlfOffsetIndex)=6, ]]



    public Optional<MerchantPayModeDownTimeCacheEntity> getDowntimeAPIs(String status) {
//        List<MerchantPayModeDownTimeCacheEntity> downtimeAPIs = downtimeAPICacheRepository.findByStatusAndRecordStatus(status);
        Optional<MerchantPayModeDownTimeCacheEntity> pdxInstance = downtimeAPICacheRepository.findById("15");


        if (pdxInstance.isEmpty()) {
            logger.info("Transaction downtime api details found empty in cache and called from database tables.");
//            downtimeAPIs = adminServicesClient.getDowntimeAPIs();
        }
        return pdxInstance;
    }


public MerchantPayModeDownTimeCacheEntity mapPdxInstanceToEntity(PdxInstance instance) {
    MerchantPayModeDownTimeCacheEntity entity = new MerchantPayModeDownTimeCacheEntity();
    entity.setId((String) instance.getField("id"));
    entity.setStartTimestamp((String) instance.getField("startTimestamp"));
    entity.setEndTimestamp((String) instance.getField("endTimestamp"));
    entity.setErrorMessage((String) instance.getField("errorMessage"));
    entity.setStatus((String) instance.getField("status"));
    entity.setRecordStatus((String) instance.getField("recordStatus"));
    entity.setPayModeCode((String) instance.getField("payModeCode"));
    return entity;
}
