'use strict';

angular.module('afikomanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('gift', {
                parent: 'entity',
                url: '/gift',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gift/gifts.html',
                        controller: 'GiftController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gift');
                        return $translate.refresh();
                    }]
                }
            })
            .state('giftDetail', {
                parent: 'entity',
                url: '/gift/:id',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/gift/gift-detail.html',
                        controller: 'GiftDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('gift');
                        return $translate.refresh();
                    }]
                }
            });
    });
