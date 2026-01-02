@Entity
@Table(name = "GST_REPORT_INFO")
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GstReportInfo extends  AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID", nullable = false, updatable = false, unique = true)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private GstReportType reportType;
    private String monthYear;
    private String name;
    @Column(name = "S3_PATH")
    private String s3Path;
    private String sftpPath;
    private Long totalCount;
    private Long failedCount;
    private Long inprogressCount;
    private Long successCount;
    private String status;
    private String remark;
}


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GstReportInfoDto {
    private UUID id;
    private GstReportType reportType;
    private String monthYear;
    private String name;
    private String status;
    private String s3Path;
    private String sftpPath;
    private Long totalCount;
    private Long failedCount;
    private Long inprogressCount;
    private Long successCount;
    private String remark;
}

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = false))
public interface GstReportInfoMapper {

    GstReportInfoDto mapEntityToDto(GstReportInfo gstReportManagement);
}

but here gstReportInfoMapper.mapEntityToDto(gstReportInfo)  GstReportInfoDto property values are null


     GstReportInfoDto(id=null, reportType=null, monthYear=null, name=null, status=null, s3Path=null, sftpPath=null, totalCount=null, failedCount=null, inprogressCount=null, successCount=null, remark=null)
why it it null
