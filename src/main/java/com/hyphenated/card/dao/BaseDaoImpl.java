/*
The MIT License (MIT)

Copyright (c) 2013 Jacob Kanipe-Illig

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
package com.hyphenated.card.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



public class BaseDaoImpl<T> implements BaseDao<T> {

	@PersistenceContext
	private EntityManager entityManager;
	
	private final Class<T> persistentClass;
	
	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	protected EntityManager getSession(){
		return entityManager;
	}
	
	@SuppressWarnings("unchecked")
	public T findById(Serializable id){
		return (T) getSession().find(persistentClass, id);
	}

	@Override
	public Class<T> getEntityClass() {
		return persistentClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return getSession().createQuery("from " + persistentClass.getSimpleName(), persistentClass).getResultList();
	}
	
	@Override
	public void remove(T objToRemove){
		getSession().remove(objToRemove);
	}
	
	@Override
	public T save(T objToSave){
		if (getSession().contains(objToSave)) return objToSave;
        Object id = getSession().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(objToSave);
        if (id != null && !(id instanceof Number && ((Number) id).longValue() == 0))
            return getSession().merge(objToSave);
		getSession().persist(objToSave);
		return objToSave;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T merge(T objToMerge){
		return (T) getSession().merge(objToMerge);
	}
}
