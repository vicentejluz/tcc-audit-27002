package com.fatec.tcc.tccaudit.models.entities;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tb_evidence")
public class Evidence implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvidence;

    @OneToOne(optional = true)
    @JoinColumn(name = "id_answer")
    private Answer answer;

    private String name;

    @Lob
    private byte[] file;

    @Transient
    private MultipartFile multipartFile;

    public Evidence() {
    }

    public Evidence(Answer answer, String name, byte[] file) {
        this.answer = answer;
        this.name = name;
        this.file = file;
    }

    public Long getIdEvidence() {
        return idEvidence;
    }

    public void setIdEvidence(Long idEvidence) {
        this.idEvidence = idEvidence;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idEvidence == null) ? 0 : idEvidence.hashCode());
        result = prime * result + ((answer == null) ? 0 : answer.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + Arrays.hashCode(file);
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
        Evidence other = (Evidence) obj;
        if (idEvidence == null) {
            if (other.idEvidence != null)
                return false;
        } else if (!idEvidence.equals(other.idEvidence))
            return false;
        if (answer == null) {
            if (other.answer != null)
                return false;
        } else if (!answer.equals(other.answer))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (!Arrays.equals(file, other.file))
            return false;
        return true;
    }

}