'use strict';

angular.module('afikomanApp')
    .factory('CompanyOrganizationPairing', function ($resource) {
        return $resource('api/companyOrganizationPairings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
