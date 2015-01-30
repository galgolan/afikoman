'use strict';

angular.module('afikomanApp')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {
        });
    });
