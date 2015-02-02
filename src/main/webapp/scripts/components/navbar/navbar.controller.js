'use strict';

angular.module('afikomanApp')
    .controller('NavbarController', function ($rootScope, $scope, $location, $state, Auth, Principal) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.isInRole = Principal.isInRole;
        $scope.$state = $state;

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };

        $scope.login = function (role) {
            $rootScope.loginType = role;
        };
    });
