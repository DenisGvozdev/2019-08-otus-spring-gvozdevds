/*---------------- Работаем с ролями ---------------*/

function findAllRoles() {
    $('#tableRoles tbody').empty();

    $.get('/roles').done(function (roles) {
        roles.forEach(function (role) {
            $('#tableRoles tbody').append(`
                    <tr>
                        <td>${role.role}</td>
                        <td>${role.description}</td>
                          <td>
                            <button type="button" class="refButton" title="Просмотр" onclick="fillRoleForm('${role.role}', 'show')">
                                <span class="glyphicon glyphicon-eye-open"></span>
                            </button>
                            <button type="button" class="refButton" title="Редактировать" onclick="fillRoleForm('${role.role}', 'edit')">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button type="button" class="refButton" title="Удалить" onclick="deleteRole('${role.role}')">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </td>
                        <td>
                            <input type="checkbox" name="roleId" value="${role.role}" hidden>
                        </td>
                    </tr>
                `)
        });
    });
}

function fillRoleForm(role, operation) {

    // Очищаем форму
    clearRoleForm();

    // Задаем режим открытия формы
    var formCreateUpdateRole = $("#formCreateUpdateRole")[0];
    formCreateUpdateRole.mode = operation;

    var titleRoleForm = $("#title-role-form")[0];
    if ("add" === operation) {
        titleRoleForm.innerText = "Добавление роли ";
        setDisableFieldsRoleForm(false);
        return;

    } else if ("edit" === operation) {
        titleRoleForm.innerText = "Редактирование роли ";
        setDisableFieldsRoleForm(false);

    } else if ("show" === operation) {
        titleRoleForm.innerText = "Просмотр роли ";
        setDisableFieldsRoleForm(true);
    }

    $.get('roles/{role}', {"role": role, "description": null})
        .done(function (data) {
            if (data != null) {
                $("#role-role")[0].value = data.role;
                $("#role-description")[0].value = data.description;
            }
        })
        .fail(function (e) {
            alert("При загрузке роли возникла ошибка: " + e.responseJSON.message);
        });
}

function deleteRole(role) {
    $.ajax({
        type: "DELETE",
        url: 'roles/' + role,
        success: function (result) {
            alert("Роль успешно удалена");
            clearRoleForm();
            findAllRoles();
        },
        error: function (e) {
            alert("Ошибка удаления роли: " + e.responseJSON.message);
        }
    });
}

function clearRoleForm() {
    $("#role-role")[0].value = null;
    $("#role-description")[0].value = null;
}

function setDisableFieldsRoleForm(isShowForm) {
    $("#role-role")[0].disabled = isShowForm;
    $("#role-description")[0].disabled = isShowForm;
    $("#buttonSaveRole")[0].hidden = isShowForm;
}


function addUpdateRole() {

    var form = $("#formCreateUpdateRole")[0];
    var operation = form.mode;
    var data = new FormData(form);
    var type = (operation === "add") ? "POST" : "PUT";
    var url = (operation === "add") ? "roles" : "roles/{role}";
    var msgSuccess = (operation === "add") ? "Роль успешно добавлена" : "Роль успешно обновлена";
    var msgError = (operation === "add") ? "Ошибка создания роли" : "Ошибка обновления роли";

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
                findAllRoles();
            } else {
                alert(msgError);
            }
        },
        error: function (e) {
            alert(msgError + e.toString());
        }
    });
}