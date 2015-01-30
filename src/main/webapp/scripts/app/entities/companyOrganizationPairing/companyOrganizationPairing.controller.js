'use strict';

angular.module('afikomanApp')
    .controller('CompanyOrganizationPairingController', function ($scope, CompanyOrganizationPairing) {
        $scope.companyOrganizationPairings = [];
        $scope.loadAll = function() {
            CompanyOrganizationPairing.query(function(result) {
               $scope.companyOrganizationPairings = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            CompanyOrganizationPairing.save($scope.companyOrganizationPairing,
                function () {
                    $scope.loadAll();
                    $('#saveCompanyOrganizationPairingModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.companyOrganizationPairing = CompanyOrganizationPairing.get({id: id});
            $('#saveCompanyOrganizationPairingModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.companyOrganizationPairing = CompanyOrganizationPairing.get({id: id});
            $('#deleteCompanyOrganizationPairingConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            CompanyOrganizationPairing.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCompanyOrganizationPairingConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.companyOrganizationPairing = {company: null, organization: null, companyId: null, organizationId: null, metadata: null, id: null};
        };
    });
