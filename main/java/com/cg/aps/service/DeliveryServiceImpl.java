package com.cg.aps.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.aps.dao.DeliveryDAO;
import com.cg.aps.entity.Delivery;
import com.cg.aps.exception.DatabaseException;
import com.cg.aps.exception.DuplicateRecordException;
import com.cg.aps.exception.RecordNotFoundException;
/**
 * 
 * @author Shishir
 * delivery service layer implementation of service interface
 *
 */
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService{

	@Autowired
	private DeliveryDAO deliveryDao;
	
	@Override
	public Integer addDelivery(Delivery delivery) throws DuplicateRecordException {
		try {
			deliveryDao.save(delivery);
			return delivery.getDeliveryId();
		}catch(DataAccessException e) {
			throw new DuplicateRecordException(e.getMessage());
		}catch(Exception e) {
			throw new DuplicateRecordException(e.getMessage());
		}
	}

	@Override
	public void updateDelivery(Delivery delivery) throws RecordNotFoundException {
		try {			
			deliveryDao.save(delivery);
		}catch(DataAccessException e) {
			throw new RecordNotFoundException(e.getMessage());
		}catch(Exception e) {
			throw new RecordNotFoundException(e.getMessage());
		}
		
	}

	@Override
	public void deleteDelivery(Delivery delivery) throws RecordNotFoundException {
		try {			
			deliveryDao.delete(delivery);
		}catch(DataAccessException e) {
			throw new RecordNotFoundException(e.getMessage());
		}catch(Exception e) {
			throw new RecordNotFoundException(e.getMessage());
		}
		
	}


	@Override
	public Delivery findByPk(Integer id) throws RecordNotFoundException {
		try {			
			Optional<Delivery> optional = deliveryDao.findById(id);
			if(optional.isPresent()) {
				return optional.get();
			}else {
				throw new Exception("Invalid id");
			}
		}catch(DataAccessException e) {
			throw new RecordNotFoundException(e.getMessage());
		}catch(Exception e) {
			throw new RecordNotFoundException(e.getMessage());
		}
	}

	@Override
	public List<Delivery> search(Integer pageNo, Integer pageSize) throws DatabaseException {
		try {	
			PageRequest paging = PageRequest.of(pageNo, pageSize);
			Page<Delivery> pagedResult = deliveryDao.findAll(paging);
			if(pagedResult.hasContent()) {
				return pagedResult.getContent();
			}else {
				throw new Exception("Invalid pageNo and pageSize");
			}
		}catch(DataAccessException e) {
			throw new DatabaseException(e.getMessage());
		}catch(Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public List<Delivery> search() throws DatabaseException {
		try {			
			return deliveryDao.findAll();
		}catch(DataAccessException e) {
			throw new DatabaseException(e.getMessage());
		}catch(Exception e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	@Override
	public List<Delivery> findByPersonName(String name) throws RecordNotFoundException {
		try {			
			return deliveryDao.findByPersonName(name);
		}catch(DataAccessException e) {
			throw new RecordNotFoundException(e.getMessage());
		}catch(Exception e) {
			throw new RecordNotFoundException(e.getMessage());
		}
	}

}
