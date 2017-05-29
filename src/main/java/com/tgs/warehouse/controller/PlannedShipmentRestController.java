package com.tgs.warehouse.controller;

import com.tgs.warehouse.entities.PlannedShipment;
import com.tgs.warehouse.services.PlannedShipmentService;
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
@RequestMapping("/PlannedShipments")
public class PlannedShipmentRestController {

    PlannedShipmentService plannedShipmentService;

    @Autowired
    public PlannedShipmentRestController(PlannedShipmentService plannedShipmentService) {
        Objects.requireNonNull(plannedShipmentService);
        this.plannedShipmentService = plannedShipmentService;
    }

    // Create new Planned Shipment
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addPlannedShipment(@RequestBody PlannedShipment plannedShipment) {
        try {
            plannedShipmentService.savePlannedShipment(plannedShipment);
            System.out.println("New Planned Shipment added with REST Controller");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    // Retrieve Planned Shipments not included in Shipments
//    @RequestMapping(method = RequestMethod.GET)
//    public ResponseEntity<List<PlannedShipment>> getPlannedShipments() {
//        List<PlannedShipment> plannedShipmentList = plannedShipmentService.getPlannedShipmentsWithoutShipment();
//        return new ResponseEntity<>(plannedShipmentList, HttpStatus.OK);
//    }

    // Retrieve all Planned Shipments
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<PlannedShipment>> getAllPlannedShipments() {
        List<PlannedShipment> plannedShipmentList = plannedShipmentService.getAllPlannedShipments();
        return new ResponseEntity<>(plannedShipmentList, HttpStatus.OK);
    }

    // Retrieve Planned Shipment by Id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<PlannedShipment> getPlannedShipment(@PathVariable(value = "id") Long id) {
        System.out.println("Retrieving planned shipment with id " + id);

        PlannedShipment foundPlannedShipment = plannedShipmentService.getPlannedShipment(id);
        return new ResponseEntity<>(foundPlannedShipment, HttpStatus.OK);
    }

    // Retrieve Planned Shipments not included in Shipments
    @RequestMapping(value = "/unshipped",method = RequestMethod.GET)
    public ResponseEntity<List<PlannedShipment>> getUnshippedPlannedShipments() {

        List<PlannedShipment> foundPlannedShipments = plannedShipmentService.getPlannedShipmentsWithoutShipment();
        return new ResponseEntity<>(foundPlannedShipments, HttpStatus.OK);
    }

    // Update Planned Shipment - DAO method called by the Service performs SaveOrUpdate
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePlannedShipment(@RequestBody PlannedShipment plannedShipment){
        try {
            plannedShipmentService.savePlannedShipment(plannedShipment);
            System.out.println("Planned Shipment updated with REST Controller");
            return new ResponseEntity<PlannedShipment>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete Planned Shipment
    @RequestMapping(value = "/{plannedShipmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePlannedShipment(@PathVariable("plannedShipmentId") Long plannedShipmentId) {
        //Long productPalletId = Long.valueOf(palletId);
        try {
            plannedShipmentService.deletePlannedShipment(plannedShipmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
