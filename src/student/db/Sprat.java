/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Sprat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sprat.findAll", query = "SELECT s FROM Sprat s")
    , @NamedQuery(name = "Sprat.findByIdSprat", query = "SELECT s FROM Sprat s WHERE s.idSprat = :idSprat")
    , @NamedQuery(name = "Sprat.findByBrojSprata", query = "SELECT s FROM Sprat s WHERE s.brojSprata = :brojSprata")})
public class Sprat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idSprat")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idSprat;
    @Column(name = "BrojSprata")
    private Integer brojSprata;
    @JoinColumn(name = "idObjekat", referencedColumnName = "idObjekat")
    @ManyToOne(optional = false)
    private Objekat idObjekat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSprat")
    private Collection<Posao> posaoCollection;

    public Sprat() {
    }

    public Sprat(Integer idSprat) {
        this.idSprat = idSprat;
    }

    public Integer getIdSprat() {
        return idSprat;
    }

    public void setIdSprat(Integer idSprat) {
        this.idSprat = idSprat;
    }

    public Integer getBrojSprata() {
        return brojSprata;
    }

    public void setBrojSprata(Integer brojSprata) {
        this.brojSprata = brojSprata;
    }

    public Objekat getIdObjekat() {
        return idObjekat;
    }

    public void setIdObjekat(Objekat idObjekat) {
        this.idObjekat = idObjekat;
    }

    @XmlTransient
    public Collection<Posao> getPosaoCollection() {
        return posaoCollection;
    }

    public void setPosaoCollection(Collection<Posao> posaoCollection) {
        this.posaoCollection = posaoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSprat != null ? idSprat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprat)) {
            return false;
        }
        Sprat other = (Sprat) object;
        if ((this.idSprat == null && other.idSprat != null) || (this.idSprat != null && !this.idSprat.equals(other.idSprat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Sprat[ idSprat=" + idSprat + " ]";
    }
    
    public static Sprat findById(EntityManager em, int idSprat) {
        TypedQuery<Sprat> query = em.createNamedQuery("Sprat.findByIdSprat", Sprat.class);
        query.setParameter("idSprat", idSprat);
        return query.getSingleResult();
    }
    
}
