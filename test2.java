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





------------


package com.epay.admin.entity.cache;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.Objects;

@Region("Refresh_DownTime_PayMode")  // GemFire Region
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)  // Lombok generates toString() only for included fields
public class MerchantPayModeDownTimeSaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "paygtwid", nullable = false)
    @ToString.Include  // Include in toString()
    private String gatewayId;

    @Column(name = "downtimestartdatetime")
    @ToString.Include
    private String startTimestamp;

    @Column(name = "downtimeenddatetime")
    @ToString.Include
    private String endTimestamp;

    @Column(name = "remarks")
    private String errorMessage;

    @Column(name = "status")
    @ToString.Include
    private String status;

    @Column(name = "recordstatus")
    private String recordStatus;

    @Column(name = "paymodecode")
    private String payModeCode;

    // Overriding equals() based on gatewayId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
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





------------


package com.epay.admin.entity.cache;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.Objects;

@Region("Refresh_DownTime_PayMode")  // GemFire Region
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)  // Lombok generates toString() only for included fields
public class MerchantPayModeDownTimeSaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "paygtwid", nullable = false)
    @ToString.Include  // Include in toString()
    private String gatewayId;

    @Column(name = "downtimestartdatetime")
    @ToString.Include
    private String startTimestamp;

    @Column(name = "downtimeenddatetime")
    @ToString.Include
    private String endTimestamp;

    @Column(name = "remarks")
    private String errorMessage;

    @Column(name = "status")
    @ToString.Include
    private String status;

    @Column(name = "recordstatus")
    private String recordStatus;

    @Column(name = "paymodecode")
    private String payModeCode;

    // Overriding equals() based on gatewayId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantPayModeDownTimeSaveEntity that = (MerchantPayModeDownTimeSaveEntity) o;
        return Objects.equals(gatewayId, that.gatewayId);
    }

    // Overriding hashCode() based on gatewayId
    @Override
    public int hashCode() {
        return gatewayId != null ? Objects.hash(gatewayId) : 0;
    }

    // Manually add hashCode() to Lombok-generated toString()
    @ToString.Include(name = "hashCode")
    public int getHashCodeForToString() {
        return this.hashCode();
    }
}
    
      MerchantPayModeDownTimeSaveEntity that = (MerchantPayModeDownTimeSaveEntity) o;
        return Objects.equals(gatewayId, that.gatewayId);
    }

    // Overriding hashCode() based on gatewayId
    @Override
    public int hashCode() {
        return gatewayId != null ? Objects.hash(gatewayId) : 0;
    }

    // Manually add hashCode() to Lombok-generated toString()
    @ToString.Include(name = "hashCode")
    public int getHashCodeForToString() {
        return this.hashCode();
    }








    _________00000000000000///000000000

package com.epay.admin.entity.cache;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.gemfire.mapping.annotation.Region;

import java.io.Serializable;
import java.util.Objects;

@Region("Refresh_DownTime_PayMode")  // GemFire Region
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantPayModeDownTimeSaveEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "paygtwid", nullable = false)
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

    // Overriding equals() based on gatewayId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MerchantPayModeDownTimeSaveEntity that = (MerchantPayModeDownTimeSaveEntity) o;
        return Objects.equals(gatewayId, that.gatewayId);
    }

    // Overriding hashCode() based on gatewayId
    @Override
    public int hashCode() {
        return gatewayId != null ? Objects.hash(gatewayId) : 0;
    }

    // Manually writing the toString method to include all fields and hashCode
    @Override
    public String toString() {
        return "MerchantPayModeDownTimeSaveEntity{" +
                "gatewayId='" + gatewayId + '\'' +
                ", startTimestamp='" + startTimestamp + '\'' +
                ", endTimestamp='" + endTimestamp + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", status='" + status + '\'' +
                ", recordStatus='" + recordStatus + '\'' +
                ", payModeCode='" + payModeCode + '\'' +
                ", hashCode=" + this.hashCode() +  // Including hashCode in the toString
                '}';
    }
}
    
    
