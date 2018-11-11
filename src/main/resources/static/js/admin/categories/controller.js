$(document).ready(function () {

});

CategoryController = {};

CategoryController.add = function () {
    //validateFields('#category-add-form');
    sendRequest('#category-add-form', null, 'POST', request);
};

CategoryController.edit = function () {
    //validateFields('#category-form');
    sendRequest('#category-form', null, 'PATCH', request);
};

CategoryController.delete = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/categories/' + id, 'DELETE', reload);
};