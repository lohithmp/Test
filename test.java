public void processBulkRefund(String bulkId) {

        logger.info("ProcessBulkRefund received for bulkId: {}", bulkId);

        //Step-1: Get bulk refund booking
        BulkRefundBooking bulkRefundBooking = refundDao.findByBulkId(bulkId);
        logger.info("Got bulkRefundBooking: {} for bulkId: {}", bulkRefundBooking , bulkId);

        //Step-2: Update processing status
        updateProcessingStatus(bulkRefundBooking);

        //Step-3: Read CSV file from s3 service
        List<String[]> refundData = readCsvFile(bulkRefundBooking.getFilePath());
        logger.info("Got csvFile size : {} for bulkId: {}", refundData.size() , bulkId);

        //Step-4: Validate Headers
        String headerError = refundValidator.validateBulkRefundHeader(refundData, bulkRefundBooking.getMerchantId());
        logger.info("headerError : {} for bulkId: {}", headerError , bulkId);

        //Step-5: If headers are valid then read and process refund from csv file row by row
        //With Kafka
        if (StringUtils.isEmpty(headerError)) {
            logger.info("Valid headers, headerError : {} for bulkId: {}", headerError , bulkId);
            processingRefundBooking(bulkId, refundData, bulkRefundBooking);

        } else {
            logger.info("Invalid headers, headerError : {} for bulkId: {}", headerError , bulkId);
            //Step-6: Mark this csv file as failed
            buildBulkRefundBookingWithError(bulkRefundBooking, headerError);
            //Step-7: Update bulk refund status for current bulk id
            refundDao.saveBulkRefundBooking(bulkRefundBooking);
        }
    }


public String validateBulkRefundHeader(List<String[]> csvFile, String mId) {

        logger.info("Validate bulk refund header for mId: {}", mId);

        if (ObjectUtils.isEmpty(csvFile) || ObjectUtils.isEmpty(csvFile.getFirst())) {
            logger.debug("Valid file for mId: {}", mId);
            return "Invalid file, headers not available";
        }

        String[] requiredHeaders = BULK_REFUND_HEADERS.split(",");

        for (int i = 0; i < requiredHeaders.length; i++) {

            if (i >= csvFile.getFirst().length || !csvFile.getFirst()[i].equalsIgnoreCase(requiredHeaders[i])) {

                logger.debug("Invalid file for mId: {}, error: {} ", mId, requiredHeaders[i] + " not found in " + (i + 1) + " column.");
                return requiredHeaders[i] + " not found in " + (i + 1) + " column.";
            }

        }

        return null;

    }


validateBulkRefundHeader is giving null but i want string
