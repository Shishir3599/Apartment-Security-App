package com.cg.aps.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cg.aps.entity.Delivery;
import com.cg.aps.entity.DomesticHelp;
import com.cg.aps.entity.GuardShift;
import com.cg.aps.entity.SecurityAlert;
import com.cg.aps.entity.Visitor;
import com.cg.aps.exception.DatabaseException;
import com.cg.aps.exception.DuplicateRecordException;
import com.cg.aps.exception.RecordNotFoundException;
import com.cg.aps.service.DeliveryService;
import com.cg.aps.service.DomesticHelpService;
import com.cg.aps.service.GuardShiftService;
import com.cg.aps.service.SecurityAlertService;
import com.cg.aps.service.VisitorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/guard")
public class GuardController {
  @Autowired
	private DeliveryService deliveryService;
	/**
	 * @author Shishir
	 * @param deliveryId - id of delivery
	 * @return delivery
	 */
	@ApiOperation(value = "get delivery by Id",
			response = Delivery.class,
			tags = "get-delivery",
			consumes = "deliveryId",
			httpMethod = "GET")
	@GetMapping("/delivery/{deliveryId}")
	public ResponseEntity<Delivery> getDeliveryById(@PathVariable Integer deliveryId){
		try {
			Delivery delivery = deliveryService.findByPk(deliveryId);
			return new ResponseEntity<>(delivery,HttpStatus.OK);
		}catch(RecordNotFoundException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/**
	 * @author Shishir
	 * @return all deliveries
	 */
	@ApiOperation(value = "get all deliveries",
			response = Delivery.class,
			tags = "get-all-deliveries",
			httpMethod = "GET")
	@GetMapping("/delivery")
	public ResponseEntity<List<Delivery>> getAllDeliveries(){
		try {
			List<Delivery> deliveryList = deliveryService.search();
			return new ResponseEntity<>(deliveryList,HttpStatus.OK);
		}catch(DatabaseException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/**
	 * @author Shishir
	 * @param pageNo - page number
	 * @param pageSize - page size
	 * @return deliveries of the pageNo with pageSize
	 */
	@ApiOperation(value = "get deliveries by page no and page size",
			response = Delivery.class,
			tags = "get-delivery",
			consumes = "page no and page size",
			httpMethod = "GET")
	@GetMapping("/delivery/{pageNo}/{pageSize}")
	public ResponseEntity<List<Delivery>> getDeliveries(@PathVariable Integer pageNo,@PathVariable Integer pageSize){
		try {
			List<Delivery> deliveryList = deliveryService.search(pageNo, pageSize);
			return new ResponseEntity<>(deliveryList,HttpStatus.OK);
		}catch(DatabaseException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/**
	 * @author Shishir
	 * @param delivery - delivery object
	 * @return adds delivery
	 */
	@ApiOperation(value = "add delivery",
			response = Integer.class,
			tags = "add-delivery",
			consumes = "delivery object",
			httpMethod = "POST")
	@PostMapping("/delivery")
	public ResponseEntity<Integer> addDelivery(@RequestBody Delivery delivery) {
		try {
			Integer deliveryId = deliveryService.addDelivery(delivery);
			return new ResponseEntity<>(deliveryId,HttpStatus.OK);
		}catch(DuplicateRecordException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/**
	 * @author Shishir
	 * @param delivery - delivery object
	 * @return updating delivery
	 */
	@ApiOperation(value = "update delivery",
			response = String.class,
			tags = "update-delivery",
			consumes = "delivery object",
			httpMethod = "PUT")
	@PutMapping("/delivery")
	public ResponseEntity<String> updateDelivery(@RequestBody Delivery delivery) {
		try {
			deliveryService.updateDelivery(delivery);
			return new ResponseEntity<>("updated successfully",HttpStatus.OK);
		}catch(RecordNotFoundException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
	
	/**
	 * @author Shishir
	 * @param delivery - delivery object
	 * @return deleting delivery
	 */
	@ApiOperation(value = "delete delivery",
			response = String.class,
			tags = "delete-delivery",
			consumes = "delivery object",
			httpMethod = "DELETE")
	@DeleteMapping("/delivery")
	public ResponseEntity<String> deleteDelivery(@RequestBody Delivery delivery) {
		try {
			deliveryService.deleteDelivery(delivery);
			return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
		}catch(RecordNotFoundException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,e.getMessage());
		}
	}
