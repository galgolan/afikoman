'use strict';

angular.module('afikomanApp')
    .factory('Organization', function ($resource) {
        return $resource('api/organizations/:id', {}, {
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
