package com.example.a41754.tourofheros.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Hero {
        @Id(autoincrement = true)
        private Long _id;
        @NotNull
        private String name;
        @Generated(hash = 564741131)
        public Hero(Long _id, @NotNull String name) {
            this._id = _id;
            this.name = name;
        }
        @Generated(hash = 2034257870)
        public Hero() {
        }
        public Long get_id() {
            return this._id;
        }
        public void set_id(Long _id) {
            this._id = _id;
        }
        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }
}
