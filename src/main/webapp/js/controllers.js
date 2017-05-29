/**
 * Created by dana on 5/20/2017.
 */
angular.module('WarehouseManager.controllers',[])
    .controller('PalletListController',function($scope,$state,popupService,$window,Pallet){

    $scope.pallets=Pallet.query();

    $scope.deletePallet=function(pallet){
        if(popupService.showPopup('Really delete this?')){
            pallet.$delete(removePalletFromUIList($scope, pallet));
        }
    }

}).controller('PalletViewController',function($scope,$state,$stateParams,Pallet) {

    $scope.pallet = {};
    $scope.packages = [];

    Pallet.get({id: $stateParams.id}).$promise.then(function(data) {
        $scope.pallet = data;
        console.log($scope.pallet);
        $scope.packages = $scope.pallet.packages;
        console.log($scope.packages);
    });

    $scope.cancel = function(){
        $state.go('pallets');
    };

}).controller('PalletCreateController',function($scope,$state,$stateParams,Pallet){

    $scope.pallet = new Pallet();
    $scope.pallet.packages = [];
    $scope.pallet.packages[0] = {};
    $scope.pallet.packages[1] = {};

    $scope.addPackage=function(){
        $scope.pallet.packages.push({});
        return false;
    };

    $scope.savePallet=function(){
        $scope.pallet.$save(function(){
            $state.go('pallets');
        });
    };

    $scope.cancel = function(){
        $state.go('pallets');
    };

}).controller('PalletEditController',function($scope,$state,$stateParams,Pallet){

    $scope.pallet = {};
    $scope.packages = [];

    Pallet.get({id: $stateParams.id}).$promise.then(function(data) {
        $scope.pallet = data;
        $scope.packages = $scope.pallet.packages;
    });

    console.log("pallet " + $scope.packages);

    $scope.addPackage=function(){
        $scope.pallet.packages.push({});
    }

    $scope.savePallet=function(){
        $scope.pallet.$update(function(){
            $state.go('pallets');
        });
    };

    $scope.loadPallet=function(){
        $scope.pallet=Pallet.get({id:$stateParams.id});
    };

    $scope.cancel = function(){
        $state.go('viewPallet');
    };

    $scope.loadPallet();

})

