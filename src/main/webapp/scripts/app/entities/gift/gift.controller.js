'use strict';

angular.module('afikomanApp')
    .controller('GiftController', function ($scope, Gift) {
        $scope.gifts = [];
        $scope.loadAll = function() {
            Gift.query(function(result) {
               $scope.gifts = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Gift.save($scope.gift,
                function () {
                    $scope.loadAll();
                    $('#saveGiftModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.gift = Gift.get({id: id});
            $('#saveGiftModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.gift = Gift.get({id: id});
            $('#deleteGiftConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Gift.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteGiftConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.gift = {name: null, dateAdded: null, inStock: true, price: null, picture: null, metadata: null, id: null};
        };
    });
