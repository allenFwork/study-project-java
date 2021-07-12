package cn.com.epicc.ms.cpi.entity.picc_cpi;

import java.math.BigDecimal;
import java.util.Date;

public class AlipayPolicy {
    private String orderId;

    private String outOrderId;

    private String summaryOrderId;

    private String prodNo;

    private String outProdNo;

    private BigDecimal status;

    private BigDecimal policyType;

    private String insuredTime;

    private String issueTime;

    private String surrenderTime;

    private String effectStartTime;

    private String effectEndTime;

    private BigDecimal sumInsured;

    private BigDecimal feeRate;

    private BigDecimal premium;

    private BigDecimal actualPremium;

    private BigDecimal applyNum;

    private String bizData;

    private BigDecimal surrenderFee;

    private String prodType;

    private Date inputdate;

    private Long personPhonenum;

    private String dispatchflag;

    private String policyno;

    private String gatherno;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId == null ? null : outOrderId.trim();
    }

    public String getSummaryOrderId() {
        return summaryOrderId;
    }

    public void setSummaryOrderId(String summaryOrderId) {
        this.summaryOrderId = summaryOrderId == null ? null : summaryOrderId.trim();
    }

    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo == null ? null : prodNo.trim();
    }

    public String getOutProdNo() {
        return outProdNo;
    }

    public void setOutProdNo(String outProdNo) {
        this.outProdNo = outProdNo == null ? null : outProdNo.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getPolicyType() {
        return policyType;
    }

    public void setPolicyType(BigDecimal policyType) {
        this.policyType = policyType;
    }

    public String getInsuredTime() {
        return insuredTime;
    }

    public void setInsuredTime(String insuredTime) {
        this.insuredTime = insuredTime == null ? null : insuredTime.trim();
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime == null ? null : issueTime.trim();
    }

    public String getSurrenderTime() {
        return surrenderTime;
    }

    public void setSurrenderTime(String surrenderTime) {
        this.surrenderTime = surrenderTime == null ? null : surrenderTime.trim();
    }

    public String getEffectStartTime() {
        return effectStartTime;
    }

    public void setEffectStartTime(String effectStartTime) {
        this.effectStartTime = effectStartTime == null ? null : effectStartTime.trim();
    }

    public String getEffectEndTime() {
        return effectEndTime;
    }

    public void setEffectEndTime(String effectEndTime) {
        this.effectEndTime = effectEndTime == null ? null : effectEndTime.trim();
    }

    public BigDecimal getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(BigDecimal sumInsured) {
        this.sumInsured = sumInsured;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getActualPremium() {
        return actualPremium;
    }

    public void setActualPremium(BigDecimal actualPremium) {
        this.actualPremium = actualPremium;
    }

    public BigDecimal getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(BigDecimal applyNum) {
        this.applyNum = applyNum;
    }

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData == null ? null : bizData.trim();
    }

    public BigDecimal getSurrenderFee() {
        return surrenderFee;
    }

    public void setSurrenderFee(BigDecimal surrenderFee) {
        this.surrenderFee = surrenderFee;
    }

    public String getProdType() {
        return prodType;
    }

    public void setProdType(String prodType) {
        this.prodType = prodType == null ? null : prodType.trim();
    }

    public Date getInputdate() {
        return inputdate;
    }

    public void setInputdate(Date inputdate) {
        this.inputdate = inputdate;
    }

    public Long getPersonPhonenum() {
        return personPhonenum;
    }

    public void setPersonPhonenum(Long personPhonenum) {
        this.personPhonenum = personPhonenum;
    }

    public String getDispatchflag() {
        return dispatchflag;
    }

    public void setDispatchflag(String dispatchflag) {
        this.dispatchflag = dispatchflag == null ? null : dispatchflag.trim();
    }

    public String getPolicyno() {
        return policyno;
    }

    public void setPolicyno(String policyno) {
        this.policyno = policyno == null ? null : policyno.trim();
    }

    public String getGatherno() {
        return gatherno;
    }

    public void setGatherno(String gatherno) {
        this.gatherno = gatherno == null ? null : gatherno.trim();
    }
}