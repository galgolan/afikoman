'use strict';

angular.module('afikomanApp')
    .controller('AdoptionController', function ($scope, Adoption) {
        $scope.adoptions = [];
        $scope.loadAll = function() {
            Adoption.query(function(result) {
               $scope.adoptions = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            Adoption.save($scope.adoption,
                function () {
                    $scope.loadAll();
                    $('#saveAdoptionModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.adoption = Adoption.get({id: id});
            $('#saveAdoptionModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.adoption = Adoption.get({id: id});
            $('#deleteAdoptionConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Adoption.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteAdoptionConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.adoption = {employeeName: null, employeeEmail: null, company: null, organization: null, kid: null, kidId: null, organizationId: null, companyId: null, id: null};
        };
    });
