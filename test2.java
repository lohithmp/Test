package com.epay.admin.entity.cache;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.Objects;

//@Data
@Region("Refresh_DownTime_PayMode")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPayModeDownTimeSaveEntity implements Serializable {

    @Id
    @Column(name = "paygtwid")
    private String gatewayId;

    @Column(name = "downtimestartdatetime")
    private String startTimestamp;

    @Column(name = "downtimeenddatetime")
    private String endTimestamp;

    @Column(name = "remarks")
    private String errorMessage;

    @Column(name = "status")
    private String status;

    @Column(name = "recordstatus")
    private String recordStatus;

    @Column(name = "paymodecode")
    private String payModeCode;

    @Override
    public int hashCode() {
        return Objects.hash(gatewayId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantPayModeDownTimeSaveEntity that = (MerchantPayModeDownTimeSaveEntity) o;
        return Objects.equals(gatewayId, that.gatewayId);
    }

    @Override
    public String toString() {
        return "MerchantPayModeDownTimeSaveEntity{" +
                "gatewayId='" + gatewayId + '\'' +
                ", hashCode=" + this.hashCode() +  // Include hashCode in output
                '}';
    }

}






