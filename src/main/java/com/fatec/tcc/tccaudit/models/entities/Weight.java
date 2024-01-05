package com.fatec.tcc.tccaudit.models.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_weight")
public class Weight {
    @Id
    private Long idWeight;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id_weight", referencedColumnName = "idAnswer", foreignKey = @ForeignKey(name = "fk_weight_id_answer"))
    private Answer answer;

    @Column(nullable = false)
    private Double weight;

    public Weight() {
    }

    public Weight(Answer answer, Double weight) {
        this.answer = answer;
        this.weight = weight;
    }

    public Long getIdWeight() {
        return idWeight;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idWeight == null) ? 0 : idWeight.hashCode());
        result = prime * result + ((answer == null) ? 0 : answer.hashCode());
        result = prime * result + ((weight == null) ? 0 : weight.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Weight other = (Weight) obj;
        if (idWeight == null) {
            if (other.idWeight != null)
                return false;
        } else if (!idWeight.equals(other.idWeight))
            return false;
        if (answer == null) {
            if (other.answer != null)
                return false;
        } else if (!answer.equals(other.answer))
            return false;
        if (weight == null) {
            if (other.weight != null)
                return false;
        } else if (!weight.equals(other.weight))
            return false;
        return true;
    }
}