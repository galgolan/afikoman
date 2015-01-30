'use strict';

angular.module('afikomanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('admin-login', {
                parent: 'account',
                url: '/admin-login',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/account/admin-login/admin-login.html',
                        controller: 'AdminLoginController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('login');
                        return $translate.refresh();
                    }]
                }
            });
    });
