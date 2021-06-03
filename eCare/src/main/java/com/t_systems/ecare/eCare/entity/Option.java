package com.t_systems.ecare.eCare.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @Column(name = "connection_cost")
    private double connectionCost;
    @ManyToMany(cascade = CascadeType.ALL/*{CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH,CascadeType.DETACH}*/,fetch = FetchType.EAGER)
    @JoinTable(name = "tariff_option",joinColumns = @JoinColumn(name="option_id"),
            inverseJoinColumns = @JoinColumn(name = "tariff_id")
    )
    private List<Tariff> tariffsList;
    @Column(name = "group_id")
    int numberGroup;
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH,CascadeType.DETACH},fetch = FetchType.EAGER)
    @JoinTable(name = "contract_option",joinColumns = @JoinColumn(name="option_id"),
            inverseJoinColumns = @JoinColumn(name = "contract_id")
    )
    private Set<Contract> contractList=new HashSet<>();

    public void addTariffToOption(Tariff tariff)
    {
        if(tariffsList==null)
            tariffsList=new ArrayList<>();
        tariffsList.add(tariff);
    }

    public Option(String name, double price, double connectionCost, int numberGroup) {
        this.name = name;
        this.price = price;
        this.connectionCost = connectionCost;
        this.numberGroup = numberGroup;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", connectionCost=" + connectionCost +
                '}';
    }

}
