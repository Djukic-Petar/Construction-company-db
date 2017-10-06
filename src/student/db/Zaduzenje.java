/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "Zaduzenje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Zaduzenje.findAll", query = "SELECT z FROM Zaduzenje z")
    , @NamedQuery(name = "Zaduzenje.findByIdZaduzenje", query = "SELECT z FROM Zaduzenje z WHERE z.idZaduzenje = :idZaduzenje")
    , @NamedQuery(name = "Zaduzenje.findByDatumZaduzenja", query = "SELECT z FROM Zaduzenje z WHERE z.datumZaduzenja = :datumZaduzenja")
    , @NamedQuery(name = "Zaduzenje.findByDatumRazduzenja", query = "SELECT z FROM Zaduzenje z WHERE z.datumRazduzenja = :datumRazduzenja")
    , @NamedQuery(name = "Zaduzenje.findByNapomena", query = "SELECT z FROM Zaduzenje z WHERE z.napomena = :napomena")})
public class Zaduzenje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idZaduzenje")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idZaduzenje;
    @Column(name = "DatumZaduzenja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumZaduzenja;
    @Column(name = "DatumRazduzenja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumRazduzenja;
    @Column(name = "Napomena")
    private String napomena;
    @JoinColumn(name = "idMagacin", referencedColumnName = "idMagacin")
    @ManyToOne(optional = false)
    private Magacin idMagacin;
    @JoinColumn(name = "idRoba", referencedColumnName = "idRoba")
    @ManyToOne(optional = false)
    private Roba idRoba;
    @JoinColumn(name = "idZaposleni", referencedColumnName = "idZaposleni")
    @ManyToOne
    private Zaposleni idZaposleni;

    public Zaduzenje() {
    }

    public Zaduzenje(Integer idZaduzenje) {
        this.idZaduzenje = idZaduzenje;
    }

    public Integer getIdZaduzenje() {
        return idZaduzenje;
    }

    public void setIdZaduzenje(Integer idZaduzenje) {
        this.idZaduzenje = idZaduzenje;
    }

    public Date getDatumZaduzenja() {
        return datumZaduzenja;
    }

    public void setDatumZaduzenja(Date datumZaduzenja) {
        this.datumZaduzenja = datumZaduzenja;
    }

    public Date getDatumRazduzenja() {
        return datumRazduzenja;
    }

    public void setDatumRazduzenja(Date datumRazduzenja) {
        this.datumRazduzenja = datumRazduzenja;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Magacin getIdMagacin() {
        return idMagacin;
    }

    public void setIdMagacin(Magacin idMagacin) {
        this.idMagacin = idMagacin;
    }

    public Roba getIdRoba() {
        return idRoba;
    }

    public void setIdRoba(Roba idRoba) {
        this.idRoba = idRoba;
    }

    public Zaposleni getIdZaposleni() {
        return idZaposleni;
    }

    public void setIdZaposleni(Zaposleni idZaposleni) {
        this.idZaposleni = idZaposleni;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idZaduzenje != null ? idZaduzenje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Zaduzenje)) {
            return false;
        }
        Zaduzenje other = (Zaduzenje) object;
        if ((this.idZaduzenje == null && other.idZaduzenje != null) || (this.idZaduzenje != null && !this.idZaduzenje.equals(other.idZaduzenje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Zaduzenje[ idZaduzenje=" + idZaduzenje + " ]";
    }

    public static Zaduzenje findById(EntityManager em, int idZaduzenje) {
        TypedQuery<Zaduzenje> query = em.createNamedQuery("Zaduzenje.findByIdZaduzenje", Zaduzenje.class);
        query.setParameter("idZaduzenje", idZaduzenje);
        return query.getSingleResult();
    }
}
