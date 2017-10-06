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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Tip")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tip.findAll", query = "SELECT t FROM Tip t")
    , @NamedQuery(name = "Tip.findByIdTip", query = "SELECT t FROM Tip t WHERE t.idTip = :idTip")
    , @NamedQuery(name = "Tip.findByNaziv", query = "SELECT t FROM Tip t WHERE t.naziv = :naziv")})
public class Tip implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idTip")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idTip;
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTip")
    private Collection<Roba> robaCollection;

    public Tip() {
    }

    public Tip(Integer idTip) {
        this.idTip = idTip;
    }

    public Integer getIdTip() {
        return idTip;
    }

    public void setIdTip(Integer idTip) {
        this.idTip = idTip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    public Collection<Roba> getRobaCollection() {
        return robaCollection;
    }

    public void setRobaCollection(Collection<Roba> robaCollection) {
        this.robaCollection = robaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTip != null ? idTip.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tip)) {
            return false;
        }
        Tip other = (Tip) object;
        if ((this.idTip == null && other.idTip != null) || (this.idTip != null && !this.idTip.equals(other.idTip))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Tip[ idTip=" + idTip + " ]";
    }
    
    public static Tip findById(EntityManager em, int idTip) {
        TypedQuery<Tip> query = em.createNamedQuery("Tip.findByIdTip", Tip.class);
        query.setParameter("idTip", idTip);
        return query.getSingleResult();
    }
    
}
