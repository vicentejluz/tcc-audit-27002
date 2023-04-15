package com.fatec.tcc.tccaudit.models.entities;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_perguntas")
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPergunta;

    private String pergunta;

    private String resumo;

    private int naoSeAplica;
    private int naoAtende;
    private int atendeParcial;
    private int atendeTotal;

    public Question() {
    }

    public Question(String pergunta, String resumo, int naoSeAplica, int naoAtende, int atendeParcial,
            int atendeTotal) {
        this.pergunta = pergunta;
        this.resumo = resumo;
        this.naoSeAplica = naoSeAplica;
        this.naoAtende = naoAtende;
        this.atendeParcial = atendeParcial;
        this.atendeTotal = atendeTotal;
    }

    public Long getIdPergunta() {
        return idPergunta;
    }

    public void setIdPergunta(Long idPergunta) {
        this.idPergunta = idPergunta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getresumo() {
        return resumo;
    }

    public void setresumo(String resumo) {
        this.resumo = resumo;
    }

    public int getNaoSeAplica() {
        return naoSeAplica;
    }

    public void setNaoSeAplica(int naoSeAplica) {
        this.naoSeAplica = naoSeAplica;
    }

    public int getNaoAtende() {
        return naoAtende;
    }

    public void setNaoAtende(int naoAtende) {
        this.naoAtende = naoAtende;
    }

    public int getAtendeParcial() {
        return atendeParcial;
    }

    public void setAtendeParcial(int atendeParcial) {
        this.atendeParcial = atendeParcial;
    }

    public int getAtendeTotal() {
        return atendeTotal;
    }

    public void setAtendeTotal(int atendeTotal) {
        this.atendeTotal = atendeTotal;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idPergunta == null) ? 0 : idPergunta.hashCode());
        result = prime * result + ((pergunta == null) ? 0 : pergunta.hashCode());
        result = prime * result + ((resumo == null) ? 0 : resumo.hashCode());
        result = prime * result + naoSeAplica;
        result = prime * result + naoAtende;
        result = prime * result + atendeParcial;
        result = prime * result + atendeTotal;
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
        Question other = (Question) obj;
        if (idPergunta == null) {
            if (other.idPergunta != null)
                return false;
        } else if (!idPergunta.equals(other.idPergunta))
            return false;
        if (pergunta == null) {
            if (other.pergunta != null)
                return false;
        } else if (!pergunta.equals(other.pergunta))
            return false;
        if (resumo == null) {
            if (other.resumo != null)
                return false;
        } else if (!resumo.equals(other.resumo))
            return false;
        if (naoSeAplica != other.naoSeAplica)
            return false;
        if (naoAtende != other.naoAtende)
            return false;
        if (atendeParcial != other.atendeParcial)
            return false;
        if (atendeTotal != other.atendeTotal)
            return false;
        return true;
    }

}