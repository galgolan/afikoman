'use strict';

angular.module('afikomanApp')
    .controller('AdminLoginController', function ($rootScope, $scope, $state, $timeout, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function () {
            Auth.login({
                username: $scope.username,
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                //$rootScope.back();
                if ($rootScope.loginType == 'Admin')
                    $state.go('home');
                else
                    $state.go('error');
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };
    });
