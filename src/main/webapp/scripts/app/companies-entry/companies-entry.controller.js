'use strict';

angular.module('afikomanApp')
    .controller('CompaniesEntryController', function ($q, $scope, $state, Gift, Kid, Company, CompanyOrganizationPairing, Adoption, Principal) {
        $scope.kids = [];
        $scope.gifts = [];
        $scope.company = {};
        $scope.kid = {};
        $scope.employee = {};

        var getGiftForKid = function(kid, gifts) {
            return $.grep(gifts, function(gift) { return gift.id == kid.desiredGift; })[0];
        }

        $scope.loadAll = function() {
            $q.all([
                Gift.query().$promise,
                CompanyOrganizationPairing.query().$promise,
                Company.query().$promise,
                Kid.query().$promise,
                Principal.identity(),
                Adoption.query().$promise
                ]).then(function (data) {
                var currCompanyName = data[4].firstName ||  data[4].login;
                $scope.company = $.grep(data[2], function(company) { return company.name == currCompanyName; })[0];

                if (typeof($scope.company) === "undefined") $state.go('error'); // no company listed under logged in account

                // get already made adoptions by this company
                var adoptedKidNames = $.map(data[5], function (adoption) { if (adoption.company == currCompanyName) return adoption.kid; })

                var companyOrganizationPairings = $.grep(data[1], function(pairing) { return pairing.company == currCompanyName; });
                var organizationNames = $.map(companyOrganizationPairings, function(pairing) { return pairing.organization; });

                // take only kids from paired organizations who hasn't been adopted yet
                var kids = $.grep(data[3], function (kid) { return $.inArray(kid.organization, organizationNames) >= 0; });
                kids = $.grep(kids, function (kid) { return $.inArray(kid.firstName, adoptedKidNames) == -1; });

                // match gift to every kid
                var giftIds = $.map(kids, function(kid) { return kid.desiredGift; });
                $scope.gifts = $.grep(data[0], function (gift) { return $.inArray(gift.id, giftIds) >= 0; });
                $.each(kids, function(index, kid) { kid.gift = getGiftForKid(kid, $scope.gifts) });

                // we shuffle the kids array to reduce the chance that the same kid will be selected by two people
                $scope.kids = kids.sort(function() {
                  return .5 - Math.random();
                });

                // if no kids, show no-more-kids modal
                if (kids.length <= 0) {
                    $('#noMoreKidsModal').on('hidden.bs.modal', $scope.noMoreKidsModalHidden)
                    $('#noMoreKidsModal').modal('show');
                }
            });
        };

        $scope.loadAll();

        $scope.selectKid = function (kidId) {            
            $scope.kid = $.grep($scope.kids, function (kid) { return kid.id == kidId; })[0];
        }

        $scope.submit = function () {

            Adoption.query(function (result) {
                var alreadyAdopted = $.grep(result, function (adoption) { return adoption.kidId == $scope.kid.id; });
                if (alreadyAdopted != null && alreadyAdopted.length > 0) {                    
                    $scope.loadAll();
                    $scope.kid = {};
                    $('#saveKidModal').modal('hide');
                    $('#alreadyAdoptedModal').modal('show');
                }
                else {
                    Adoption.save({
                        employeeName: $scope.employee.firstName + ' ' + $scope.employee.lastName, 
                        employeeEmail: $scope.employee.email, 
                        company: $scope.company.name, 
                        organization: $scope.kid.organization, 
                        kid: $scope.kid.firstName, 
                        kidId: $scope.kid.id, 
                        organizationId: null, 
                        companyId: $scope.company.id, 
                        id: null
                    }, function () {
                            $scope.loadAll();
                            $scope.employee = {};
                            $scope.kid = {};
                            $('#saveKidModal').modal('hide');
                    });
                }
            });            
        }

        $scope.submitNoKid = function () {
            Adoption.save({
                employeeName: $scope.employee.firstName + ' ' + $scope.employee.lastName, 
                employeeEmail: $scope.employee.email, 
                company: $scope.company.name,
                companyId: $scope.company.id, 
                id: null}, function () {                    
                    $('#noMoreKidsModal').modal('hide');
                });
        }

        $scope.noMoreKidsModalHidden = function(keyEvent) {
          $state.go('companies-thanks'); // TODO: redirect to thank you page
        }
    });
