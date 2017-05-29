package com.tgs.warehouse.controller;

import com.tgs.warehouse.entities.ProductPallet;
import com.tgs.warehouse.services.PalletService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Created by dana on 5/13/2017.
 */

@RestController
@RequestMapping("/Pallets")
public class PalletRestController {

    @Autowired
    PalletService palletService;

    @Autowired
    public PalletRestController(PalletService palletService) {
        Objects.requireNonNull(palletService);
        this.palletService = palletService;
    }

    // Create new pallet
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> addPallet(@RequestBody ProductPallet productPallet) {
        try {
            palletService.saveProductPallet(productPallet);
            System.out.println("New Pallet added with REST Controller");
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // Retrieve all pallets from DB
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ProductPallet>> getAllPallets() {
        List<ProductPallet> productPalletList = palletService.getAllPallets();
        return new ResponseEntity<>(productPalletList, HttpStatus.OK);
    }

    // Retrieve Pallet by Id
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<ProductPallet> getPallet(@PathVariable(value = "id") Long id) {
        System.out.println("Retrieving pallet with id " + id);

        ProductPallet foundPallet = palletService.getProductPalletById(id);
        return new ResponseEntity<>(foundPallet, HttpStatus.OK);
    }

    // Retrieve Pallets not included in Shipments
    @RequestMapping(value = "/unshipped",method = RequestMethod.GET)
    public ResponseEntity<List<ProductPallet>> getUnshippedPallets() {

        List<ProductPallet> foundPallets = palletService.getPalletsWithoutShipment();
        return new ResponseEntity<>(foundPallets, HttpStatus.OK);
    }

//    // Retrieve pallets by description
//    @RequestMapping(value = "/{description}",method = RequestMethod.GET)
//    public ResponseEntity<List<ProductPallet>> getPalletsByDescription(@PathVariable(value = "description") String description) {
//
//        System.out.println("Fetching pallets with description " + description);
//
//        List<ProductPallet> productPalletList = palletService.search(description);
//        return new ResponseEntity<>(productPalletList, HttpStatus.OK);
//    }

    // Delete pallet
    @RequestMapping(value = "/{palletId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePallet(@PathVariable("palletId") Long palletId) {

        //Long productPalletId = Long.valueOf(palletId);
        try {
            palletService.deleteProductPallet(palletId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
