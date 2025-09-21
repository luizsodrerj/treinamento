package jeebackendexemplo.core.repository;

import jeebackendexemplo.core.entity.Cliente;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class ClienteRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Cliente save(Cliente cliente) {
        entityManager.persist(cliente);

        return cliente;
    }

    public Cliente findById(Long id) {
        return entityManager.find(Cliente.class, id);
    }

    public List<Cliente> findByNomeRazaoSocialContainingIgnoreCase(String nomeRazaoSocial) {
        String jpql = "select c from Cliente c " +
                        "where upper(c.nomeRazaoSocial) like upper(:nomeRazaoSocial)";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("nomeRazaoSocial", "%"+nomeRazaoSocial+"%");

        return query.getResultList();
    }

}
