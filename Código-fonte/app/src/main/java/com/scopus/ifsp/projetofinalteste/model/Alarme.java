package com.scopus.ifsp.projetofinalteste.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

@Entity
public class Alarme implements Serializable {
    public static final long serialVersionUID = 42L;

    @Id(autoincrement = true)
    private Long id;

    private String titulo;
    private String data;
    private String ultima_data;
    private int ativar_ultima_data;
    private String time;
    private String repeat;
    private String repeat_no;
    private String repeat_type;
    private String active;

    private Long remedioId;

    @ToOne(joinProperty = "remedioId")
    private Remedio remedio;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 415645767)
    private transient AlarmeDao myDao;

    @Generated(hash = 39699933)
    private transient Long remedio__resolvedKey;

    @Generated(hash = 1463560413)
    public Alarme(Long id, String titulo, String data, String ultima_data,
            int ativar_ultima_data, String time, String repeat, String repeat_no,
            String repeat_type, String active, Long remedioId) {
        this.id = id;
        this.titulo = titulo;
        this.data = data;
        this.ultima_data = ultima_data;
        this.ativar_ultima_data = ativar_ultima_data;
        this.time = time;
        this.repeat = repeat;
        this.repeat_no = repeat_no;
        this.repeat_type = repeat_type;
        this.active = active;
        this.remedioId = remedioId;
    }
    @Generated(hash = 1942807575)
    public Alarme() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitulo() {
        return this.titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public String getUltima_data() {
        return this.ultima_data;
    }
    public void setUltima_data(String ultima_data) {
        this.ultima_data = ultima_data;
    }
    public int getAtivar_ultima_data() {
        return this.ativar_ultima_data;
    }
    public void setAtivar_ultima_data(int ativar_ultima_data) {
        this.ativar_ultima_data = ativar_ultima_data;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getRepeat() {
        return this.repeat;
    }
    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
    public String getRepeat_no() {
        return this.repeat_no;
    }
    public void setRepeat_no(String repeat_no) {
        this.repeat_no = repeat_no;
    }
    public String getRepeat_type() {
        return this.repeat_type;
    }
    public void setRepeat_type(String repeat_type) {
        this.repeat_type = repeat_type;
    }
    public String getActive() {
        return this.active;
    }
    public void setActive(String active) {
        this.active = active;
    }
    public Long getRemedioId() {
        return this.remedioId;
    }
    public void setRemedioId(Long remedioId) {
        this.remedioId = remedioId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1270555936)
    public Remedio getRemedio() {
        Long __key = this.remedioId;
        if (remedio__resolvedKey == null || !remedio__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RemedioDao targetDao = daoSession.getRemedioDao();
            Remedio remedioNew = targetDao.load(__key);
            synchronized (this) {
                remedio = remedioNew;
                remedio__resolvedKey = __key;
            }
        }
        return remedio;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1776351867)
    public void setRemedio(Remedio remedio) {
        synchronized (this) {
            this.remedio = remedio;
            remedioId = remedio == null ? null : remedio.getId();
            remedio__resolvedKey = remedioId;
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
    @Generated(hash = 1023909803)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAlarmeDao() : null;
    }
}
