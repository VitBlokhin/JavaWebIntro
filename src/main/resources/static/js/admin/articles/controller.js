$(document).ready(function () {

});

ArticleController = {};

ArticleController.edit = function () {
    sendRequest('#article-form', null, 'PATCH', request);
};

ArticleController.delete = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id, 'DELETE', reload);
};

ArticleController.show = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id + '/show' , 'POST', reload);
};

ArticleController.hide = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id + '/hide', 'POST', reload);
};

ArticleController.block = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id + '/block', 'POST', reload);
};