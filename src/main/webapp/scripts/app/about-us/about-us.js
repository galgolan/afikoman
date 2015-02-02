'use strict';

angular.module('afikomanApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('about-us', {
                parent: 'site',
                url: '/about-us',
                data: {
                    roles: []
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/about-us/about-us.html',
                        controller: 'AboutUsController'
                    }
                },
                resolve: {
                    mainTranslatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                        //$translatePartialLoader.addPart('main');
                        return $translate.refresh();
                    }]
                }
            });
    });
