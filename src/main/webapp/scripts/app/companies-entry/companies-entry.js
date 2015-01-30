'use strict';

angular.module('afikomanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('companies-entry', {
                parent: 'site',
                url: '/companies-entry',
                data: {
                    roles: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/companies-entry/companies-entry.html',
                        controller: 'CompaniesEntryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('kid');
                        $translatePartialLoader.addPart('company');
                        $translatePartialLoader.addPart('gift');
                        $translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            });
    });
