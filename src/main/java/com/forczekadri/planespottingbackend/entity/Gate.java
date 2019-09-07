package com.forczekadri.planespottingbackend.entity;

import javax.persistence.*;

@Entity
@Table(name="gates")
public class Gate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name="in_use")
    private boolean in_use;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isIn_use() {
        return in_use;
    }

    public void setIn_use(boolean in_use) {
        this.in_use = in_use;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "id=" + id +
                ", in_use=" + in_use +
                '}';
    }
}
