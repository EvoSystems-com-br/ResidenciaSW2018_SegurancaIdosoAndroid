package com.scopus.ifsp.projetofinalteste.model;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.scopus.ifsp.projetofinalteste.model.DaoSession;
import com.scopus.ifsp.projetofinalteste.model.IdosoDao;
import com.scopus.ifsp.projetofinalteste.model.DadoDao;

/**
 * @author Denis Magno
 */
@Entity
public class Dado implements Serializable {
    // Inicialização de número de serial para serialização do objeto para o greenDao
    public static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    private Long id;

    private Date datetime;
    private Integer status;

    private Long idosoId;

    @ToOne(joinProperty = "idosoId")
    private Idoso idoso;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 613387789)
    private transient DadoDao myDao;

    @Generated(hash = 1553080979)
    public Dado(Long id, Date datetime, Integer status, Long idosoId) {
        this.id = id;
        this.datetime = datetime;
        this.status = status;
        this.idosoId = idosoId;
    }

    @Generated(hash = 1915070836)
    public Dado() {
    }

    @Generated(hash = 1027092189)
    private transient Long idoso__resolvedKey;

    public int getHora() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH");
        return Integer.parseInt(dateFormat.format(this.datetime));
    }

    public int getMinuto() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm");
        return Integer.parseInt(dateFormat.format(this.datetime));
    }

    public int getSegundo() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ss");
        return Integer.parseInt(dateFormat.format(this.datetime));
    }

    public int getDia() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        return Integer.parseInt(dateFormat.format(this.datetime));
    }

    public int getMes() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        return Integer.parseInt(dateFormat.format(this.datetime));
    }

    public int getAno() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        return Integer.parseInt(dateFormat.format(this.datetime));
    }

    public enum EnumStatus {
        ENTROU(1) {
            @Override
            public String toString() {
                return "Entrou";
            }
        },
        SAIU(0) {
            @Override
            public String toString() {
                return "Saiu";
            }
        };

        private final int valor;

        EnumStatus(int valor) {
            this.valor = valor;
        }

        public int getValor() {
            return this.valor;
        }

        public static EnumStatus getByValue(int valor) {
            for (EnumStatus n : values()) {
                if (n.valor == valor) {
                    return n;
                }
            }
            throw new IllegalArgumentException("EnumStatus inválido: " + valor);
        }
    }

    public boolean isEmpty() {
        if (this.datetime != null) {
            return false;
        } else if (this.status != null) {
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

    public Date getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Generated(hash = 918023796)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDadoDao() : null;
    }
}
