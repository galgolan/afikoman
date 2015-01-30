'use strict';

angular.module('afikomanApp')
    .controller('OrganizationController', function ($scope, Organization, Auth) {
        $scope.organizations = [];
        $scope.loadAll = function() {
            Organization.query(function(result) {
               $scope.organizations = result;
            });
        };
        $scope.loadAll();

        var register = function () {
            var registerAccount = {
                password: 'P455w0rd',
                langKey: 'he',
                login: $scope.organization.name,
                email: $scope.organization.pocEmail,
                dateAdded: new Date()
            };

            Auth.createAccount(registerAccount).then(function () {
                $scope.success = 'OK';
            }).catch(function (response) {
                alert('failed to register');
                $scope.success = null;
                if (response.status === 400 && response.data === 'login already in use') {
                    $scope.errorUserExists = 'ERROR';
                } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                    $scope.errorEmailExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            });
        };

        $scope.create = function () {
            Organization.save($scope.organization,
                function () {
                    $scope.loadAll();
                    $('#saveOrganizationModal').modal('hide');
                    register();
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.organization = Organization.get({id: id});
            $('#saveOrganizationModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.organization = Organization.get({id: id});
            $('#deleteOrganizationConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Organization.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteOrganizationConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.organization = {name: null, dateAdded: null, password: null, pocEmail: null, metadata: null, id: null};
        };
    });
