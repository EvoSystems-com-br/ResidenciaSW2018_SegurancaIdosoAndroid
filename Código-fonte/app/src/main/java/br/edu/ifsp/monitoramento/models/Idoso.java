package br.edu.ifsp.monitoramento.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * @author Denis Magno
 */

@Entity
public class Idoso implements Serializable {
    // Inicialização de número de serial para serialização do objeto para o greenDao
    public static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    private Long id;

    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private String grupoSanguineo;
    private Double altura;
    private Double peso;
    private String logradouro;
    private String numero;
    private Integer cep;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;

    @ToMany(referencedJoinProperty = "idosoId")
    private List<Alergia> alergias;

    @ToMany(referencedJoinProperty = "idosoId")
    private List<Medicamento> medicamentos;

    @ToMany(referencedJoinProperty = "idosoId")
    private List<ContatoEmergencia> contatosEmergencia;

    @ToMany(referencedJoinProperty = "idosoId")
    private List<Dado> dados;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1746625679)
    private transient IdosoDao myDao;

    @Generated(hash = 894439965)
    public Idoso(Long id, String nome, String sobrenome, String dataNascimento,
            String grupoSanguineo, Double altura, Double peso, String logradouro,
            String numero, Integer cep, String bairro, String cidade, String estado,
            String complemento) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.dataNascimento = dataNascimento;
        this.grupoSanguineo = grupoSanguineo;
        this.altura = altura;
        this.peso = peso;
        this.logradouro = logradouro;
        this.numero = numero;
        this.cep = cep;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.complemento = complemento;
    }

    @Generated(hash = 782939218)
    public Idoso() {
    }

    public boolean isEmpty() {
        if (this.nome != null) {
            return false;
        } else if (this.dataNascimento != null) {
            return false;
        } else if (this.grupoSanguineo != null) {
            return false;
        } else if (this.altura != null) {
            return false;
        } else if (this.peso != null) {
            return false;
        } else if (this.logradouro != null) {
            return false;
        } else if (this.numero != null) {
            return false;
        } else if (this.cep != null) {
            return false;
        } else if (this.bairro != null) {
            return false;
        } else if (this.cidade != null) {
            return false;
        } else if (this.estado != null) {
            return false;
        } else if (this.complemento != null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isEntity() {
        if (this.id >= 0 && this.id != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setAlergias(List<Alergia> alergias) {
        this.alergias = alergias;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void setContatosEmergencia(List<ContatoEmergencia> contatosEmergencia) {
        this.contatosEmergencia = contatosEmergencia;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGrupoSanguineo() {
        return this.grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public Double getAltura() {
        return this.altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return this.peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getCep() {
        return this.cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2094548747)
    public List<Alergia> getAlergias() {
        if (alergias == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AlergiaDao targetDao = daoSession.getAlergiaDao();
            List<Alergia> alergiasNew = targetDao._queryIdoso_Alergias(id);
            synchronized (this) {
                if (alergias == null) {
                    alergias = alergiasNew;
                }
            }
        }
        return alergias;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 607189254)
    public synchronized void resetAlergias() {
        alergias = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 813669850)
    public List<Medicamento> getMedicamentos() {
        if (medicamentos == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MedicamentoDao targetDao = daoSession.getMedicamentoDao();
            List<Medicamento> medicamentosNew = targetDao
                    ._queryIdoso_Medicamentos(id);
            synchronized (this) {
                if (medicamentos == null) {
                    medicamentos = medicamentosNew;
                }
            }
        }
        return medicamentos;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1135978272)
    public synchronized void resetMedicamentos() {
        medicamentos = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1426577176)
    public List<ContatoEmergencia> getContatosEmergencia() {
        if (contatosEmergencia == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ContatoEmergenciaDao targetDao = daoSession.getContatoEmergenciaDao();
            List<ContatoEmergencia> contatosEmergenciaNew = targetDao
                    ._queryIdoso_ContatosEmergencia(id);
            synchronized (this) {
                if (contatosEmergencia == null) {
                    contatosEmergencia = contatosEmergenciaNew;
                }
            }
        }
        return contatosEmergencia;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 154492452)
    public synchronized void resetContatosEmergencia() {
        contatosEmergencia = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1968898988)
    public List<Dado> getDados() {
        if (dados == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            DadoDao targetDao = daoSession.getDadoDao();
            List<Dado> dadosNew = targetDao._queryIdoso_Dados(id);
            synchronized (this) {
                if (dados == null) {
                    dados = dadosNew;
                }
            }
        }
        return dados;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 433349631)
    public synchronized void resetDados() {
        dados = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1528524508)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getIdosoDao() : null;
    }
}