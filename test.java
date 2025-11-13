--liquibase formatted sql
--changeset Lohith:1

CREATE TABLE BUSINESS_INFO (
    ID RAW(16) PRIMARY KEY NOT NULL,
    BUSINESS_PAN VARCHAR2(255),
    BUSINESS_GSTIN VARCHAR2(255),
    BUSINESS_TAN VARCHAR2(255),
    LEGAL_NAME VARCHAR2(255) NOT NULL,
    BRAND_NAME VARCHAR2(255),
    CONSTITUTION VARCHAR2(255),
    WEBSITE_URL VARCHAR2(255),
    DATE_OF_ESTABLISHMENT_OR_INCORPORATION VARCHAR2(255),
    CURRENT_ANNUAL_BUSINESS_TURNOVER VARCHAR2(255),
    REGISTERED_OFFICE_ADDRESS CLOB NOT NULL,
    COUNTRY VARCHAR2(255) NOT NULL,
    STATE VARCHAR2(255) NOT NULL,
    CITY_OR_AREA VARCHAR2(255) NOT NULL,
    PINCODE VARCHAR2(255) NOT NULL,
    NATURE_OF_BUSINESS VARCHAR2(255),
    COMPANY_PROFILE VARCHAR2(255),
    PRODUCT_OR_SERVICE_INTENDED_TO_SELL VARCHAR2(255),
    RECOVERY_OF_TRANSACTION_CHARGES VARCHAR2(255),
    CREATED_BY          VARCHAR2(100) NOT NULL,
    CREATED_AT          NUMBER NOT NULL,
    UPDATED_BY          VARCHAR2(100) NOT NULL,
    UPDATED_AT          NUMBER NOT NULL,
    PARTITION_DATE    DATE GENERATED ALWAYS AS (TO_DATE('1970-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS')+CREATED_AT/1000/86400) VIRTUAL)
    PARTITION BY RANGE (PARTITION_DATE) INTERVAL (NUMTOYMINTERVAL(1, 'MONTH')) (PARTITION P_START VALUES LESS THAN (TO_DATE(' 2020-01-01 00:00:00', 'SYYYY-MM-DD HH24:MI:SS', 'NLS_CALENDAR=GREGORIAN')));



--CREATE Sequence
CREATE SEQUENCE custom_rev_id_seq START WITH 1 INCREMENT BY 1 NOCACHE;

--CREATE table AUDIT_REVISION_INFO
CREATE TABLE AUDIT_REVISION_INFO
  (
    AUDIT_REVISION_ID   NUMBER(19,0) PRIMARY KEY ,
    AUDIT_REVISION_TIME NUMBER NOT NULL ,
    ENTITY_CLASS_NAME   VARCHAR2(200 BYTE) NOT NULL
  ) ;


--CREATE table BUSINESS_INFO_AUDIT
  CREATE TABLE BUSINESS_INFO_AUDIT
  (
    ID RAW(16) DEFAULT SYS_GUID() NOT NULL ENABLE,
    REV NUMBER(19,0),
    REVTYPE NUMBER(3,0),
    BUSINESS_INFO_ID RAW(16),
    BUSINESS_PAN VARCHAR2(255),
    BUSINESS_GSTIN VARCHAR2(255),
    BUSINESS_TAN VARCHAR2(255),
    LEGAL_NAME VARCHAR2(255),
    BRAND_NAME VARCHAR2(255),
    CONSTITUTION VARCHAR2(255),
    WEBSITE_URL VARCHAR2(255),
    DATE_OF_ESTABLISHMENT_OR_INCORPORATION VARCHAR2(255),
    CURRENT_ANNUAL_BUSINESS_TURNOVER VARCHAR2(255),
    REGISTERED_OFFICE_ADDRESS CLOB,
    COUNTRY VARCHAR2(255),
    STATE VARCHAR2(255) ,
    CITY_OR_AREA VARCHAR2(255) ,
    PINCODE VARCHAR2(255) ,
    NATURE_OF_BUSINESS VARCHAR2(255),
    COMPANY_PROFILE VARCHAR2(255),
    PRODUCT_OR_SERVICE_INTENDED_TO_SELL VARCHAR2(255),
    RECOVERY_OF_TRANSACTION_CHARGES VARCHAR2(255),
    CREATED_AT      NUMBER NOT NULL ENABLE,
    UPDATED_AT      NUMBER,
    CREATED_BY        VARCHAR2(100 BYTE) NOT NULL ENABLE,
    UPDATED_BY        VARCHAR2(100 BYTE),
    PARTITION_DATE    DATE GENERATED ALWAYS AS (TO_DATE('1970-01-01 00:00:00','SYYYY-MM-DD HH24:MI:SS')+CREATED_AT/1000/86400) VIRTUAL )
    PARTITION BY RANGE(PARTITION_DATE) INTERVAL (NUMTOYMINTERVAL(1,'MONTH'))(PARTITION P_START VALUES LESS THAN (TO_DATE('01-01-2020','DD-MM-YYYY'))
  ) ;



