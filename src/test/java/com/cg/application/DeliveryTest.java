package com.cg.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.cg.aps.application.ApartmentSecurityAppApplication;
import com.cg.aps.entity.Delivery;
import com.cg.aps.exception.DuplicateRecordException;
import com.cg.aps.exception.RecordNotFoundException;
import com.cg.aps.service.DeliveryService;

@SpringBootTest(classes = ApartmentSecurityAppApplication.class)
@Transactional
@Rollback(true)
public class DeliveryTest {

	@Autowired
	private DeliveryService deliveryService;
	
	public Delivery addDelivery() throws DuplicateRecordException, RecordNotFoundException {
		Delivery delivery = new Delivery();
		delivery.setPersonName("Rohit");
		delivery.setDeliveryDateTime(LocalDateTime.now());
		delivery.setStatus("delivered");
		Integer id = deliveryService.addDelivery(delivery);
		return deliveryService.findByPk(id);
	}
	

	/*
	 * check add delivery
	 */
	@Test
	public void testAddDelivery() throws DuplicateRecordException, RecordNotFoundException {
		Delivery delivery = addDelivery();
		assertEquals("Rohit", delivery.getPersonName());
		assertEquals("delivered",delivery.getStatus());
	}
	
	/*
	 * check edit delivery
	 */
	@Test
	public void testEditDelivery() throws DuplicateRecordException, RecordNotFoundException {
		Delivery delivery = addDelivery();
		delivery.setPersonName("Kumar");
		deliveryService.updateDelivery(delivery);
		assertEquals("Kumar",deliveryService.findByPk(delivery.getDeliveryId()).getPersonName());
	}
	
	/*
	 * check delete delivery
	 */
	@Test
	public void testDeleteDelivery() throws DuplicateRecordException, RecordNotFoundException {
		Delivery delivery = addDelivery();
		deliveryService.deleteDelivery(delivery);
		assertThrows(RecordNotFoundException.class, ()->{
			deliveryService.findByPk(delivery.getDeliveryId());
			});
	}
}
