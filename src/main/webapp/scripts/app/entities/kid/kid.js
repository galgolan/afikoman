'use strict';

angular.module('afikomanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('kid', {
                parent: 'entity',
                url: '/kid',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kid/kids.html',
                        controller: 'KidController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('kid');
                        return $translate.refresh();
                    }]
                }
            })
            .state('kidDetail', {
                parent: 'entity',
                url: '/kid/:id',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/kid/kid-detail.html',
                        controller: 'KidDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('kid');
                        return $translate.refresh();
                    }]
                }
            });
    });
