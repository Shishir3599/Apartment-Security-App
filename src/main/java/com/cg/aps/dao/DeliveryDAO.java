package com.cg.aps.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.aps.entity.Delivery;
/**
 * 
 * @author Shishir
 * repository for delivery
 *
 */
@Repository
public interface DeliveryDAO extends JpaRepository<Delivery,Integer> {

	@Query("select d from Delivery d where d.personName = :name")
	public List<Delivery> findByPersonName(String name) throws Exception;
}
