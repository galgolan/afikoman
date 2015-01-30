'use strict';

angular.module('afikomanApp')
    .controller('OrganizationDetailController', function ($scope, $stateParams, Organization) {
        $scope.organization = {};
        $scope.load = function (id) {
            Organization.get({id: id}, function(result) {
              $scope.organization = result;
            });
        };
        $scope.load($stateParams.id);
    });
