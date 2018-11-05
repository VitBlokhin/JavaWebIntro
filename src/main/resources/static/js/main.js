$(document).ready(function () {
    MaterializeUtils.materializeUpdate();
});

function getData(form) {
    var formData = {};
    $('input, texarea, select', form).each(function () {
            if (this.name && this.name != '') {
                formData[this.name] = this.value;
                //console.log('formData[' + this.name + '] = ' + formData[this.name]);
            }
        }
    );
    return formData;
}

function request(url) {
    window.location = url;
}

// for non-GET requests with forms (POST, PUT, PATCH, DELETE)
function sendRequest(form, url, successFunction, contentType) {
    var data = getData(form);
    var action = $(form).attr('action');
    var method = $(form).attr('method');

    $.ajax({
        method: method || "POST",
        url: url || '/rest' + action,
        data: data,
        contentType: contentType || "application/x-www-form-urlencoded",
        success: function (response) {
            successFunction(response);
        },
        error: function (response) {
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

function reload() {
    location.reload();
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