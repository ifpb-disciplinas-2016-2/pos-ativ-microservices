package ifpb.pos.passagem.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Natarajan
 */
@XmlRootElement
@Entity
@SequenceGenerator(name = "reseva_passagem_seq",
        allocationSize = 1,
        initialValue = 1,
        sequenceName = "reseva_passagem_sequencia")

public class ReservaPassagem implements Serializable {

    @Id
    @GeneratedValue(generator = "reseva_passagem_seq", strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idCliente;

    @OneToOne
    private Empresa empresa;
//    private LocalDate dataInicio;
//    private int quantidadeDiarias;

    public ReservaPassagem() {
    }

    public ReservaPassagem(Long idCliente, Empresa empresa) {
        this.idCliente = idCliente;
        this.empresa = empresa;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "ReservaPassagem{" + "id=" + id + ", idCliente=" + idCliente + ", empresa=" + empresa + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.idCliente);
        hash = 29 * hash + Objects.hashCode(this.empresa);
//        hash = 29 * hash + Objects.hashCode(this.dataInicio);
//        hash = 29 * hash + this.quantidadeDiarias;
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
        final ReservaPassagem other = (ReservaPassagem) obj;
        if (this.id != other.id) {
            return false;
        }
//        if (this.quantidadeDiarias != other.quantidadeDiarias) {
//            return false;
//        }
        if (!Objects.equals(this.idCliente, other.idCliente)) {
            return false;
        }
        if (!Objects.equals(this.empresa, other.empresa)) {
            return false;
        }
//        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
//            return false;
//        }
        return true;
    }

}
