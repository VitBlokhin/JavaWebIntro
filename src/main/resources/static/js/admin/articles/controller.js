$(document).ready(function () {

});

ArticleController = {};

ArticleController.hide = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id + '/hide', 'POST', reload);
};

ArticleController.show = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id + '/show' , 'POST', reload);
};

ArticleController.block = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id + '/block', 'POST', reload);
};

ArticleController.unblock = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id + '/unblock', 'POST', reload);
};

ArticleController.delete = function () {
    var id = $('input[name="id"]').val();
    sendRequest(null, '/admin/articles/' + id, 'DELETE', reload);
};
