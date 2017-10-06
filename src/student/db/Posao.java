/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Posao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Posao.findAll", query = "SELECT p FROM Posao p")
    , @NamedQuery(name = "Posao.findByIdPosao", query = "SELECT p FROM Posao p WHERE p.idPosao = :idPosao")
    , @NamedQuery(name = "Posao.findByStatus", query = "SELECT p FROM Posao p WHERE p.status = :status")
    , @NamedQuery(name = "Posao.findByDatumPocetka", query = "SELECT p FROM Posao p WHERE p.datumPocetka = :datumPocetka")
    , @NamedQuery(name = "Posao.findByDatumKraja", query = "SELECT p FROM Posao p WHERE p.datumKraja = :datumKraja")})
public class Posao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "idPosao")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idPosao;
    @Column(name = "Status")
    private Character status;
    @Column(name = "DatumPocetka")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumPocetka;
    @Column(name = "DatumKraja")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumKraja;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPosao")
    private Collection<Radi> radiCollection;
    @JoinColumn(name = "idNorma", referencedColumnName = "idNorma")
    @ManyToOne(optional = false)
    private Norma idNorma;
    @JoinColumn(name = "idSprat", referencedColumnName = "idSprat")
    @ManyToOne(optional = false)
    private Sprat idSprat;

    public Posao() {
    }

    public Posao(Integer idPosao) {
        this.idPosao = idPosao;
    }

    public Integer getIdPosao() {
        return idPosao;
    }

    public void setIdPosao(Integer idPosao) {
        this.idPosao = idPosao;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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

    @XmlTransient
    public Collection<Radi> getRadiCollection() {
        return radiCollection;
    }

    public void setRadiCollection(Collection<Radi> radiCollection) {
        this.radiCollection = radiCollection;
    }

    public Norma getIdNorma() {
        return idNorma;
    }

    public void setIdNorma(Norma idNorma) {
        this.idNorma = idNorma;
    }

    public Sprat getIdSprat() {
        return idSprat;
    }

    public void setIdSprat(Sprat idSprat) {
        this.idSprat = idSprat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPosao != null ? idPosao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Posao)) {
            return false;
        }
        Posao other = (Posao) object;
        if ((this.idPosao == null && other.idPosao != null) || (this.idPosao != null && !this.idPosao.equals(other.idPosao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Posao[ idPosao=" + idPosao + " ]";
    }
    
    public static Posao findById(EntityManager em, int idPosao) {
        TypedQuery<Posao> query = em.createNamedQuery("Posao.findByIdPosao", Posao.class);
        query.setParameter("idPosao", idPosao);
        return query.getSingleResult();
    }
}
