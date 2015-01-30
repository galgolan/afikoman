'use strict';

angular.module('afikomanApp')
    .controller('KidDetailController', function ($scope, $stateParams, Kid) {
        $scope.kid = {};
        $scope.load = function (id) {
            Kid.get({id: id}, function(result) {
              $scope.kid = result;
            });
        };
        $scope.load($stateParams.id);
    });
