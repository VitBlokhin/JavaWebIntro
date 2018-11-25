$(document).ready(function () {

});

ArticleController = {};

ArticleController.add = function () {
    sendRequest('#article-add-form', null, 'POST', request);
};

ArticleController.edit = function () {
    sendRequest('#article-form', null, 'PATCH', request);
};

ArticleController.show = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/articles/' + id + '/show' , 'POST', reload);
};

ArticleController.hide = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/articles/' + id + '/hide', 'POST', reload);
};

ArticleController.delete = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/articles/' + id, 'DELETE', reload);
};