package com.scopus.ifsp.projetofinalteste.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * @author Denis Magno
 */
@Entity
public class Remedio implements Serializable {
    public static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    private Long id;

    private String nome_farmaceutico;
    private String endereco;
    private String instituicao;
    private String RP;
    private String cpf_cnpj;
    private String data_pedido;
    private String nome_remedio;
    private String forma_farmaceutica;
    private String concentracao;
    private String quantidade;
    private String frequencia;
    private String orientacao_extra;

    private Long idosoId;

    @ToOne(joinProperty = "idosoId")
    private Idoso idoso;

    @ToMany(referencedJoinProperty = "remedioId")
    private List<Alarme> alarmes;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 2030007253)
    private transient RemedioDao myDao;

    @Generated(hash = 1027092189)
    private transient Long idoso__resolvedKey;

    @Generated(hash = 1101419490)
    public Remedio(Long id, String nome_farmaceutico, String endereco,
            String instituicao, String RP, String cpf_cnpj, String data_pedido,
            String nome_remedio, String forma_farmaceutica, String concentracao,
            String quantidade, String frequencia, String orientacao_extra,
            Long idosoId) {
        this.id = id;
        this.nome_farmaceutico = nome_farmaceutico;
        this.endereco = endereco;
        this.instituicao = instituicao;
        this.RP = RP;
        this.cpf_cnpj = cpf_cnpj;
        this.data_pedido = data_pedido;
        this.nome_remedio = nome_remedio;
        this.forma_farmaceutica = forma_farmaceutica;
        this.concentracao = concentracao;
        this.quantidade = quantidade;
        this.frequencia = frequencia;
        this.orientacao_extra = orientacao_extra;
        this.idosoId = idosoId;
    }

    @Generated(hash = 65041058)
    public Remedio() {
    }

    public boolean isEmpty() {
        if (this.nome_farmaceutico != null) {
            return false;
        } else if (this.endereco != null) {
            return false;
        } else if (this.instituicao != null) {
            return false;
        } else if (!this.RP.isEmpty()) {
            return false;
        } else if (!this.cpf_cnpj.isEmpty()) {
            return false;
        } else if (!this.data_pedido.isEmpty()) {
            return false;
        } else if (!this.nome_remedio.isEmpty()) {
            return false;
        } else if (!this.forma_farmaceutica.isEmpty()) {
            return false;
        } else if (!this.concentracao.isEmpty()) {
            return false;
        } else if (!this.quantidade.isEmpty()) {
            return false;
        } else if (!this.frequencia.isEmpty()) {
            return false;
        } else if (!this.orientacao_extra.isEmpty()) {
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

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome_farmaceutico() {
        return this.nome_farmaceutico;
    }

    public void setNome_farmaceutico(String nome_farmaceutico) {
        this.nome_farmaceutico = nome_farmaceutico;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getInstituicao() {
        return this.instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getRP() {
        return this.RP;
    }

    public void setRP(String RP) {
        this.RP = RP;
    }

    public String getCpf_cnpj() {
        return this.cpf_cnpj;
    }

    public void setCpf_cnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public String getData_pedido() {
        return this.data_pedido;
    }

    public void setData_pedido(String data_pedido) {
        this.data_pedido = data_pedido;
    }

    public String getNome_remedio() {
        return this.nome_remedio;
    }

    public void setNome_remedio(String nome_remedio) {
        this.nome_remedio = nome_remedio;
    }

    public String getForma_farmaceutica() {
        return this.forma_farmaceutica;
    }

    public void setForma_farmaceutica(String forma_farmaceutica) {
        this.forma_farmaceutica = forma_farmaceutica;
    }

    public String getConcentracao() {
        return this.concentracao;
    }

    public void setConcentracao(String concentracao) {
        this.concentracao = concentracao;
    }

    public String getQuantidade() {
        return this.quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getFrequencia() {
        return this.frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getOrientacao_extra() {
        return this.orientacao_extra;
    }

    public void setOrientacao_extra(String orientacao_extra) {
        this.orientacao_extra = orientacao_extra;
    }

    public Long getIdosoId() {
        return this.idosoId;
    }

    public void setIdosoId(Long idosoId) {
        this.idosoId = idosoId;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 688242880)
    public Idoso getIdoso() {
        Long __key = this.idosoId;
        if (idoso__resolvedKey == null || !idoso__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IdosoDao targetDao = daoSession.getIdosoDao();
            Idoso idosoNew = targetDao.load(__key);
            synchronized (this) {
                idoso = idosoNew;
                idoso__resolvedKey = __key;
            }
        }
        return idoso;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 322780231)
    public void setIdoso(Idoso idoso) {
        synchronized (this) {
            this.idoso = idoso;
            idosoId = idoso == null ? null : idoso.getId();
            idoso__resolvedKey = idosoId;
        }
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

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 561496845)
    public List<Alarme> getAlarmes() {
        if (alarmes == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AlarmeDao targetDao = daoSession.getAlarmeDao();
            List<Alarme> alarmesNew = targetDao._queryRemedio_Alarmes(id);
            synchronized (this) {
                if (alarmes == null) {
                    alarmes = alarmesNew;
                }
            }
        }
        return alarmes;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1140198186)
    public synchronized void resetAlarmes() {
        alarmes = null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1710176519)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRemedioDao() : null;
    }
}