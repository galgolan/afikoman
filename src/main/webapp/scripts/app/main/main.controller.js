'use strict';

angular.module('afikomanApp')
    .controller('MainController', function ($rootScope, $scope, $state, Principal) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;

            if ($scope.isAuthenticated() && !Principal.isInRole('ROLE_ADMIN')) {
                if ($rootScope.loginType == 'Company')
                    $state.go('companies-entry');
                else if ($rootScope.loginType == 'Organization')
                    $state.go('kids-entry');
            }
        });

        $scope.login = function (role) {
            $rootScope.loginType = role;
            if (role === 'Admin')
                $state.go('admin-login');
            else
                $state.go('login');
        };
    });
