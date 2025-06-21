package com.rslakra.vantageservice.city.persistence.entity;

import com.rslakra.appsuite.core.ToString;
import com.rslakra.appsuite.spring.persistence.entity.NamedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cities")
public class City extends NamedEntity<Long> {
    
    @Column(name = "founded_in")
    private Integer foundedIn;
    
    @Column(name = "population")
    private Integer population;
    
    /**
     * Returns the string representation of this object.
     *
     * @return
     */
    @Override
    public String toString() {
        return ToString.of(City.class)
                .add("id", getId())
                .add("name", getName())
                .add("foundedIn", getFoundedIn())
                .add("population", getPopulation())
                .toString();
    }
}
