/**
 * Created by dana on 5/20/2017.
 */

angular.module('WarehouseManager.services',[])
    // A new instance of the function($window) will be provided when popupService will be declared as a controller argument
    .service('popupService',function($window){
        this.showPopup=function(message){
            return $window.confirm(message);
        }
    })

    .factory('User',function($resource){
        return $resource('http://localhost:8082/WarehouseManager/api/Login',{},{
            update: {
                method: 'PUT'
            }
        });
    })

    .factory('Pallet',function($resource){
        return $resource('http://localhost:8082/WarehouseManager/api/Pallets/:id',{id:'@id'},{
            update: {
                method: 'PUT'
            }
        });
    })

    // The value returned by function($resource) will be provided when factoryName (PlannedShipment) will be declared as injectable argument
    .factory('PlannedShipment',function($resource){
        return $resource('http://localhost:8082/WarehouseManager/api/PlannedShipments/:id',{id:'@id'},{
            update: {
                method: 'PUT'
            }
        });
    })

    .factory('UnshippedPlannedShipment',function($resource){
        return $resource('http://localhost:8082/WarehouseManager/api/PlannedShipments/unshipped',{},{
            // getUnshippedPlannedShipments: {
            //     method: 'GET',
            //     isArray: true
            // }
        });
    })

    .factory('UnshippedPallet',function($resource){
        return $resource('http://localhost:8082/WarehouseManager/api/Pallets/unshipped',{},{
            // getUnshippedPallets: {
            //     method: 'GET',
            //     isArray: true
            // }
        });
    })

    .factory('Shipment',function($resource){
        return $resource('http://localhost:8082/WarehouseManager/api/Shipments/:id',{id:'@id'},{
            update: {
                method: 'PUT'
            }
        });
    });