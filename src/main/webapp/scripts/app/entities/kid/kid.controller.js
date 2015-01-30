'use strict';

angular.module('afikomanApp')
    .controller('KidController', function ($scope, Kid) {
        $scope.kids = [];
        $scope.loadAll = function() {
            Kid.query(function(result) {
               $scope.kids = result;
            });
        };
        $scope.loadAll();

        $scope.create = function () {
            // add auto-generated fields
            $scope.kid.dateAdded = new Date();
            
            Kid.save($scope.kid,
                function () {
                    $scope.loadAll();
                    $('#saveKidModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.kid = Kid.get({id: id});
            $('#saveKidModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.kid = Kid.get({id: id});
            $('#deleteKidConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Kid.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteKidConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.kid = {firstName: null, lastName: null, dateAdded: null, organization: null, desiredGift: null, personalNote: null, metadata: null, picture: null, id: null};
        };
    });
