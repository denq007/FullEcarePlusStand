package com.t_systems.ecare.eCare.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tariff")
public class Tariff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
   /* @SerializedName("id")*/
    private int id;
   /* @SerializedName("name")*/
    @Expose
    @Column(name = "name")
    private String name;
   /* @SerializedName("price")*/
    @Expose
    @Column(name = "price")
    private double price;
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH,CascadeType.DETACH},fetch = FetchType.LAZY)
    @JoinTable(name = "tariff_option", joinColumns = @JoinColumn(name = "tariff_id"),
            inverseJoinColumns =@JoinColumn(name = "option_id"))
    private List<Option> optionIdList =new ArrayList<>();
    @Column(name="old")
    boolean isOld;

    public Tariff(String name, double price, boolean isOld) {
        this.name = name;
        this.price = price;
        this.isOld = isOld;
    }
    public void addOptionToTariff(Option option)
    {
        if(optionIdList==null)
            optionIdList=new ArrayList<>();
        optionIdList.add(option);

    }
    @Override
    public String toString() {

        return "Tariff [id=" + id + ", name=" + name + ", price=" + price +"]";
    }
}