@RequiredArgsConstructor
@Component
public class BusinessInfoDao {

    private final LoggerUtility log = LoggerFactoryUtility.getLogger(this.getClass());
    private final BusinessInfoAuditRepository businessInfoAuditRepository;
    private final BusinessInfoRepository businessInfoRepository;

    @Transactional
    public void saveBusinessInfoAudit(BusinessInfoAudit businessInfoAudit) {
        log.info("BusinessInfoAudit createdAt timestamp: {}", businessInfoAudit.getCreatedAt());

        log.info("Saving business info audit");

        BusinessInfo newBusinessInfo = BusinessInfo.builder()
                .id(UUID.randomUUID()) // Assign ID manually (as per previous guidance)
                .legalName("Mandatory Legal Name") // <-- Must provide a value
                .registeredOfficeAddress("Mandatory Address") // <-- Must provide a value
                .country("Mandatory Country") // <-- Must provide a value
                .state("Mandatory State") // <-- Must provide a value
                .cityOrArea("Mandatory City") // <-- Must provide a value
                .pincode("123456") // <-- Must provide a value
                .createdAt(System.currentTimeMillis())
                // Add other required fields if you have any more constraints
                .build();
        businessInfoRepository.save(newBusinessInfo);
        businessInfoAuditRepository.save(businessInfoAudit);
    }
}



@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "BUSINESS_INFO_AUDIT")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class BusinessInfoAudit extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "BUSINESS_INFO_ID")
    private UUID businessInfoId;
    @Column(name = "BUSINESS_PAN")
    private String businessPan;
    @Column(name = "BUSINESS_GSTIN")
    private String businessGstin;
    @Column(name = "BUSINESS_TAN")
    private String businessTan;
    @Column(name = "LEGAL_NAME")
    private String legalName;
    @Column(name = "BRAND_NAME") // Explicitly map this field
    private String brandName;
    @Column(name = "CONSTITUTION")
    private String constitution;
    @Column(name = "WEBSITE_URL")
    private String websiteUrl;
    @Column(name = "DATE_OF_ESTABLISHMENT_OR_INCORPORATION")
    private String dateOfEstablishmentOrIncorporation;
    @Column(name = "CURRENT_ANNUAL_BUSINESS_TURNOVER")
    private String currentAnnualBusinessTurnover;
    @Column(name = "REGISTERED_OFFICE_ADDRESS")
    private String registeredOfficeAddress;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "STATE")
    private String state;
    @Column(name = "CITY_OR_AREA")
    private String cityOrArea;
    @Column(name = "PINCODE")
    private String pincode;
    @Column(name = "NATURE_OF_BUSINESS")
    private String natureOfBusiness;
    @Column(name = "COMPANY_PROFILE")
    private String companyProfile;
    @Column(name = "PRODUCT_OR_SERVICE_INTENDED_TO_SELL")
    private String productOrServiceIntendedToSell;
    @Column(name = "RECOVERY_OF_TRANSACTION_CHARGES")
    private String recoveryOfTransactionCharges;

    @PrePersist
    @PreUpdate
    public void setEntityNameForRevision() {
        AuditRevisionListener.setEntityName(this.getClass().getSimpleName());
    }
}



@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Setter
@Getter
@Audited
@AuditTable(value = "BUSINESS_INFO_AUDIT")
@Table(name = "BUSINESS_INFO")
public class BusinessInfo extends AuditEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String businessPan;
    private String businessGstin;
    private String businessTan;
    private String legalName;
    private String brandName;
    private String constitution;
    private String websiteUrl;
    private String dateOfEstablishmentOrIncorporation;
    private String currentAnnualBusinessTurnover;
    private String registeredOfficeAddress;
    private String country;
    private String state;
    private String cityOrArea;
    private String pincode;
    private String natureOfBusiness;
    private String companyProfile;
    private String productOrServiceIntendedToSell;
    private String recoveryOfTransactionCharges;

    @PrePersist
    @PreUpdate
    public void setEntityNameForRevision() {
        AuditRevisionListener.setEntityName(this.getClass().getSimpleName());
    }
}

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntity extends AuditEntityByDate implements Serializable {

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String updatedBy;
}

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEntityByDate implements Serializable {

    @CreatedDate
    private Long createdAt;

    @LastModifiedDate
    private Long updatedAt;
}
