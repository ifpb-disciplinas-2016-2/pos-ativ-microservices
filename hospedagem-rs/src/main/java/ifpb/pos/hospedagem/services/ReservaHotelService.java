package ifpb.pos.hospedagem.services;

import ifpb.pos.hospedagem.entities.Hotel;
import ifpb.pos.hospedagem.entities.ReservaHotel;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.PersistenceContext;

/**
 * @author natarajan
 */
@DataSourceDefinition(
        name = "java:app/jdbc/hospedagem",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://banco-hospedagem:5432/hospedagem",
//        url = "jdbc:postgresql://localhost:5432/hotel",
        user = "postgres",
        password = "12345"
)

@Stateless
public class ReservaHotelService {

    @PersistenceContext
    private EntityManager em;

    public ReservaHotel salvar(ReservaHotel reservaHotel ) {
        em.persist(reservaHotel);
        return reservaHotel ;

    }

    public ReservaHotel remover(Long key) {
        ReservaHotel reservaHotel  = this.buscar(key);
        em.remove(reservaHotel);
        return reservaHotel ;
    }

    public ReservaHotel atualizar(ReservaHotel reservaHotel) {
        return em.merge(reservaHotel);
    }

    public ReservaHotel buscar(Long key) {
        return em.find(ReservaHotel.class, key);
    }

    public List<ReservaHotel> listar() {
        return em.createQuery("FROM ReservaHotel h", ReservaHotel.class)
                .getResultList();
    }
}
