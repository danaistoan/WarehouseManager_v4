var warehouseApp = angular.module('WarehouseManager', ['ui.router','ngResource','WarehouseManager.controllers','WarehouseManager.services']);

warehouseApp.config(function($stateProvider,$httpProvider) {
    $stateProvider
        .state('login',{
            url:'/',
            templateUrl:'WarehouseManager/partials/login.html',
            controller:'LoginController'
        })
        // .state('welcome',{
        //     url:'/',
        //     templateUrl:'WarehouseManager/partials/welcome.html',
        //     controller:'LoginController'
        // })
        .state('pallets',{
            url:'/pallets',
            templateUrl:'WarehouseManager/partials/pallets.html',
            controller:'PalletListController'
        })
        .state('viewPallet',{
            url:'/pallets/:id/view',
            templateUrl:'WarehouseManager/partials/pallet-view.html',
            controller:'PalletViewController'
        })
        .state('newPallet',{
            url:'/pallets/new',
            templateUrl:'WarehouseManager/partials/pallet-add.html',
            controller:'PalletCreateController'
        })
        .state('editPallet',{
            url:'/pallets/:id/edit',
            templateUrl:'WarehouseManager/partials/pallet-edit.html',
            controller:'PalletEditController'
        })
        .state('plannedShipments',{
            url:'/plannedShipments',
            templateUrl:'WarehouseManager/partials/plannedShipments.html',
            controller:'PlannedShipmentListController'
        })
        .state('viewPlannedShipment',{
            url:'/plannedShipments/:id/view',
            templateUrl:'WarehouseManager/partials/plannedShipment-view.html',
            controller:'PlannedShipmentViewController'
        })
        .state('newPlannedShipment',{
            url:'/plannedShipments/new',
            templateUrl:'WarehouseManager/partials/plannedShipment-add.html',
            controller:'PlannedShipmentCreateController'
        })
        .state('editPlannedShipment',{
            url:'/plannedShipments/:id/edit',
            templateUrl:'WarehouseManager/partials/plannedShipment-edit.html',
            controller:'PlannedShipmentEditController'
        })
        .state('shipments',{
            url:'/shipments',
            templateUrl:'WarehouseManager/partials/shipments.html',
            controller:'ShipmentListController'
        })
        .state('viewShipment',{
            url:'/shipments/:id/view',
            templateUrl:'WarehouseManager/partials/shipment-view.html',
            controller:'ShipmentViewController'
        })
        .state('newShipment',{
            url:'/shipments/new',
            templateUrl:'WarehouseManager/partials/shipment-add.html',
            controller:'ShipmentCreateController'
        })
        .state('editShipment',{
            url:'/shipments/:id/edit',
            templateUrl:'WarehouseManager/partials/shipment-edit.html',
            controller:'ShipmentEditController'
    })
}).run(function($state){
    $state.go('pallets');
});

