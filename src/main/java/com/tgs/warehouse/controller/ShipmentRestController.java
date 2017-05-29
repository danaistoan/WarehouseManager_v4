package com.tgs.warehouse.controller;

import com.tgs.warehouse.entities.PlannedShipment;
import com.tgs.warehouse.entities.Shipment;
import com.tgs.warehouse.services.ShipmentService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Created by dana on 5/15/2017.
 */
@RestController
@RequestMapping("/Shipments")
public class ShipmentRestController {

    @Autowired
    ShipmentService shipmentService;

    @Autowired
    public ShipmentRestController(ShipmentService shipmentService) {
        Objects.requireNonNull(shipmentService);
        this.shipmentService = shipmentService;
    }

    // TODO - Create new Shipment based on shipment Json received from UI
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addShipment(@RequestBody Shipment shipment) {
        try {
            shipmentService.saveShipment(shipment);
            System.out.println("New Shipment added with REST Controller");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Get all shipments
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Shipment>> getAllShipments() {
        List<Shipment> shipmentList = shipmentService.getAllShipments();
        return new ResponseEntity<>(shipmentList, HttpStatus.OK);
    }

    // TODO - Update Shipment based on shipment Json received from UI
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateShipment(@RequestBody Shipment shipment){
        try {
            shipmentService.updateShipment(shipment);
            System.out.println("Shipment updated with REST Controller");
            return new ResponseEntity<Shipment>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete Shipment
    @RequestMapping(value = "/{shipmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteShipment(@PathVariable("shipmentId") Long shipmentId) {

        try {
            shipmentService.deleteShipment(shipmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Retrieve Planned Shipment by Id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Shipment> getShipment(@PathVariable Long id) {
        System.out.println("Retrieving shipment with id " + id);

        Shipment foundShipment = shipmentService.searchShipmentById(id);
        return new ResponseEntity<>(foundShipment, HttpStatus.OK);
    }

//    // Retrieve Shipments by Planned Shipment id
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<Shipment>> getShipments(@RequestParam(value = "search[value]", required = false) String searchValue) {
//
//        List<Shipment> foundShipmentList;
//
//        if(searchValue != null && searchValue != ""){
//            foundShipmentList = shipmentService.searchShipments(searchValue);
//        } else {
//            foundShipmentList = shipmentService.getAllShipments();
//        }
//
//        return new ResponseEntity<>(foundShipmentList, HttpStatus.OK);
//    }
}
