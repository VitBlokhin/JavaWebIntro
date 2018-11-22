$(document).ready(function () {

});

ArticleController = {};

ArticleController.add = function () {
    sendRequest('#article-add-form', null, 'POST', request);
};

ArticleController.edit = function () {
    sendRequest('#article-form', null, 'PATCH', request);
};

ArticleController.delete = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/articles/' + id, 'DELETE', reload);
};