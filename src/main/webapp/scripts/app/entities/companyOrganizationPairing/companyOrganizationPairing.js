'use strict';

angular.module('afikomanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('companyOrganizationPairing', {
                parent: 'entity',
                url: '/companyOrganizationPairing',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/companyOrganizationPairing/companyOrganizationPairings.html',
                        controller: 'CompanyOrganizationPairingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('companyOrganizationPairing');
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            })
            .state('companyOrganizationPairingDetail', {
                parent: 'entity',
                url: '/companyOrganizationPairing/:id',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/companyOrganizationPairing/companyOrganizationPairing-detail.html',
                        controller: 'CompanyOrganizationPairingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('companyOrganizationPairing');
                        return $translate.refresh();
                    }]
                }
            });
    });