.controller('PlannedShipmentListController',function($scope,$state,popupService,$window,PlannedShipment){

    $scope.plannedShipments=PlannedShipment.query();

    $scope.deletePlannedShipment=function(plannedShipment){
        if(popupService.showPopup('Really delete this?')){
            plannedShipment.$delete(removePlShpmFromUIList($scope, plannedShipment));
        }
    }

}).controller('PlannedShipmentViewController',function($scope,$state,$stateParams,PlannedShipment){

    $scope.plannedShipment=PlannedShipment.get({id:$stateParams.id});

    $scope.cancel = function(){
        $state.go('plannedShipments');
    };


}).controller('PlannedShipmentCreateController',function($scope,$state,$stateParams,PlannedShipment){

    $scope.plannedShipment=new PlannedShipment();

    $scope.addPlannedShipment=function(){
        $scope.plannedShipment.$save(function(){
            $state.go('plannedShipments');
        });
    };

    $scope.cancel = function(){
        $state.go('plannedShipments');
    };

}).controller('PlannedShipmentEditController',function($scope,$state,$stateParams,PlannedShipment) {

    $scope.updatePlannedShipment = function () {
        $scope.plannedShipment.$update(function () {
            $state.go('plannedShipments');
        });
    };

    $scope.loadPlannedShipment = function () {
        $scope.plannedShipment = PlannedShipment.get({id: $stateParams.id});
    };

    $scope.cancel = function(){
        $state.go('viewPlannedShipment');
    };

    $scope.loadPlannedShipment();

}).controller('ShipmentListController',function($scope,$state,popupService,$window,Shipment){

    $scope.shipments=Shipment.query();

    $scope.deleteShipment=function(shipment){
        if(popupService.showPopup('Really delete this?')){
            shipment.$delete(function(){
                $window.location.href='';
            });
        }
    }

}).controller('ShipmentViewController',function($scope,$state, $stateParams,Shipment){

    $scope.shipment=Shipment.get({id:$stateParams.id});

    $scope.cancel = function(){
        $state.go('shipments');
    };


}).controller('ShipmentCreateController',function($scope,$state,$stateParams,Shipment,PlannedShipment,Pallet,UnshippedPlannedShipment,UnshippedPallet){

    $scope.plannedShipments = [];
    $scope.productPalletList = [];

    $scope.shipment = new Shipment();
    $scope.shipment.productPalletList = [];

    $scope.addPallet=function(){
        $scope.shipment.productPalletList.push({ id: $scope.productPalletList[0].id });
    };

    $scope.saveShipment = function(){
        $scope.shipment.$save(function(){
            $state.go('shipments');
        });
    };

    UnshippedPlannedShipment.query().$promise.then(function(data) {
        $scope.plannedShipments = data;
        $scope.shipment.plannedShipmentId = $scope.plannedShipments[0].id;
    });

    UnshippedPallet.query().$promise.then(function(data){
        $scope.productPalletList = data;
        $scope.shipment.productPalletList.push({ id: $scope.productPalletList[0].id });
    });

    $scope.cancel = function(){
        $state.go('shipments');
    };

}).controller('ShipmentEditController',function($scope,$state,$stateParams,Shipment,UnshippedPallet,$window){

    $scope.plannedShipments = [];
    $scope.productPalletList = [];
    $scope.newProductPalletList = [];

    $scope.shipment = new Shipment();
    $scope.shipment.productPalletList = [];

    $scope.addPallet = function(){
        $scope.newProductPalletList.push({ id: $scope.unshippedProductPalletList[0].id });
    };

    $scope.removePallet = function($event){
        var palletId = parseInt($event.currentTarget.id);

        $scope.shipment.productPalletList = $scope.shipment.productPalletList.filter(function(el) {
            return el.id !== palletId;
        });
    };

    $scope.saveShipment = function(){
        $scope.shipment.productPalletList.push.apply($scope.shipment.productPalletList, $scope.newProductPalletList);
        $scope.shipment.$update(function(){
            $state.go('shipments');
        });
    };

    $scope.loadShipment = function(){
        Shipment.get({id: $stateParams.id}).$promise.then(function(data) {
            $scope.shipment = data;
        });
    };

    UnshippedPallet.query().$promise.then(function(data){
        $scope.unshippedProductPalletList = data;
    });

    // TODO: find a better way to go back to previous page depending on shipmentId
    $scope.cancel = function(){
        var url = 'WarehouseManager/#/shipments/' + $scope.shipment.id + '/view';
        $window.location.href = url;
    };

    $scope.loadShipment();

}).controller('LoginController',function($rootScope,$scope,$state,User) {

    $scope.user = new User();

    $scope.login = function(){
        $scope.user.$update(function (data) {
            $rootScope.user = data;
            $state.go('pallets');
        });
    };

});

// Remove deleted Planned Shipment from the JS PlannedShipment List, without calling the server again for the updated list
function removePlShpmFromUIList($scope, plannedShipment) {
    for (var i = 0; i < $scope.plannedShipments.length; i++) {
        if ($scope.plannedShipments[i].id === plannedShipment.id) {
            $scope.plannedShipments.splice(i, 1);
        }
    }
}

function removePalletFromUIList($scope, pallet) {
    for (var i = 0; i < $scope.pallets.length; i++) {
        if ($scope.pallets[i].id === pallet.id) {
            $scope.pallets.splice(i, 1);
        }
    }
}

function getSelectedPallets($scope) {
    for (var i = 0; i < $scope.selectedPallets.length; i++) {
        $scope.shipment.productPalletList.push(selectedPallets[i]);
    }
}