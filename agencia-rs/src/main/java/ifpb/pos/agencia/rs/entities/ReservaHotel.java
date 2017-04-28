package ifpb.pos.agencia.rs.entities;

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
@SequenceGenerator(name = "reserva_hotel_seq",
        allocationSize = 1,
        initialValue = 1,
        sequenceName = "reserva_hotel_sequencia")

public class ReservaHotel implements Serializable {

    @Id
    @GeneratedValue(generator = "reserva_hotel_seq", strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long idCliente;
    
    @OneToOne
    private Hotel hotel;
//    private LocalDate dataInicio;
//    private int quantidadeDiarias;

    public ReservaHotel() {
    }

    public ReservaHotel(Long idCliente, Hotel hotel) {
        this.idCliente = idCliente;
        this.hotel = hotel;
    }

    
//    public ReservaHotel(Long idCliente, Hotel hotel, LocalDate dataInicio, int quantidadeDiarias) {
//        this.idCliente = idCliente;
//        this.hotel = hotel;
//        this.dataInicio = dataInicio;
//        this.quantidadeDiarias = quantidadeDiarias;
//    }

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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

//    public LocalDate getDataInicio() {
//        return dataInicio;
//    }
//
//    public void setDataInicio(LocalDate dataInicio) {
//        this.dataInicio = dataInicio;
//    }
//
//    public int getQuantidadeDiarias() {
//        return quantidadeDiarias;
//    }
//
//    public void setQuantidadeDiarias(int quantidadeDiarias) {
//        this.quantidadeDiarias = quantidadeDiarias;
//    }

//    @Override
//    public String toString() {
//        return "ReservaHotel{" + "id=" + id + ", idCliente=" + idCliente + ", hotel=" + hotel + ", dataInicio=" + dataInicio + ", quantidadeDiarias=" + quantidadeDiarias + '}';
//    }

    @Override
    public String toString() {
        return "ReservaHotel{" + "id=" + id + ", idCliente=" + idCliente + ", hotel=" + hotel + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.idCliente);
        hash = 29 * hash + Objects.hashCode(this.hotel);
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
        final ReservaHotel other = (ReservaHotel) obj;
        if (this.id != other.id) {
            return false;
        }
//        if (this.quantidadeDiarias != other.quantidadeDiarias) {
//            return false;
//        }
        if (!Objects.equals(this.idCliente, other.idCliente)) {
            return false;
        }
        if (!Objects.equals(this.hotel, other.hotel)) {
            return false;
        }
//        if (!Objects.equals(this.dataInicio, other.dataInicio)) {
//            return false;
//        }
        return true;
    }
    
}
