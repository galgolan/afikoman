'use strict';

angular.module('afikomanApp')
    .controller('CompanyController', function ($scope, Company, Auth) {
        $scope.companys = [];
        $scope.loadAll = function() {
            Company.query(function(result) {
               $scope.companys = result;
            });
        };
        $scope.loadAll();

        var register = function () {
            var registerAccount = {
                password: 'P455w0rd',
                langKey: 'he',
                login: $scope.company.name,
                email: $scope.company.pocEmail
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
            Company.save($scope.company,
                function () {
                    $scope.loadAll();
                    $('#saveCompanyModal').modal('hide');
                    register();
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.company = Company.get({id: id});
            $('#saveCompanyModal').modal('show');
        };

        $scope.delete = function (id) {
            $scope.company = Company.get({id: id});
            $('#deleteCompanyConfirmation').modal('show');
        };

        $scope.confirmDelete = function (id) {
            Company.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteCompanyConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.clear = function () {
            $scope.company = {name: null, dateAdded: null, password: null, pocEmail: null, metadata: null, description: null, website: null, id: null};
        };
    });
