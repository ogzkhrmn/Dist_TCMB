package com.bank.tcmb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "tcmb")
public class TCMBEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "from_tckn", nullable = false)
    private String fromTckn;

    @Column(name = "to_tckn", nullable = false)
    private String to_tckn;

    @Column(name = "aciklama", nullable = false)
    private String aciklama;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    public String getFromTckn() {
        return fromTckn;
    }

    public void setFromTckn(String fromTckn) {
        this.fromTckn = fromTckn;
    }

    public String getTo_tckn() {
        return to_tckn;
    }

    public void setTo_tckn(String to_tckn) {
        this.to_tckn = to_tckn;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
