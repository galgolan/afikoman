'use strict';

angular.module('afikomanApp')
    .controller('AdoptionDetailController', function ($scope, $stateParams, Adoption) {
        $scope.adoption = {};
        $scope.load = function (id) {
            Adoption.get({id: id}, function(result) {
              $scope.adoption = result;
            });
        };
        $scope.load($stateParams.id);
    });
