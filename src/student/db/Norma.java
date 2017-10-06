/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package student.db;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "Norma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Norma.findAll", query = "SELECT n FROM Norma n")
    , @NamedQuery(name = "Norma.findByIdNorma", query = "SELECT n FROM Norma n WHERE n.idNorma = :idNorma")
    , @NamedQuery(name = "Norma.findByNaziv", query = "SELECT n FROM Norma n WHERE n.naziv = :naziv")
    , @NamedQuery(name = "Norma.findByCena", query = "SELECT n FROM Norma n WHERE n.cena = :cena")
    , @NamedQuery(name = "Norma.findByJedinicnaPlata", query = "SELECT n FROM Norma n WHERE n.jedinicnaPlata = :jedinicnaPlata")})
public class Norma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idNorma")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idNorma;
    @Column(name = "Naziv")
    private String naziv;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Cena")
    private BigDecimal cena;
    @Column(name = "JedinicnaPlata")
    private BigDecimal jedinicnaPlata;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNorma")
    private Collection<MaterijalZaNormu> materijalZaNormuCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNorma")
    private Collection<Posao> posaoCollection;

    public Norma() {
    }

    public Norma(Integer idNorma) {
        this.idNorma = idNorma;
    }

    public Integer getIdNorma() {
        return idNorma;
    }

    public void setIdNorma(Integer idNorma) {
        this.idNorma = idNorma;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public BigDecimal getJedinicnaPlata() {
        return jedinicnaPlata;
    }

    public void setJedinicnaPlata(BigDecimal jedinicnaPlata) {
        this.jedinicnaPlata = jedinicnaPlata;
    }

    @XmlTransient
    public Collection<MaterijalZaNormu> getMaterijalZaNormuCollection() {
        return materijalZaNormuCollection;
    }

    public void setMaterijalZaNormuCollection(Collection<MaterijalZaNormu> materijalZaNormuCollection) {
        this.materijalZaNormuCollection = materijalZaNormuCollection;
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
        hash += (idNorma != null ? idNorma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Norma)) {
            return false;
        }
        Norma other = (Norma) object;
        if ((this.idNorma == null && other.idNorma != null) || (this.idNorma != null && !this.idNorma.equals(other.idNorma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "student.db.Norma[ idNorma=" + idNorma + " ]";
    }

    public static Norma findById(EntityManager em, int idNorma) {
        TypedQuery<Norma> query = em.createNamedQuery("Norma.findByIdNorma", Norma.class);
        query.setParameter("idNorma", idNorma);
        return query.getSingleResult();
    }
}
