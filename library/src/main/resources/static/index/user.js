/*---------------- Работаем с пользователями ---------------*/

function findAllUsers() {
    $('#tableUsers tbody').empty();

    $.get('/users').done(function (users) {
        users.forEach(function (user) {
            $('#tableUsers tbody').append(`
                    <tr>
                        <td>${user.username}</td>
                        <td>${user.firstName}</td>
                        <td>${user.secondName}</td>
                        <td>${user.thirdName}</td>
                        <td>
                            <button type="button" class="refButton" title="Просмотр" onclick="fillUserForm('${user.username}', 'show')">
                                <span class="glyphicon glyphicon-eye-open"></span>
                            </button>
                            <button type="button" class="refButton" title="Редактировать" onclick="fillUserForm('${user.username}', 'edit')">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button type="button" class="refButton" title="Удалить" onclick="deleteUser('${user.username}')">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </td>
                        <td>
                            <input type="checkbox" name="userId" value="${user.username}" hidden>
                        </td>
                    </tr>
                `)
        });
    });
}

function fillUserForm(username, operation) {

    // Очищаем форму
    clearUserForm();

    // Задаем режим открытия формы
    var formCreateUpdateUser = $("#formCreateUpdateUser")[0];
    formCreateUpdateUser.mode = operation;

    var titleUserForm = $("#title-user-form")[0];
    if ("add" === operation) {
        titleUserForm.innerText = "Добавление пользователя ";
        setDisableFieldsUserForm(false);

    } else if ("edit" === operation) {
        titleUserForm.innerText = "Редактирование пользователя ";
        setDisableFieldsUserForm(false);

    } else if ("show" === operation) {
        titleUserForm.innerText = "Просмотр пользователя ";
        setDisableFieldsUserForm(true);
    }

    $.get('users/{param}', {"username": username, "email": null})
        .done(function (resultList) {
            if (resultList != null && resultList[0] != null) {
                var data = resultList[0];
                $("#user-username")[0].value = data.username;
                $("#user-password")[0].value = data.password;
                $("#user-email")[0].value = data.email;
                $("#user-phone")[0].value = data.phone;
                $("#user-firstName")[0].value = data.firstName;
                $("#user-secondName")[0].value = data.secondName;
                $("#user-thirdName")[0].value = data.thirdName;
            }
        })
        .fail(function (e) {
            alert("При загрузке пользователя возникла ошибка: " + e.responseJSON.message);
        });

    // Заполняем список ролей
    fillRolesByUsername(("add" === operation) ? null : username);
}

function setDisableFieldsUserForm(isShowForm) {
    $("#user-username")[0].disabled = isShowForm;
    $("#user-password")[0].disabled = isShowForm;
    $("#user-email")[0].disabled = isShowForm;
    $("#user-phone")[0].disabled = isShowForm;
    $("#user-firstName")[0].disabled = isShowForm;
    $("#user-secondName")[0].disabled = isShowForm;
    $("#user-thirdName")[0].disabled = isShowForm;
    $("#user-roles")[0].disabled = isShowForm;
    $("#buttonSaveUser")[0].hidden = isShowForm;
}

function clearUserForm() {
    $("#user-username")[0].value = null;
    $("#user-password")[0].value = null;
    $("#user-email")[0].value = null;
    $("#user-phone")[0].value = null;
    $("#user-firstName")[0].value = null;
    $("#user-secondName")[0].value = null;
    $("#user-thirdName")[0].value = null;
}

function deleteUser(username) {
    $.ajax({
        type: "DELETE",
        url: 'users/' + username,
        success: function (result) {
            alert("Пользователь успешно удален");
            clearUserForm();
            findAllUsers();
        },
        error: function (e) {
            alert("Ошибка удаления пользователя: " + e.responseJSON.message);
        }
    });
}

function fillRolesByUsername(username) {
    var url = '/roles';
    if (username != null)
        url = '/roles/{username}';

    $.get(url, {"username": username})
        .done(function (roles) {
            if (roles != null) {
                var selectFieldRoles = $("#user-roles");
                selectFieldRoles.empty();
                var roleOptions = selectFieldRoles[0].options;
                roles.forEach(function (role) {
                    roleOptions[roleOptions.length] = new Option(role.description, role.role, true, role.selected);
                });
            }
        })
        .fail(function (e) {
            alert("Ошибка загрузки ролей: " + e.responseJSON.message);
        });
}

function addUpdateUser() {

    var form = $("#formCreateUpdateUser")[0];
    var operation = form.mode;
    var data = new FormData(form);
    var type = (operation === "add") ? "POST" : "PUT";
    var url = (operation === "add") ? "users" : "users/{id}";
    var msgSuccess = (operation === "add") ? "Пользователь успешно добавлен" : "Пользователь успешно обновлен";
    var msgError = (operation === "add") ? "Ошибка создания пользователя" : "Ошибка обновления пользователя";

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
                findAllUsers();
            } else {
                alert(msgError);
            }
        },
        error: function (e) {
            alert(msgError + e.toString());
        }
    });
}