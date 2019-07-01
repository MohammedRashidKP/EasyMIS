package easymis.models.repository;

import easymis.models.entity.DomainObject;
import easymis.models.entity.TransactionStatus;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author RashidKP
 */
public class AbstractRepository {

    public TransactionStatus persist(Object object) throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("company-provider");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
        em.getTransaction().commit();
        em.getTransaction().begin();
        TransactionStatus status = null;
        try {
            em.persist(object);
            em.getTransaction().commit();
            status = fillTransactionStatus(null);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return status;
    }

    public <T extends DomainObject> List<T> retrieve(String queryString, List<QueryParams> params, Class<T> responseObject) {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("company-provider");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<T> resultList = null;
        try {
            Query query = em.createQuery(queryString);
            if (params != null && !params.isEmpty()) {
                params.stream().forEach((queryParam) -> {
                    if (queryParam.getParamDateValue() != null) {
                        query.setParameter(queryParam.getParamName(), queryParam.getParamDateValue(), TemporalType.DATE);
                    } else {
                        query.setParameter(queryParam.getParamName(), queryParam.getParamValue());
                    }
                });
            }
            resultList = query.setHint(QueryHints.REFRESH, true).getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                return resultList;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            em.close();
        }
        return resultList;
    }

    public TransactionStatus merge(Object object) throws Exception{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("company-provider");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        TransactionStatus status = null;
        try {
            em.merge(object);
            em.getTransaction().commit();
            status = fillTransactionStatus(null);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
        return status;
    }

    private TransactionStatus fillTransactionStatus(Exception exception) {
        TransactionStatus status = new TransactionStatus();
        if (exception != null) {
            status.setSuccess(false);
            status.setMessage(exception.getLocalizedMessage());
        } else {
            status.setSuccess(true);
        }
        return status;
    }
}
