'use strict';

angular.module('afikomanApp')
    .controller('CompanyOrganizationPairingDetailController', function ($scope, $stateParams, CompanyOrganizationPairing) {
        $scope.companyOrganizationPairing = {};
        $scope.load = function (id) {
            CompanyOrganizationPairing.get({id: id}, function(result) {
              $scope.companyOrganizationPairing = result;
            });
        };
        $scope.load($stateParams.id);
    });
