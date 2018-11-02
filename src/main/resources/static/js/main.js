$(document).ready(function () {
    MaterializeUtils.materializeUpdate();
});

function getData(obj_form) {
    var hData = {};
    $('input, texarea, select', obj_form).each(function () {
        if (this.name && this.name != '') {
            hData[this.name] = this.value;
            console.log('hData[' + this.name + '] = ' + hData[this.name]);
        }
    });
    return hData;
}

function request(url) {
    window.location = url;
}

function requestPost(url, dataForm, successFunction, contentType) {
    $.ajax({
        method: "POST",
        url: url,
        data: getData(dataForm),
        contentType: contentType || "application/x-www-form-urlencoded",
        success: function (response) {
            //TODO: проверить отработку
            if (response.status === 200) {

                successFunction(response);
            } else {
                if (!$('#error-modal')) {
                    showError(response);
                } else {
                    showErrorModal(response);
                }
            }
        },
        error: function (response) {
            console.log(response);
            if (response.status === 403) {
                response.message = "У вас нет доступа для совершения данной операции";
                showErrorModal(response);
            }
            if (response.status === 500) {
                response.message = "Ошибка на сервере";
                showErrorModal(response);
            }
            if (response.status === 400) {
                response.message = response.responseText;
                showErrorModal(response);
            }
        }
    })
}

function getId() {
    return {
        id: $("#id").val()
    }
}

function showError(response) {
    $("#info").hide();
    var error = $("#error");
    error.show();
    error.find("span").text(response.message);
    error.fadeTo(2000, 500).slideUp(500, function () {
        error.slideUp(500);
    });
}

function showErrorModal(response) {
    $("#info").hide();
    var error = $('#error-modal');
    error.find("p").text(response.message);
    error.modal('open');
}

function redirect(response) {
    window.location = response.data;
}

function showInfo(response) {
    $("#error").hide();
    var info = $("#info");
    info.show();
    info.text(response.data);
    $('html, body').animate({
        scrollTop: info.offset().top - info.width()
    }, 500);
}

function showSuccess(response, id) {
    $("#error").hide();
    var success = id === undefined ? $("#success") : $(id).find("#success");
    success.show();
    success.find("span").text(response.data);
    success.fadeTo(2000, 500).slideUp(500, function () {
        success.slideUp(500);
    });
}