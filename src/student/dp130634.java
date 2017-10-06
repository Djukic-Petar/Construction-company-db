package student;

import funkcionalnosti.Funkcionalnosti;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import student.db.*;

public class dp130634 extends Funkcionalnosti {
    
    EntityManagerFactory emf;
    EntityManager em;
    
    private void beginTransaction() {
        emf = Persistence.createEntityManagerFactory("Persistence");
        em = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    private void closeTransaction() {
        em.close();
        emf.close();
    }
    
    @Override
    public int unesiGradiliste(String naziv, Date datumOsnivanja) {
        try {
            beginTransaction();
            Gradiliste gradiliste = new Gradiliste(naziv, datumOsnivanja);
            gradiliste.setBrojObjekata(0);
            em.persist(gradiliste);
            em.getTransaction().commit();
            closeTransaction();
            return gradiliste.getIdGradiliste();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        } 
    }

    @Override
    public int obrisiGradiliste(int idGradiliste) {
        try {
            beginTransaction();
            Gradiliste gradiliste = em.find(Gradiliste.class, idGradiliste);
            em.remove(gradiliste);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        } 
    }

    @Override
    public List<Integer> dohvatiSvaGradilista() {
        try {
            beginTransaction();
            List<Integer> list = Gradiliste.findAllIds(em);
            em.getTransaction().commit();
            closeTransaction();
            if (!list.isEmpty()) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return null;
        }
    }

    @Override
    public int unesiObjekat(String naziv, int idGradiliste) {
        try {
            beginTransaction();
            Gradiliste gradiliste = Gradiliste.findById(em, idGradiliste);
            if (gradiliste != null) {
                Objekat objekat = new Objekat();
                objekat.setIdGradiliste(gradiliste);
                objekat.setNaziv(naziv);
                objekat.setBrojSpratova(0);
                gradiliste.setBrojObjekata(gradiliste.getBrojObjekata() + 1);
                em.persist(gradiliste);
                em.persist(objekat);
                em.getTransaction().commit();
                em.close();
                emf.close();
                return objekat.getIdObjekat();
            } else {
                em.close();
                emf.close();
                return -1;
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiObjekat(int idObjekat) {
        try {
            beginTransaction();
            Objekat objekat = Objekat.findById(em, idObjekat);
            if (objekat != null) {
                Gradiliste gradiliste = objekat.getIdGradiliste();
                gradiliste.setBrojObjekata(gradiliste.getBrojObjekata() - 1);
                em.persist(gradiliste);
                em.remove(objekat);
                em.getTransaction().commit();
            } else {
                em.close();
                emf.close();
                return 1;
            }
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int unesiSprat(int brSprata, int idObjekat) {
        try {
            beginTransaction();
            int najvisiSprat = Objekat.findNajvisiSprat(em, idObjekat);
            Objekat objekat = Objekat.findById(em, idObjekat);
            if (najvisiSprat + 1 == brSprata && objekat != null) {
                Sprat sprat = new Sprat();
                sprat.setBrojSprata(brSprata);
                sprat.setIdObjekat(objekat);
                objekat.setBrojSpratova(objekat.getBrojSpratova() + 1);
                em.persist(sprat);
                em.persist(objekat);
                em.getTransaction().commit();
                em.close();
                emf.close();
                return sprat.getIdSprat();
            } else {
                em.getTransaction().commit();
                em.close();
                emf.close();
                return -1;
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiSprat(int idSprat) {
        try {
            int ret = 1;
            beginTransaction();
            Sprat sprat = Sprat.findById(em, idSprat);
            if (Objekat.findNajvisiSprat(em, sprat.getIdObjekat().getIdObjekat()) == sprat.getBrojSprata()) {
                Objekat objekat = sprat.getIdObjekat();
                em.remove(sprat);
                objekat.setBrojSpratova(objekat.getBrojSpratova() - 1);
                em.persist(objekat);
                em.getTransaction().commit();
                ret = 0;
            }
            closeTransaction();
            return ret;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int unesiZaposlenog(String ime, String prezime, String jmbg, String pol, String ziroRacun, String email, String brojTelefona) {
        try {
            beginTransaction();
            Zaposleni zaposleni = new Zaposleni(ime, prezime, jmbg, pol.charAt(0), ziroRacun, email, brojTelefona);
            zaposleni.setBrZaduzeneRobe(0);
            zaposleni.setProsecnaOcena(BigDecimal.TEN);
            zaposleni.setUkupnoIsplacenIznos(BigDecimal.ZERO);
            em.persist(zaposleni);
            em.getTransaction().commit();
            closeTransaction();
            return zaposleni.getIdZaposleni();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        } 
    }

    @Override
    public int obrisiZaposlenog(int idZaposleni) {
        try {
            beginTransaction();
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposleni);
            if (zaposleni != null) {
                em.remove(zaposleni);
                em.getTransaction().commit();
            } else {
                em.close();
                emf.close();
                return 1;
            }
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public BigDecimal dohvatiUkupanIsplacenIznosZaZaposlenog(int idZaposleni) {
        try {
            beginTransaction();
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposleni);
            if (zaposleni != null) {
                em.close();
                emf.close();
                return zaposleni.getUkupnoIsplacenIznos();
            } else {
                em.close();
                emf.close();
                return new BigDecimal(-1);
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return new BigDecimal(-1);
        }
    }

    @Override
    public BigDecimal dohvatiProsecnuOcenuZaZaposlenog(int idZaposleni) {
        try {
            beginTransaction();
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposleni);
            if (zaposleni != null) {
                em.close();
                emf.close();
                return zaposleni.getProsecnaOcena();
            } else {
                em.close();
                emf.close();
                return new BigDecimal(-1);
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return new BigDecimal(-1);
        }
    }

    @Override
    public int dohvatiBrojTrenutnoZaduzeneOpremeZaZaposlenog(int idZaposleni) {
        try {
            beginTransaction();
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposleni);
            if (zaposleni != null) {
                em.close();
                emf.close();
                return zaposleni.getBrZaduzeneRobe();
            } else {
                em.close();
                emf.close();
                return -1;
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public List<Integer> dohvatiSveZaposlene() {
        try {
            beginTransaction();
            List<Integer> list = Zaposleni.findAllIds(em);
            closeTransaction();
            if (!list.isEmpty()) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return null;
        }
    }

    @Override
    public int unesiMagacin(int idSef, BigDecimal plata, int idGradiliste) {
        try {
            beginTransaction();
            Gradiliste gradiliste = Gradiliste.findById(em, idGradiliste);
            Zaposleni sef = Zaposleni.findById(em, idSef);
            boolean ok = true;
            if (gradiliste != null && sef != null && sef.getIdMagacin() == null) {
                Magacin magacin = new Magacin();
                magacin.setIdGradiliste(gradiliste);
                magacin.setIdSef(sef);
                magacin.setPlata(plata);
                em.persist(magacin);
                sef.setIdMagacin(magacin);
                em.persist(sef);
                em.getTransaction().commit();
                em.close();
                emf.close();
                return magacin.getIdMagacin();
            } else {
                em.close();
                emf.close();
                return -1;
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiMagacin(int idMagacin) {
        try {
            beginTransaction();
            Magacin magacin = Magacin.findById(em, idMagacin);
            if (magacin != null) {
                if (magacin.getIdSef() == null && magacin.getZaposleniCollection().isEmpty() && magacin.getSadrziCollection().isEmpty()) {
                    em.remove(magacin);
                    em.getTransaction().commit();
                } else {
                    em.close();
                    emf.close();
                    return 1;
                }
            } else {
                em.close();
                emf.close();
                return 1;
            }
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int izmeniSefaZaMagacin(int idMagacin, int idSefNovo) {
        try {
            beginTransaction();
            Magacin magacin = Magacin.findById(em, idMagacin);
            boolean ok = true;
            Zaposleni sef = Zaposleni.findById(em, idSefNovo);
            Collection<Radi> poslovi = sef.getRadiCollection();
            for (Radi r : poslovi) {
                if (r.getDatumKraja() == null) {
                    ok = false; break;
                }
            }
            if (magacin != null && sef.getIdMagacin() == null && ok) {
                magacin.setIdSef(sef);
                sef.setIdMagacin(magacin);
                em.persist(magacin);
                em.persist(sef);
                em.getTransaction().commit();
            } else {
                em.close();
                emf.close();
                return 1;
            }
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int izmeniPlatuZaMagacin(int idMagacin, BigDecimal plataNovo) {
        try {
            beginTransaction();
            Magacin magacin = Magacin.findById(em, idMagacin);
            if (magacin != null) {
                magacin.setPlata(plataNovo);
                em.persist(magacin);
                em.getTransaction().commit();
            } else {
                em.close();
                emf.close();
                return 1;
            }
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int isplatiPlateZaposlenimaUSvimMagacinima() {
        try {
            beginTransaction();
            List<Magacin> magacini = Magacin.findAll(em);
            for (Magacin m : magacini) {
                Collection<Zaposleni> zaposleni = m.getZaposleniCollection();
                for (Zaposleni z : zaposleni) {
                    z.setUkupnoIsplacenIznos(z.getUkupnoIsplacenIznos().add(m.getPlata()));
                    em.persist(z);
                }
            }
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int isplatiPlateZaposlenimaUMagacinu(int idMagacin) {
        try {
            beginTransaction();
            Magacin magacin = Magacin.findById(em, idMagacin);
            Collection<Zaposleni> zaposleni = magacin.getZaposleniCollection();
            for (Zaposleni z : zaposleni) {
                z.setUkupnoIsplacenIznos(z.getUkupnoIsplacenIznos().add(magacin.getPlata()));
                em.persist(z);
            }
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }
    
    @Override
    public int unesiRobuUMagacinPoKolicini(int idRoba, int idMagacin, BigDecimal kolicina) {
        try {
            beginTransaction();
            Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
            if (sadrzi != null) {
                sadrzi.setKolicina(sadrzi.getKolicina().add(kolicina));
                em.persist(sadrzi);
            } else {
                sadrzi = new Sadrzi();
                sadrzi.setIdMagacin(Magacin.findById(em, idMagacin));
                sadrzi.setIdRoba(Roba.findById(em, idRoba));
                sadrzi.setKolicina(kolicina);
                em.persist(sadrzi);
            }
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int unesiRobuUMagacinPoBrojuJedinica(int idRoba, int idMagacin, int brojJedinica) {
        try {
            beginTransaction();
            Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
            if (sadrzi != null) {
                sadrzi.setBrojJedinica(sadrzi.getBrojJedinica() + brojJedinica);
                em.persist(sadrzi);
            } else {
                sadrzi = new Sadrzi();
                sadrzi.setIdMagacin(Magacin.findById(em, idMagacin));
                sadrzi.setIdRoba(Roba.findById(em, idRoba));
                sadrzi.setBrojJedinica(brojJedinica);
                em.persist(sadrzi);
            }
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public BigDecimal uzmiRobuIzMagacinaPoKolicini(int idRoba, int idMagacin, BigDecimal kolicina) {
        try {
            beginTransaction();
            Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
            BigDecimal result;
            if (sadrzi != null) {
                if (sadrzi.getKolicina().subtract(kolicina).compareTo(kolicina) <= 0) {
                    result = sadrzi.getKolicina();
                    em.remove(sadrzi);
                } else {
                    sadrzi.setKolicina(sadrzi.getKolicina().subtract(kolicina));
                    result = kolicina;
                    em.persist(sadrzi);
                }
            } else {
                em.close();
                emf.close();
                return new BigDecimal(-1);
            }
            em.getTransaction().commit();
            closeTransaction();
            return result;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return new BigDecimal(-1);
        }
    }

    @Override
    public int uzmiRobuIzMagacinaPoBrojuJedinica(int idRoba, int idMagacin, int brojJedinica) {
        try {
            beginTransaction();
            Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
            int result;
            if (sadrzi != null) {
                if (sadrzi.getBrojJedinica() <= brojJedinica) {
                    result = sadrzi.getBrojJedinica();
                    em.remove(sadrzi);
                } else {
                    sadrzi.setBrojJedinica(sadrzi.getBrojJedinica() - brojJedinica);
                    result = brojJedinica;
                    em.persist(sadrzi);
                }
            } else {
                em.close();
                emf.close();
                return -1;
            }
            em.getTransaction().commit();
            closeTransaction();
            return result;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public BigDecimal pogledajKolicinuRobeUMagacinu(int idRoba, int idMagacin) {
        try {
            beginTransaction();
            Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
            closeTransaction();
            return sadrzi.getKolicina() == null ? new BigDecimal(-1) : sadrzi.getKolicina();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return new BigDecimal(-1);
        }
    }

    @Override
    public int pogledajBrojJedinicaRobeUMagacinu(int idRoba, int idMagacin) {
        try {
            beginTransaction();
            Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
            closeTransaction();
            return sadrzi.getBrojJedinica() == null ? -1 : sadrzi.getBrojJedinica();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int unesiTipRobe(String naziv) {
        try {
            beginTransaction();
            Tip tip = new Tip();
            tip.setNaziv(naziv);
            em.persist(tip);
            em.getTransaction().commit();
            closeTransaction();
            return tip.getIdTip();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiTipRobe(int idTipRobe) {
        try {
            beginTransaction();
            Tip tip = Tip.findById(em, idTipRobe);
            em.remove(tip);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int unesiRobu(String naziv, String kod, int idTipRobe) {
        try {
            beginTransaction();
            Roba roba = new Roba();
            roba.setIdTip(Tip.findById(em, idTipRobe));
            roba.setKod(kod);
            roba.setNaziv(naziv);
            em.persist(roba);
            em.getTransaction().commit();
            closeTransaction();
            return roba.getIdRoba();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiRobu(int idRoba) {
        try {
            beginTransaction();
            Roba roba = Roba.findById(em, idRoba);
            em.remove(roba);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public List<Integer> dohvatiSvuRobu() {
        try {
            beginTransaction();
            List<Integer> list = Roba.findAllIds(em);
            closeTransaction();
            if (!list.isEmpty()) {
                return list;
            } else {
                return null;
            }
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return null;
        }
    }

    @Override
    public int zaposleniRadiUMagacinu(int idZaposleni, int idMagacin) {
        try {
            beginTransaction();
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposleni);
            Magacin magacin = Magacin.findById(em, idMagacin);
            int result;
            if (magacin != null && zaposleni != null) {
                Collection<Radi> poslovi = zaposleni.getRadiCollection();
                boolean ok = true;
                for (Radi r : poslovi) {
                    if (r.getDatumKraja() == null) {
                        ok = false; break;
                    }
                }
                if (zaposleni.getIdMagacin() != null) {
                    ok = false;
                }
                if (ok) {
                    zaposleni.setIdMagacin(magacin);
                    em.persist(zaposleni);
                    result = zaposleni.getIdZaposleni();
                } else {
                    result = -1;
                }
            } else {
                result = -1;
            }
            em.getTransaction().commit();
            closeTransaction();
            return result;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int zaposleniNeRadiUMagacinu(int idZaposleni) {
        try {
            beginTransaction();
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposleni);
            if (zaposleni.getIdMagacin() == null) {
                return 1;
            } else {
                zaposleni.setIdMagacin(null);
                em.persist(zaposleni);
            }
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int zaposleniZaduzujeOpremu(int idZaposlenogKojiZaduzuje, int idMagacin, int idRoba, Date datumZaduzenja, String napomena) {
        try {
            beginTransaction();
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposlenogKojiZaduzuje);
            Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
            Zaduzenje zaduzenje = new Zaduzenje();
            zaduzenje.setIdMagacin(sadrzi.getIdMagacin());
            zaduzenje.setIdRoba(sadrzi.getIdRoba());
            zaduzenje.setIdZaposleni(zaposleni);
            zaduzenje.setDatumZaduzenja(datumZaduzenja);
            zaduzenje.setNapomena(napomena);
            
            em.persist(zaduzenje);
            em.getTransaction().commit();
            closeTransaction();
            return zaduzenje.getIdZaduzenje();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int zaposleniRazduzujeOpremu(int idZaduzenjaOpreme, Date datumRazduzenja) {
        try {
            beginTransaction();
            Zaduzenje zaduzenje = Zaduzenje.findById(em, idZaduzenjaOpreme);
            zaduzenje.setDatumRazduzenja(datumRazduzenja);
            
            em.persist(zaduzenje);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int unesiNormuUgradnogDela(String naziv, BigDecimal cenaIzrade, BigDecimal jedinicnaPlataRadnika) {
        try {
            beginTransaction();
            Norma norma = new Norma();
            norma.setNaziv(naziv);
            norma.setCena(cenaIzrade);
            norma.setJedinicnaPlata(jedinicnaPlataRadnika);
            
            em.persist(norma);
            em.getTransaction().commit();
            closeTransaction();
            return norma.getIdNorma();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiNormuUgradnogDela(int idNormaUgradnogDela) {
        try {
            beginTransaction();
            
            Norma norma = Norma.findById(em, idNormaUgradnogDela);
            em.remove(norma);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public BigDecimal dohvatiJedinicnuPlatuRadnikaNormeUgradnogDela(int idNR) {
        try {
            beginTransaction();
            
            Norma norma = Norma.findById(em, idNR);
            BigDecimal retVal = norma.getJedinicnaPlata();
            em.getTransaction().commit();
            closeTransaction();
            return retVal;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return new BigDecimal(-1);
        }
    }

    @Override
    public int unesiPotrebanMaterijalPoBrojuJedinica(int idRobaKojaJePotrosniMaterijal, int idNormaUgradnogDela, int brojJedinica) {
        try {
            beginTransaction();
            
            MaterijalZaNormu mzn = new MaterijalZaNormu();
            mzn.setBrojJedinica(brojJedinica);
            mzn.setIdNorma(Norma.findById(em, idNormaUgradnogDela));
            mzn.setIdRoba(Roba.findById(em, idRobaKojaJePotrosniMaterijal));
            
            em.persist(mzn);
            em.getTransaction().commit();
            closeTransaction();
            return mzn.getIdMaterijalZaNormu();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int unesiPotrebanMaterijalPoKolicini(int idRobaKojaJePotrosniMaterijal, int idNormaUgradnogDela, BigDecimal kolicina) {
        try {
            beginTransaction();
            
            MaterijalZaNormu mzn = new MaterijalZaNormu();
            mzn.setKolicina(kolicina);
            mzn.setIdNorma(Norma.findById(em, idNormaUgradnogDela));
            mzn.setIdRoba(Roba.findById(em, idRobaKojaJePotrosniMaterijal));
            
            em.persist(mzn);
            em.getTransaction().commit();
            closeTransaction();
            return mzn.getIdMaterijalZaNormu();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiPotrebanMaterijal(int idRobaKojaJePotrosniMaterijal, int idNormaUgradnogDela) {
        try {
            beginTransaction();
            
            MaterijalZaNormu mzn = MaterijalZaNormu.findByRobaAndNorma(em, idRobaKojaJePotrosniMaterijal, idNormaUgradnogDela);
            
            em.remove(mzn);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int unesiPosao(int idNormaUgradnogDela, int idSprat, Date datumPocetka) {
        try {
            beginTransaction();
            
            Sprat sprat = Sprat.findById(em, idSprat);
            Collection<Magacin> magacini = sprat.getIdObjekat().getIdGradiliste().getMagacinCollection();
            
            if (magacini == null || magacini.isEmpty()) {
                em.close();
                emf.close();
                return -1;
            }
            
            Magacin magacin = (Magacin)magacini.toArray()[0];
            
            Norma norma = Norma.findById(em, idNormaUgradnogDela);
            Collection<MaterijalZaNormu> materijal = norma.getMaterijalZaNormuCollection();
            
            boolean ok = true;
            
            for (MaterijalZaNormu m : materijal) {
                boolean found = false;
                Collection<Sadrzi> sadrzi = magacin.getSadrziCollection();
                for (Sadrzi s : sadrzi) {
                    if (s.getIdRoba().getIdRoba().equals(m.getIdRoba().getIdRoba())) {
                        if (s.getBrojJedinica() != null) {
                            if (s.getBrojJedinica() >= m.getBrojJedinica()) {
                                found = true;
                            }
                        } else {
                            if (s.getKolicina().compareTo(m.getKolicina()) >= 0) {
                                found = true;
                            }
                        }
                    }
                }
                if (!found) {
                    ok = false; break;
                }
            }
            
            if (!ok) {
                em.close();
                emf.close();
                return -1;
            }
            
            materijal.forEach((MaterijalZaNormu m) -> {
                Collection<Sadrzi> sadrzi = magacin.getSadrziCollection();
                sadrzi.forEach((Sadrzi s) -> {
                    if (s.getIdRoba().getIdRoba().equals(m.getIdRoba().getIdRoba())) {
                        if (s.getBrojJedinica() != null) {
                            s.setBrojJedinica(s.getBrojJedinica() - m.getBrojJedinica());
                            if (s.getBrojJedinica() == 0) {
                                em.remove(s);
                            } else {
                                em.persist(s);
                            }
                        } else {
                            s.setKolicina(s.getKolicina().subtract(m.getKolicina()));
                            if (s.getKolicina().intValue() == 0) {
                                em.remove(s);
                            } else {
                                em.persist(s);
                            }
                        }
                    }
                });
            });
            
            Posao posao = new Posao();
            posao.setDatumPocetka(datumPocetka);
            posao.setIdNorma(norma);
            posao.setIdSprat(sprat);
            posao.setDatumKraja(null);
            posao.setStatus('U');
            
            em.persist(posao);
            em.getTransaction().commit();
            closeTransaction();
            return posao.getIdPosao();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiPosao(int idPosao) {
        try {
            beginTransaction();
            
            Posao posao = Posao.findById(em, idPosao);
            Magacin magacin = (Magacin)posao.getIdSprat().getIdObjekat().getIdGradiliste().getMagacinCollection().toArray()[0];
            Collection<MaterijalZaNormu> materijali = posao.getIdNorma().getMaterijalZaNormuCollection();
            for (MaterijalZaNormu materijal : materijali) {
                if (materijal.getBrojJedinica() == null)
                    unesiRobuUMagacinPoKoliciniHelper(em, materijal.getIdRoba().getIdRoba(), magacin.getIdMagacin(), materijal.getKolicina());
                else
                    unesiRobuUMagacinPoBrojuJedinicaHelper(em, materijal.getIdRoba().getIdRoba(), magacin.getIdMagacin(), materijal.getBrojJedinica());
            }
            Collection<Radi> radii = posao.getRadiCollection();
            for (Radi radi : radii) {
                if (radi.getDatumKraja() == null)
                    radi.setDatumKraja(new Date(new java.util.Date().getTime()));
                em.persist(radi);
            }
            
            em.remove(posao);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    private void unesiRobuUMagacinPoKoliciniHelper(EntityManager em, int idRoba, int idMagacin, BigDecimal kolicina) {
        Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
        if (sadrzi != null) {
            sadrzi.setKolicina(sadrzi.getKolicina().add(kolicina));
            em.persist(sadrzi);
        } else {
            sadrzi = new Sadrzi();
            sadrzi.setIdMagacin(Magacin.findById(em, idMagacin));
            sadrzi.setIdRoba(Roba.findById(em, idRoba));
            sadrzi.setKolicina(kolicina);
            em.persist(sadrzi);
        }
    }

    private void unesiRobuUMagacinPoBrojuJedinicaHelper(EntityManager em, int idRoba, int idMagacin, int brojJedinica) {
        Sadrzi sadrzi = Sadrzi.findByRobaAndMagacin(em, idRoba, idMagacin);
        if (sadrzi != null) {
            sadrzi.setBrojJedinica(sadrzi.getBrojJedinica() + brojJedinica);
            em.persist(sadrzi);
        } else {
            sadrzi = new Sadrzi();
            sadrzi.setIdMagacin(Magacin.findById(em, idMagacin));
            sadrzi.setIdRoba(Roba.findById(em, idRoba));
            sadrzi.setBrojJedinica(brojJedinica);
            em.persist(sadrzi);
        }
    }
    
    @Override
    public int izmeniDatumPocetkaZaPosao(int idPosao, Date datumPocetka) {
        try {
            beginTransaction();
            
            Posao posao = Posao.findById(em, idPosao);
            posao.setDatumPocetka(datumPocetka);
            
            em.persist(posao);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int zavrsiPosao(int idPosao, Date datumKraja) {
        try {
            beginTransaction();
            
            Posao posao = Posao.findById(em, idPosao);
            posao.setDatumKraja(datumKraja);
            posao.setStatus('Z');
            
            em.persist(posao);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int zaposleniRadiNaPoslu(int idZaposleni, int idPosao, Date datumPocetka) {
        try {
            beginTransaction();
            
            Zaposleni zaposleni = Zaposleni.findById(em, idZaposleni);
            if(zaposleni.getIdMagacin() != null) 
                return -1;
            
            Collection<Radi> radii = zaposleni.getRadiCollection();
            for(Radi radi : radii) {
                if(radi.getDatumKraja() == null) return -1;
            }
            
            Radi radi = new Radi();
            radi.setIdZaposleni(zaposleni);
            radi.setDatumPocetka(datumPocetka);
            radi.setIdPosao(Posao.findById(em, idPosao));
            
            em.persist(radi);
            em.getTransaction().commit();
            closeTransaction();
            return radi.getIdRadi();
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int zaposleniJeZavrsioSaRadomNaPoslu(int idZaposleniNaPoslu, Date datumKraja) {
        try {
            beginTransaction();
            
            Radi radi = Radi.findById(em, idZaposleniNaPoslu);
            radi.setDatumKraja(datumKraja);
            
            em.persist(radi);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int izmeniDatumPocetkaRadaZaposlenogNaPoslu(int idZaposleniNaPoslu, Date datumPocetkaNovo) {
        try {
            beginTransaction();
            
            Radi radi = Radi.findById(em, idZaposleniNaPoslu);
            radi.setDatumPocetka(datumPocetkaNovo);
            
            em.persist(radi);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int izmeniDatumKrajaRadaZaposlenogNaPoslu(int idZaposleniNaPoslu, Date datumKrajaNovo) {
        try {
            beginTransaction();
            
            Radi radi = Radi.findById(em, idZaposleniNaPoslu);
            radi.setDatumKraja(datumKrajaNovo);
            
            em.persist(radi);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }

    @Override
    public int zaposleniDobijaOcenu(int idZaposleniNaPoslu, int ocena) {
        try {
            beginTransaction();
            
            Radi radi = Radi.findById(em, idZaposleniNaPoslu);
            radi.setOcena(ocena);
            
            int cnt = 0;
            double sum = 0;
            
            for (Integer oc : Radi.findOcenaByIdZaposleni(em, radi.getIdZaposleni().getIdZaposleni())) {
                if (oc != null) {
                    sum += oc;
                    cnt++;
                }
            }
            
            Zaposleni zaposleni = radi.getIdZaposleni();
            zaposleni.setProsecnaOcena(new BigDecimal(cnt != 0 ? sum/cnt : 10));
            
            em.persist(zaposleni);
            em.persist(radi);
            em.getTransaction().commit();
            closeTransaction();
            return idZaposleniNaPoslu;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int obrisiOcenuZaposlenom(int idZaposleniNaPoslu) {
        try {
            beginTransaction();
            
            Radi radi = Radi.findById(em, idZaposleniNaPoslu);
            radi.setOcena(null);
            
            int cnt = 0;
            double sum = 0;
            
            for (Integer oc : Radi.findOcenaByIdZaposleni(em, radi.getIdZaposleni().getIdZaposleni())) {
                if (oc != null) {
                    sum += oc;
                    cnt++;
                }
            }
            
            Zaposleni zaposleni = radi.getIdZaposleni();
            zaposleni.setProsecnaOcena(new BigDecimal(cnt != 0 ? sum/cnt : 10));
            
            em.persist(radi);
            em.getTransaction().commit();
            closeTransaction();
            return idZaposleniNaPoslu;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return -1;
        }
    }

    @Override
    public int izmeniOcenuZaZaposlenogNaPoslu(int idZaposleniNaPoslu, int ocenaNovo) {
        try {
            beginTransaction();
            
            Radi radi = Radi.findById(em, idZaposleniNaPoslu);
            radi.setOcena(ocenaNovo);
            
            int cnt = 0;
            double sum = 0;
            
            for (Integer oc : Radi.findOcenaByIdZaposleni(em, radi.getIdZaposleni().getIdZaposleni())) {
                if (oc != null) {
                    sum += oc;
                    cnt++;
                }
            }
            
            Zaposleni zaposleni = radi.getIdZaposleni();
            zaposleni.setProsecnaOcena(new BigDecimal(cnt != 0 ? sum/cnt : 10));
            
            em.persist(radi);
            em.getTransaction().commit();
            closeTransaction();
            return 0;
        } catch (Exception e) {
            Logger.getLogger(dp130634.class.getName()).log(Level.SEVERE, null, e);
            closeTransaction();
            return 1;
        }
    }
    
}
