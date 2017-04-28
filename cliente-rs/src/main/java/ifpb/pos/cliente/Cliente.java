package ifpb.pos.cliente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Natarajan
 */
@XmlRootElement
@Entity
@SequenceGenerator(name = "cliente_seq",
        allocationSize = 1,
        initialValue = 4,
        sequenceName = "cliente_sequencia")

public class Cliente implements Serializable {

    @Id
    @GeneratedValue(generator = "cliente_seq", strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    
    @Column(unique = true)
    private String cpf;
    private BigDecimal renda;

    public Cliente() {
    }

    public Cliente(String nome, String cpf, BigDecimal renda) {
        this.nome = nome;
        this.cpf = cpf;
        this.renda = renda;
    }

    public Cliente(Long id, String nome, String cpf, BigDecimal renda) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.renda = renda;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public BigDecimal getRenda() {
        return renda;
    }

    public void setRenda(BigDecimal renda) {
        this.renda = renda;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", renda=" + renda + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.nome);
        hash = 83 * hash + Objects.hashCode(this.cpf);
        hash = 83 * hash + Objects.hashCode(this.renda);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.cpf, other.cpf)) {
            return false;
        }
        if (!Objects.equals(this.renda, other.renda)) {
            return false;
        }
        return true;
    }

}
