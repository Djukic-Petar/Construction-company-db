/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
@Table(name = "Radi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Radi.findAll", query = "SELECT r FROM Radi r")
    , @NamedQuery(name = "Radi.findByIdRadi", query = "SELECT r FROM Radi r WHERE r.idRadi = :idRadi")
    , @NamedQuery(name = "Radi.findByOcena", query = "SELECT r FROM Radi r WHERE r.ocena = :ocena")
    , @NamedQuery(name = "Radi.findByDatumPocetka", query = "SELECT r FROM Radi r WHERE r.datumPocetka = :datumPocetka")
    , @NamedQuery(name = "Radi.findByDatumKraja", query = "SELECT r FROM Radi r WHERE r.datumKraja = :datumKraja")
    , @NamedQuery(name = "Radi.findOcenaByIdZaposleni", query = "SELECT r.ocena FROM Radi r WHERE r.idZaposleni = :idZaposleni")})
public class Radi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idRadi")
    private Integer idRadi;
    @Column(name = "Ocena")
    private Integer ocena;
    @Column(name = "DatumPocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumPocetka;
    @Column(name = "DatumKraja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumKraja;
    @JoinColumn(name = "idPosao", referencedColumnName = "idPosao")
    @ManyToOne(optional = false)
    private Posao idPosao;
    @JoinColumn(name = "idZaposleni", referencedColumnName = "idZaposleni")
    @ManyToOne(optional = false)
    private Zaposleni idZaposleni;

    public Radi() {
    }

    public Radi(Integer idRadi) {
        this.idRadi = idRadi;
    }

    public Integer getIdRadi() {
        return idRadi;
    }

    public void setIdRadi(Integer idRadi) {
        this.idRadi = idRadi;
    }

    public Integer getOcena() {
        return ocena;
    }

    public void setOcena(Integer ocena) {
        this.ocena = ocena;
    }

    public Date getDatumPocetka() {
        return datumPocetka;
    }

    public void setDatumPocetka(Date datumPocetka) {
        this.datumPocetka = datumPocetka;
    }

    public Date getDatumKraja() {
        return datumKraja;
    }

    public void setDatumKraja(Date datumKraja) {
        this.datumKraja = datumKraja;
    }

    public Posao getIdPosao() {
        return idPosao;
    }

    public void setIdPosao(Posao idPosao) {
        this.idPosao = idPosao;
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
        hash += (idRadi != null ? idRadi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Radi)) {
            return false;
        }
        Radi other = (Radi) object;
        if ((this.idRadi == null && other.idRadi != null) || (this.idRadi != null && !this.idRadi.equals(other.idRadi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Radi[ idRadi=" + idRadi + " ]";
    }

    public static Radi findById(EntityManager em, int idRadi) {
        TypedQuery<Radi> query = em.createNamedQuery("Radi.findByIdRadi", Radi.class);
        query.setParameter("idRadi", idRadi);
        return query.getSingleResult();
    }
    
    public static List<Integer> findOcenaByIdZaposleni(EntityManager em, int idZaposleni) {
        TypedQuery<Integer> query = em.createNamedQuery("Radi.findOcenaByIdZaposleni", Integer.class);
        query.setParameter("idZaposleni", Zaposleni.findById(em, idZaposleni));
        return query.getResultList();
    }
}
