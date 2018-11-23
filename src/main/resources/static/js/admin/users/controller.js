$(document).ready(function () {

});

UserController = {};

UserController.addUser = function () {
    //validateFields('#user-add-form');
    sendRequest('#user-add-form', null, null, request);
};

UserController.edit = function () {
    //validateFields('#user-form');
    sendRequest('#user-form', null, null, request);
};

UserController.password = function () {
    //validateFields('#password-form');
    sendRequest('#password-form', null, null, CallbackUtil.emptySuccessSave);
};

UserController.block = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/users/' + id + '/block', 'POST', reload);
};

UserController.unblock = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/users/' + id + '/unblock', 'POST', reload);
};

UserController.delete = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/users/' + id, 'DELETE', reload);
};



