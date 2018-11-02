$(document).ready(function () {

});

UserController = {};

UserController.addUser = function() {
    requestPost('/admin/users/add', '#user-add-form', showSuccess);
};