package com.fatec.tcc.tccaudit.models.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_answer")
public class Answer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnswer;

    @ManyToOne()
    @JoinColumn(name = "id_question", nullable = false, foreignKey = @ForeignKey(name = "fk_answer_id_question"))
    private Question question;

    @ManyToOne()
    @JoinColumn(name = "id_company", nullable = false, foreignKey = @ForeignKey(name = "fk_answer_id_company"))
    private Company company;

    @JsonIgnore
    @OneToOne(mappedBy = "answer")
    private Evidence evidence;

    @JsonIgnore
    @OneToOne(mappedBy = "answer")
    private Weight weight;

    private boolean notApplicable;

    private boolean notMet;

    private boolean partiallyMet;

    private boolean fullyMet;

    public Answer() {
    }

    public Answer(Question question, Company company, boolean notApplicable, boolean notMet,
            boolean partiallyMet, boolean fullyMet) {
        this.question = question;
        this.company = company;
        this.notApplicable = notApplicable;
        this.notMet = notMet;
        this.partiallyMet = partiallyMet;
        this.fullyMet = fullyMet;
    }

    public Long getIdAnswer() {
        return idAnswer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Evidence getEvidence() {
        return evidence;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setWeight(Weight weight) {
        this.weight = weight;
    }

    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
    }

    public boolean isNotApplicable() {
        return notApplicable;
    }

    public void setNotApplicable(boolean notApplicable) {
        this.notApplicable = notApplicable;
    }

    public boolean isNotMet() {
        return notMet;
    }

    public void setNotMet(boolean notMet) {
        this.notMet = notMet;
    }

    public boolean isPartiallyMet() {
        return partiallyMet;
    }

    public void setPartiallyMet(boolean partiallyMet) {
        this.partiallyMet = partiallyMet;
    }

    public boolean isFullyMet() {
        return fullyMet;
    }

    public void setFullyMet(boolean fullyMet) {
        this.fullyMet = fullyMet;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAnswer == null) ? 0 : idAnswer.hashCode());
        result = prime * result + ((question == null) ? 0 : question.hashCode());
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result + ((evidence == null) ? 0 : evidence.hashCode());
        result = prime * result + (notApplicable ? 1231 : 1237);
        result = prime * result + (notMet ? 1231 : 1237);
        result = prime * result + (partiallyMet ? 1231 : 1237);
        result = prime * result + (fullyMet ? 1231 : 1237);
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
        Answer other = (Answer) obj;
        if (idAnswer == null) {
            if (other.idAnswer != null)
                return false;
        } else if (!idAnswer.equals(other.idAnswer))
            return false;
        if (question == null) {
            if (other.question != null)
                return false;
        } else if (!question.equals(other.question))
            return false;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (evidence == null) {
            if (other.evidence != null)
                return false;
        } else if (!evidence.equals(other.evidence))
            return false;
        if (notApplicable != other.notApplicable)
            return false;
        if (notMet != other.notMet)
            return false;
        if (partiallyMet != other.partiallyMet)
            return false;
        if (fullyMet != other.fullyMet)
            return false;
        return true;
    }

    @PrePersist
    @PreUpdate
    private void validateFields() {
        int countTrue = 0;
        if (notApplicable) {
            countTrue++;
        }
        if (notMet) {
            countTrue++;
        }
        if (partiallyMet) {
            countTrue++;
        }
        if (fullyMet) {
            countTrue++;
        }
        if (countTrue != 1) {
            throw new IllegalArgumentException(
                    "Only one field can be true: notApplicable, notMet, partiallyMet or fullyMet");
        }
    }
}