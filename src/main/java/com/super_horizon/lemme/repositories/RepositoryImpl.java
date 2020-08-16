package com.super_horizon.lemme.repositories;

import java.util.*;
import java.lang.reflect.InvocationTargetException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;

public class RepositoryImpl<E> implements IRepository<E> {

    protected final MongoTemplate mongoTemplate;
    private Class<E> documentClass;

    public RepositoryImpl(MongoTemplate mongoTemplate, Class<E> type) {
        this.documentClass = type;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public E findOrCreate(Map<String, String> dynamicQuery) {

        try{
                     
            Query queryDocument = new Query();
            List<Criteria> criteria = new ArrayList<>();    
                     
            // generate query to run it against db 
            for (Map.Entry<String, String> entry : dynamicQuery.entrySet()) {
                String property = (String) entry.getKey();
                String value = (String) entry.getValue();
                criteria.add(Criteria.where(property).is(value));
            }
            
            queryDocument.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));

            // get the collection from db and run the query
            // then add result into final list of documents
            E document = this.mongoTemplate.findOne(queryDocument, this.documentClass);
            
            // if document does not exist, then create one
            if (document == null) {

                E obj = this.documentClass.getConstructor(Map.class).newInstance(dynamicQuery);                   
                E savedDocument = this.mongoTemplate.save(obj);                   
                return savedDocument;

            }
            else {
                return document;
            }
            
        }
        catch (NoSuchMethodException e) {
        }
        catch (InstantiationException e) {
        }
        catch (IllegalAccessException e) {
        }
        catch (InvocationTargetException e) {
        }
        catch (NullPointerException e) {
        }
        catch (IllegalStateException e) {
        }
        catch (UnsupportedOperationException e) {
        }
        catch (ClassCastException e) {         
        }
        catch (IllegalArgumentException e) {        
        }
        catch (ArrayStoreException e) {        
        }
        catch (ExceptionInInitializerError e) {       
        }
        catch (SecurityException e) {       
        }

        return null;

    }

}