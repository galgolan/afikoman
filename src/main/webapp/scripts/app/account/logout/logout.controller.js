'use strict';

angular.module('afikomanApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
