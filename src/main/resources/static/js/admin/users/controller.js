$(document).ready(function () {

});

UserController = {};

UserController.addUser = function () {
    sendRequest('#user-add-form', null, request);
};

UserController.edit = function () {
    //var id = $('input[name="id"]').val();
    sendRequest('#user-form', null, request);
};

UserController.password = function () {
    sendRequest('#password-form', null, showSuccess);
};

UserController.block = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/rest/admin/users/' + id + '/block', reload);
};

UserController.unblock = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/rest/admin/users/' + id + '/unblock', reload);
};



