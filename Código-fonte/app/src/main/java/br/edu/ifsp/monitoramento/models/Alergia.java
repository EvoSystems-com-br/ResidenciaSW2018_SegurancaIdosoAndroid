package br.edu.ifsp.monitoramento.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * @author Denis Magno
 */
@Entity
public class Alergia implements Serializable {
    // Inicialização de número de serial para serialização do objeto para o greenDao
    public static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    private Long id;

    private String descricao;

    private Long idosoId;

    @ToOne(joinProperty = "idosoId")
    private Idoso idoso;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 913142708)
    private transient AlergiaDao myDao;

    @Generated(hash = 1177401886)
    public Alergia(Long id, String descricao, Long idosoId) {
        this.id = id;
        this.descricao = descricao;
        this.idosoId = idosoId;
    }

    @Generated(hash = 188551958)
    public Alergia() {
    }

    @Generated(hash = 1027092189)
    private transient Long idoso__resolvedKey;

    public boolean isEmpty() {
        if (this.descricao != null) {
            return false;
        } else if (!this.idoso.isEmpty()) {
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

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 42964942)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAlergiaDao() : null;
    }
}