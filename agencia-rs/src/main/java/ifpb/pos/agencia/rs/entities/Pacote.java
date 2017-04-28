package ifpb.pos.agencia.rs.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
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
@SequenceGenerator(name = "pacote_seq",
        allocationSize = 1,
        initialValue = 1,
        sequenceName = "pacote_sequencia")

public class Pacote implements Serializable {

    @Id
    @GeneratedValue(generator = "pacote_seq", strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idCliente;
    
    private Long reservaHotel;
    
    private Long reservaPassagem;
    
    public Pacote() {
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

    public Long getReservaHotel() {
        return reservaHotel;
    }

    public void setReservaHotel(Long reservaHotel) {
        this.reservaHotel = reservaHotel;
    }

    public Long getReservaPassagem() {
        return reservaPassagem;
    }

    public void setReservaPassagem(Long reservaPassagem) {
        this.reservaPassagem = reservaPassagem;
    }

    @Override
    public String toString() {
        return "Pacote{" + "id=" + id + ", idCliente=" + idCliente + ", reservaHotel=" + reservaHotel + ", reservaPassagem=" + reservaPassagem + '}';
    }

    
}
