package ifpb.pos.passagem.services;

import ifpb.pos.passagem.entities.ReservaPassagem;
import ifpb.pos.passagem.entities.ReservaPassagem;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.PersistenceContext;

/**
 * @author natarajan
 */
@DataSourceDefinition(
        name = "java:app/jdbc/passagem",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://banco-passagem:5432/passagem",
//        url = "jdbc:postgresql://localhost:5432/empresa",
        user = "postgres",
        password = "12345"
)

@Stateless
public class ReservaPassagemService {

    @PersistenceContext
    private EntityManager em;

    public ReservaPassagem salvar(ReservaPassagem reservaPassagem ) {
        em.persist(reservaPassagem);
        return reservaPassagem ;

    }

    public ReservaPassagem remover(Long key) {
        ReservaPassagem reservaPassagem  = this.buscar(key);
        em.remove(reservaPassagem);
        return reservaPassagem ;
    }

    public ReservaPassagem atualizar(ReservaPassagem reservaPassagem) {
        return em.merge(reservaPassagem);
    }

    public ReservaPassagem buscar(Long key) {
        return em.find(ReservaPassagem.class, key);
    }

    public List<ReservaPassagem> listar() {
        return em.createQuery("FROM ReservaPassagem h", ReservaPassagem.class)
                .getResultList();
    }
}
