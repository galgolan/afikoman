'use strict';

angular.module('afikomanApp')
    .factory('Adoption', function ($resource) {
        return $resource('api/adoptions/:id', {}, {
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
