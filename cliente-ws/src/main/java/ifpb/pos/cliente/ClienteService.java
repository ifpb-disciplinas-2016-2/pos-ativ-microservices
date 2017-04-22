package ifpb.pos.cliente;

import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import java.util.List;
import javax.persistence.PersistenceContext;

/**
 * @author natarajan
 */
@DataSourceDefinition(
        name = "java:app/jdbc/cliente",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://banco-cliente:5432/cliente",        
//        url = "jdbc:postgresql://localhost:5432/cliente",
        user = "postgres",
        password = "12345"
)

@Stateless
public class ClienteService {

    @PersistenceContext
    private EntityManager em;

    public Cliente salvar(Cliente cliente) {
        em.persist(cliente);
        return cliente;

    }

    public Cliente remover(int key) {
        Cliente cliente = this.buscar(key);
        em.remove(cliente);
        return cliente;
    }

    public Cliente atualizar(Cliente cliente) {
        return em.merge(cliente);
    }

    public Cliente buscar(int key) {
        return em.find(Cliente.class, key);
    }

    public List<Cliente> listar() {
        return em.createQuery("FROM Cliente p", Cliente.class)
                .getResultList();
    }
}
