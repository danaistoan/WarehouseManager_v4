package com.tgs.warehouse.services;

import com.tgs.warehouse.dao.PalletDAO;
import com.tgs.warehouse.dao.PlannedShipmentDAO;
import com.tgs.warehouse.dao.ShipmentDAO;
import com.tgs.warehouse.entities.PlannedShipment;
import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.entities.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by dana on 5/7/2017.
 */
@Service
public class ShipmentService {

    private ShipmentDAO shipmentDAO;
    private PlannedShipmentDAO plannedShipmentDAO;
    private PalletDAO palletDAO;

    @Autowired
    public ShipmentService(ShipmentDAO shipmentDAO, PlannedShipmentDAO plannedShipmentDAO, PalletDAO palletDAO) {
        Objects.requireNonNull(shipmentDAO);
        Objects.requireNonNull(plannedShipmentDAO);
        this.shipmentDAO = shipmentDAO;
        this.plannedShipmentDAO = plannedShipmentDAO;
        this.palletDAO = palletDAO;
    }

    // SaveShipment after checking the completed status
    public void saveShipment(Shipment shipment){

        shipmentDAO.insertShipment(shipment);
    }

    // Search shipment by PlannedShipmentID
    public List<Shipment> searchShipments(String searchValue){

        Long planned_shipment_id = Long.valueOf(searchValue);
        List<Shipment> resultShipments = shipmentDAO.searchShipments(planned_shipment_id);

        return resultShipments;
    }

    public Shipment searchShipmentById(Long id){

        Shipment foundShipment = shipmentDAO.getShipmentById(id);

        return foundShipment;
    }

    // Retrieve all shipments from DB
    public List<Shipment> getAllShipments() {

        List<Shipment> allShipmentsList = shipmentDAO.getAllShipments();
        return allShipmentsList;
    }

    // Deletes shipment by ShipmentID
    public boolean deleteShipment(Long shipmentId) {

        boolean shpmDeleteSuccess = shipmentDAO.deleteShipment(shipmentId);
        return shpmDeleteSuccess;
    }

    // Update shipment by retrieving first the existent shipment in DB
    public void updateShipment(Shipment shipment){

        boolean completed = false;
        PlannedShipment plannedShipment = plannedShipmentDAO.getPlannedShipmentById(shipment.getPlannedShipmentId());
        int plannedShipmentQty = plannedShipment.getQuantity();
        int noOfPalletsToShip = shipment.getProductPalletList().size();
        if(noOfPalletsToShip == plannedShipmentQty){
            completed = true;
        }

        Shipment oldShipment = shipmentDAO.getShipmentById(shipment.getId());

        List<ProductPallet> newPalletList = new ArrayList<ProductPallet>();
        for(ProductPallet pallet : shipment.getProductPalletList()){
            ProductPallet newPallet = palletDAO.getProductPalletById(pallet.getId());
            newPalletList.add(newPallet);
        }

        oldShipment.setProductPalletList(newPalletList);
        oldShipment.setCompleted(completed);
        shipmentDAO.updateShipment(oldShipment);
    }
}
