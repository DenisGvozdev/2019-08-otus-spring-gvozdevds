/*---------------- Работаем с авторами ---------------*/

function findAllAuthors() {
    $('#tableAuthors tbody').empty();

    $.get('/authors').done(function (authors) {
        authors.forEach(function (author) {
            $('#tableAuthors tbody').append(`
                    <tr>
                        <td>${author.fio}</td>
                        <!--td>${author.birthDateString}</td-->
                        <td>
                            <button type="button" class="refButton" title="Просмотр" onclick="fillAuthorForm('${author.id}', 'show')">
                                <span class="glyphicon glyphicon-eye-open"></span>
                            </button>
                            <button type="button" class="refButton" title="Редактировать" onclick="fillAuthorForm('${author.id}', 'edit')">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button type="button" class="refButton" title="Удалить" onclick="deleteAuthor('${author.id}')">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </td>
                        <td>
                            <input type="checkbox" name="authorId" value="${author.id}" hidden>
                        </td>
                    </tr>
                `)
        });
    });
}

function addUpdateAuthor() {

    var form = $("#formCreateUpdateAuthor")[0];
    var operation = form.mode;
    var data = new FormData(form);
    var type = (operation === "add") ? "POST" : "PUT";
    var url = (operation === "add") ? "authors" : "authors/{id}";
    var msgSuccess = (operation === "add") ? "Автор успешно добавлен" : "Автор успешно обновлен";
    var msgError = (operation === "add") ? "Ошибка создания автора" : "Ошибка обновления автора";

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
                findAllAuthors();
            } else {
                alert(msgError);
            }
        },
        error: function (e) {
            alert(msgError + e.toString());
        }
    });
}

function fillAuthorForm(id, operation) {

    // Очищаем форму
    clearAuthorForm();

    // Задаем режим открытия формы
    var formCreateUpdateAuthor = $("#formCreateUpdateAuthor")[0];
    formCreateUpdateAuthor.mode = operation;

    var titleAuthorForm = $("#title-author-form")[0];
    if ("add" === operation) {
        titleAuthorForm.innerText = "Добавление автора ";
        setDisableFieldsAuthorForm(false);
        return;

    } else if ("edit" === operation) {
        titleAuthorForm.innerText = "Редактирование автора ";
        setDisableFieldsAuthorForm(false);

    } else if ("show" === operation) {
        titleAuthorForm.innerText = "Просмотр автора ";
        setDisableFieldsAuthorForm(true);
    }

    $.get('authors/{authorId}', {"authorId": id, "name": null})
        .done(function (data) {
            if (data != null) {
                $("#author-id")[0].value = data.id;
                $("#author-first-name")[0].value = data.firstName;
                $("#author-second-name")[0].value = data.secondName;
                $("#author-third-name")[0].value = data.thirdName;
                $("#author-birth-date")[0].value = data.birthDateString;
            }
        })
        .fail(function (e) {
            alert("При загрузке автора возникла ошибка: " + e.responseJSON.message);
        });
}

function clearAuthorForm() {
    $("#author-id")[0].value = null;
    $("#author-first-name")[0].value = null;
    $("#author-second-name")[0].value = null;
    $("#author-third-name")[0].value = null;
    $("#author-birth-date")[0].value = null;
}

function setDisableFieldsAuthorForm(isShowForm) {
    $("#author-first-name")[0].disabled = isShowForm;
    $("#author-second-name")[0].disabled = isShowForm;
    $("#author-third-name")[0].disabled = isShowForm;
    $("#author-birth-date")[0].disabled = isShowForm;
    $("#buttonSaveAuthor")[0].hidden = isShowForm;
}

function deleteAuthor(id) {
    $.ajax({
        type: "DELETE",
        url: 'authors/' + id,
        success: function (result) {
            alert("Автор успешно удален");
            clearAuthorForm();
            findAllAuthors();
        },
        error: function (e) {
            alert("Ошибка удаления автора: " + e.responseJSON.message);
        }
    });
}