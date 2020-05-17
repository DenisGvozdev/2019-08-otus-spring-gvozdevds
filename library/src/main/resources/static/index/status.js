/*---------------- Работаем со статусами ---------------*/

function findAllStatuses() {
    $('#tableStatuses tbody').empty();

    $.get('/statuses').done(function (statuses) {
        statuses.forEach(function (status) {
            $('#tableStatuses tbody').append(`
                    <tr>
                        <td>${status.name}</td>
                        <td>
                            <button type="button" class="refButton" title="Просмотр" onclick="fillStatusForm('${status.id}', 'show')">
                                <span class="glyphicon glyphicon-eye-open"></span>
                            </button>
                            <button type="button" class="refButton" title="Редактировать" onclick="fillStatusForm('${status.id}', 'edit')">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button type="button" class="refButton" title="Удалить" onclick="deleteStatus('${status.id}')">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </td>
                        <td>
                            <input type="checkbox" name="statusId" value="${status.id}" hidden>
                        </td>
                    </tr>
                `)
        });
    });
}

function addUpdateStatus() {

    var form = $("#formCreateUpdateStatus")[0];
    var operation = form.mode;
    var data = new FormData(form);
    var type = (operation === "add") ? "POST" : "PUT";
    var url = (operation === "add") ? "statuses" : "statuses/{id}";
    var msgSuccess = (operation === "add") ? "Статус успешно добавлен" : "Статус успешно обновлен";
    var msgError = (operation === "add") ? "Ошибка создания статуса" : "Ошибка обновления статуса";

    $.ajax({
        url: url,
        type: type,
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (book) {
            if (book != null) {
                alert(msgSuccess);
                findAllStatuses();
            } else {
                alert(msgError);
            }
        },
        error: function (e) {
            alert(msgError + e.toString());
        }
    });
}

function fillStatusForm(id, operation) {

    // Очищаем форму
    clearStatusForm();

    // Задаем режим открытия формы
    var formCreateUpdateStatus = $("#formCreateUpdateStatus")[0];
    formCreateUpdateStatus.mode = operation;

    var titleStatusForm = $("#title-status-form")[0];
    if ("add" === operation) {
        titleStatusForm.innerText = "Добавление статуса ";
        setDisableFieldsStatusForm(false);
        return;

    } else if ("edit" === operation) {
        titleStatusForm.innerText = "Редактирование статуса ";
        setDisableFieldsStatusForm(false);

    } else if ("show" === operation) {
        titleStatusForm.innerText = "Просмотр статуса ";
        setDisableFieldsStatusForm(true);
    }

    $.get('statuses/{statusId}', {"statusId": id, "name": null})
        .done(function (data) {
            if (data != null) {
                $("#status-id")[0].value = data.id;
                $("#status-name")[0].value = data.name;
            }
        })
        .fail(function (e) {
            alert("При загрузке статуса возникла ошибка: " + e.responseJSON.message);
        });
}

function clearStatusForm() {
    $("#status-id")[0].value = null;
    $("#status-name")[0].value = null;
}

function setDisableFieldsStatusForm(isShowForm) {
    $("#status-name")[0].disabled = isShowForm;
    $("#buttonSaveStatus")[0].hidden = isShowForm;
}

function deleteStatus(id) {
    $.ajax({
        type: "DELETE",
        url: 'statuses/' + id,
        success: function (result) {
            alert("Статус успешно удален");
            clearStatusForm();
            findAllStatuses();
        },
        error: function (e) {
            alert("Ошибка удаления статуса: " + e.responseJSON.message);
        }
    });
}