'use strict';

angular.module('afikomanApp')
    .factory('Company', function ($resource) {
        return $resource('api/companys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dateAdded = new Date(data.dateAdded);
                    return data;
                }
            }
        });
    });
