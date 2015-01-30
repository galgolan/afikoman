'use strict';

angular.module('afikomanApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


