$(document).ready(function () {

});

UserController = {};

UserController.edit = function(){
    //TODO
    sendRequest('#user-form', null, null, showSuccess);
};

UserController.password = function(){
    //TODO
    sendRequest('#password-form', null, null, showSuccess);
};