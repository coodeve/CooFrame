package com.coodev.base.dao.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Info {
    @Id(autoincrement = true)
    private Long _id;

    @Generated(hash = 2082792900)
    public Info(Long _id) {
        this._id = _id;
    }

    @Generated(hash = 614508582)
    public Info() {
    }

    public Long get_id() {
        return this._id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
