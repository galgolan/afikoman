'use strict';

angular.module('afikomanApp')
    .controller('KidsEntryController', function ($scope, Gift, Kid, Principal) {
        $scope.kid = {};
        $scope.gifts = [];
        $scope.loadAll = function() {
            Gift.query(function(result) {
               $scope.gifts = result;
            });
        };

        $scope.loadAll();

        $scope.create = function () {
            // add auto-generated fields
            $scope.kid.dateAdded = new Date();
            Principal.identity().then(function(account) {
                $scope.settingsAccount = account;
                $scope.kid.organization = account.login;
                Kid.save($scope.kid,
                function () {
                    $('#saveKidModal').modal('hide');
                    $scope.clear();
                    $('#approvalNoticeModal').modal('show');
                });
            });
        };

        $scope.clear = function () {
            $scope.kid = {firstName: null, lastName: null, dateAdded: null, organization: null, desiredGift: null, personalNote: null, metadata: null, picture: null, id: null};
        };

        $scope.selectGift = function (giftId) {
            $scope.clear();
            $scope.kid.desiredGift = giftId;
        }

        $scope.clear();
    });
