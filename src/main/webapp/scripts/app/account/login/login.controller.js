'use strict';

angular.module('afikomanApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, Auth) {
        $scope.user = {};
        $scope.errors = {};

        $scope.rememberMe = true;
        $timeout(function (){angular.element('[ng-model="username"]').focus();});
        $scope.login = function () {
            Auth.login({
                username: $scope.username.toLowerCase(),
                password: 'P455w0rd',
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                //$rootScope.back();
                if ($rootScope.loginType == 'Company')
                    $state.go('companies-entry');
                else if ($rootScope.loginType == 'Organization')
                    $state.go('kids-entry');
                else
                    $state.go('error');
            }).catch(function () {
                $scope.authenticationError = true;
            });
        };

        $scope.isCompany = function() {
            return ($rootScope.loginType == 'Company');
        }
    });
