'use strict';

angular.module('afikomanApp')
    .controller('GiftDetailController', function ($scope, $stateParams, Gift) {
        $scope.gift = {};
        $scope.load = function (id) {
            Gift.get({id: id}, function(result) {
              $scope.gift = result;
            });
        };
        $scope.load($stateParams.id);
    });
