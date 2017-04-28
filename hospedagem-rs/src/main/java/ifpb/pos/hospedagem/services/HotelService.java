package ifpb.pos.hospedagem.services;

import ifpb.pos.hospedagem.entities.Hotel;
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
public class HotelService {

    @PersistenceContext
    private EntityManager em;

    public Hotel salvar(Hotel hotel) {
        em.persist(hotel);
        return hotel;

    }

    public Hotel remover(Long key) {
        Hotel hotel = this.buscar(key);
        em.remove(hotel);
        return hotel;
    }

    public Hotel atualizar(Hotel hotel) {
        return em.merge(hotel);
    }

    public Hotel buscar(Long key) {
        return em.find(Hotel.class, key);
    }

    public List<Hotel> listar() {
        return em.createQuery("FROM Hotel h", Hotel.class)
                .getResultList();
    }
}
