package com.tapumandal.ims.repository.implementation;

import com.tapumandal.ims.entity.Measurement;
import com.tapumandal.ims.entity.Company;
import com.tapumandal.ims.repository.CompanyRepository;
import com.tapumandal.ims.repository.ProductRepository;
import com.tapumandal.ims.util.ApplicationPreferences;
import com.tapumandal.ims.util.MyPagenation;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class CompanyRepositoryImpl implements CompanyRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public Session getSession() {
        return entityManager.unwrap(Session.class);
    }


    @Override
    public int create(Company company) {

        getSession().saveOrUpdate(company);
        getSession().flush();
        getSession().clear();
        return company.getId();
    }

    @Override
    public int update(Company company) {

        getSession().update(company);
        getSession().flush();
        getSession().clear();
        return company.getId();
    }

    @Override
    public List<Company> getAll(Pageable pageable) {


        Query resQuery = getQuery();

        int pageNum = pageable.getPageNumber();
        if(pageNum<1){
            pageNum = 1;
        }

        resQuery.setFirstResult((pageNum-1)*pageable.getPageSize());
        resQuery.setMaxResults(pageable.getPageSize());
        return resQuery.getResultList();
    }

    @Override
    public MyPagenation getPageable(Pageable pageable) {
        Query resQuery = getQuery();

        MyPagenation myPagenation = new MyPagenation();

        myPagenation.setTotalElement(resQuery.getResultList().size());
        return myPagenation;
    }

    private Query getQuery(){
        String query = "FROM Company C WHERE C.isDeleted = 0 AND C.id = "+ ApplicationPreferences.getUser().getCompany().getId();
        Query resQuery =  getSession().createQuery(query);

        return resQuery;
    }

    @Override
    public Company getById(int id) {

        String query = "FROM Company C WHERE C.id = "+id+" AND C.isDeleted = 0 AND C.id = "+ ApplicationPreferences.getUser().getCompany().getId();
        return (Company) getSession().createQuery(query).uniqueResult();
    }

    @Override
    public List<Company> getByKeyAndValue(String key, String value) {
        return (List<Company>) getSession().createQuery(
                "from Company where "+key+" = :value"
        ).setParameter("value", value)
                .getResultList();
    }

    @Override
    public boolean delete(int id) {

        Optional<Company> proTmp = Optional.ofNullable(getById(id));
        if(proTmp.isPresent()){
            Company company = proTmp.get();
            company.setActive(false);
            company.setDeleted(true);
            update(company);
            return true;
        }else{
            return false;
        }
    }


}