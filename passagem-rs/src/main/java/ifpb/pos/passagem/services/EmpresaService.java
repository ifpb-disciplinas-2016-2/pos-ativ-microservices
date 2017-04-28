package ifpb.pos.passagem.services;

import ifpb.pos.passagem.entities.Empresa;
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
public class EmpresaService {

    @PersistenceContext
    private EntityManager em;

    public Empresa salvar(Empresa empresa) {
        em.persist(empresa);
        return empresa;

    }

    public Empresa remover(Long key) {
        Empresa empresa = this.buscar(key);
        em.remove(empresa);
        return empresa;
    }

    public Empresa atualizar(Empresa empresa) {
        return em.merge(empresa);
    }

    public Empresa buscar(Long key) {
        return em.find(Empresa.class, key);
    }

    public List<Empresa> listar() {
        return em.createQuery("FROM Empresa h", Empresa.class)
                .getResultList();
    }
}
