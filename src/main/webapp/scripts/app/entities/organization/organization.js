'use strict';

angular.module('afikomanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('organization', {
                parent: 'entity',
                url: '/organization',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organization/organizations.html',
                        controller: 'OrganizationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organization');
                        return $translate.refresh();
                    }]
                }
            })
            .state('organizationDetail', {
                parent: 'entity',
                url: '/organization/:id',
                data: {
                    roles: ['ROLE_ADMIN']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/organization/organization-detail.html',
                        controller: 'OrganizationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('organization');
                        return $translate.refresh();
                    }]
                }
            });
    });
