package com.nagarro.everification.model;

import com.nagarro.everification.payload.request.EventSourcePatchRequest;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "event_source")
public class EventSource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name="business_key")
    private String businessKey;

    @NotBlank
    private String priority;

    @NotBlank
    @Column(name="source_bu")
    private String sourceBu;

    @NotBlank
    @Column(name="dcp_reference")
    private String dcpReference;

    @NotBlank
    @Column(name="account_name")
    private String accountName;

    @NotBlank
    @Column(name="transaction_currency")
    private String transactionCurrency;

    @NotNull
    @Column(name="transaction_amount")
    private Double transactionAmount;

    @NotBlank
    @Column(name="locked_by")
    private String lockedBy;

    @NotBlank
    @Column(name="debit_account_number")
    private String debitAccountNumber;

    @NotBlank
    @Column(name="account_currency")
    private String accountCurrency;

    @NotBlank
    @Column(name="beneficiary_name")
    private String beneficiaryName;

    @NotBlank
    @Column(name="cust_info_mkr")
    private String custInfoMkr;

    @NotBlank
    @Column(name="account_info_mkr")
    private String accountInfoMkr;

    @NotBlank
    @Column(name="outcome")
    private String outcome;

    @NotBlank
    @Column(name="extension")
    private String extension;

    @NotBlank
    @Column(name="contact_person")
    private String contactPerson;

    @NotBlank
    @Column(name="customer_called_on")
    private String customerCalledOn;

    @NotBlank
    @Column(name="comment")
    private String comment;

    @NotBlank
    @Column(name="created_by")
    private String createdBy;

    @Column(name="created_on")
    private Date createdOn;

    @Column(name="updated_on")
    private Timestamp updatedOn;
    private String updatedBy;

    @NotBlank
    @Column(name="status")
    private String status;

    public EventSource() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getSourceBu() {
        return sourceBu;
    }

    public void setSourceBu(String sourceBu) {
        this.sourceBu = sourceBu;
    }

    public String getDcpReference() {
        return dcpReference;
    }

    public void setDcpReference(String dcpReference) {
        this.dcpReference = dcpReference;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getLockedBy() {
        return lockedBy;
    }

    public void setLockedBy(String lockedBy) {
        this.lockedBy = lockedBy;
    }

    public String getDebitAccountNumber() {
        return debitAccountNumber;
    }

    public void setDebitAccountNumber(String debitAccountNumber) {
        this.debitAccountNumber = debitAccountNumber;
    }

    public String getAccountCurrency() {
        return accountCurrency;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getCustInfoMkr() {
        return custInfoMkr;
    }

    public void setCustInfoMkr(String custInfoMkr) {
        this.custInfoMkr = custInfoMkr;
    }

    public String getAccountInfoMkr() {
        return accountInfoMkr;
    }

    public void setAccountInfoMkr(String accountIInfoMkr) {
        this.accountInfoMkr = accountIInfoMkr;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getCustomerCalledOn() {
        return customerCalledOn;
    }

    public void setCustomerCalledOn(String customerCalledOn) {
        this.customerCalledOn = customerCalledOn;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
