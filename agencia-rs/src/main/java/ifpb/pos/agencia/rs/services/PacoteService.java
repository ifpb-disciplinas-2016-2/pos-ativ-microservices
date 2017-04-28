/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifpb.pos.agencia.rs.services;

import ifpb.pos.agencia.rs.entities.Pacote;
import java.util.List;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author natarajan
 */
@DataSourceDefinition(
        name = "java:app/jdbc/agencia",
        className = "org.postgresql.Driver",
        url = "jdbc:postgresql://banco-agencia:5432/agencia",        
//        url = "jdbc:postgresql://localhost:5432/agencia",
        user = "postgres",
        password = "12345"
)
@Stateless
public class PacoteService {
    
    @PersistenceContext
    private EntityManager em;
    
    public Pacote salvar(Pacote pacote) {
        em.persist(pacote);
        return pacote;

    }

    public Pacote remover(Long key) {
        Pacote pacote = this.buscar(key);
        em.remove(pacote);
        return pacote;
    }

    public Pacote atualizar(Pacote pacote) {
        return em.merge(pacote);
    }

    public Pacote buscar(Long key) {
        return em.find(Pacote.class, key);
    }

    public List<Pacote> listar() {
        return em.createQuery("FROM Pacote p", Pacote.class)
                .getResultList();
    }
    
}
