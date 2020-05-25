/*---------------- Работаем с жанрами ---------------*/

function findAllGenres() {
    $('#tableGenres tbody').empty();

    $.get('/genres').done(function (genres) {
        genres.forEach(function (genre) {
            $('#tableGenres tbody').append(`
                    <tr>
                        <td>${genre.name}</td>
                        <td>
                            <button type="button" class="refButton" title="Просмотр" onclick="fillGenreForm('${genre.id}', 'show')">
                                <span class="glyphicon glyphicon-eye-open"></span>
                            </button>
                            <button type="button" class="refButton" title="Редактировать" onclick="fillGenreForm('${genre.id}', 'edit')">
                                <span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button type="button" class="refButton" title="Удалить" onclick="deleteGenre('${genre.id}')">
                                <span class="glyphicon glyphicon-remove"></span>
                            </button>
                        </td>
                        <td>
                            <input type="checkbox" name="genreId" value="${genre.id}" hidden>
                        </td>
                    </tr>
                `)
        });
    });
}

function addUpdateGenre() {

    var form = $("#formCreateUpdateGenre")[0];
    var operation = form.mode;
    var data = new FormData(form);
    var type = (operation === "add") ? "POST" : "PUT";
    var url = (operation === "add") ? "genres" : "genres/{id}";
    var msgSuccess = (operation === "add") ? "Жанр успешно добавлен" : "Жанр успешно обновлен";
    var msgError = (operation === "add") ? "Ошибка создания жанра" : "Ошибка обновления жанра";

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
                findAllGenres();
            } else {
                alert(msgError);
            }
        },
        error: function (e) {
            alert(msgError + e.toString());
        }
    });
}

function fillGenreForm(id, operation) {

    // Очищаем форму
    clearGenreForm();

    // Задаем режим открытия формы
    var formCreateUpdateGenre = $("#formCreateUpdateGenre")[0];
    formCreateUpdateGenre.mode = operation;

    var titleGenreForm = $("#title-genre-form")[0];
    if ("add" === operation) {
        titleGenreForm.innerText = "Добавление жанра ";
        setDisableFieldsGenreForm(false);
        return;

    } else if ("edit" === operation) {
        titleGenreForm.innerText = "Редактирование жанра ";
        setDisableFieldsGenreForm(false);

    } else if ("show" === operation) {
        titleGenreForm.innerText = "Просмотр жанра ";
        setDisableFieldsGenreForm(true);
    }

    $.get('genres/{genreId}', {"genreId": id, "name": null})
        .done(function (data) {
            if (data != null) {
                $("#genre-id")[0].value = data.id;
                $("#genre-name")[0].value = data.name;
            }
        })
        .fail(function (e) {
            alert("При загрузке жанра возникла ошибка: " + e.responseJSON.message);
        });
}

function clearGenreForm() {
    $("#genre-id")[0].value = null;
    $("#genre-name")[0].value = null;
}

function setDisableFieldsGenreForm(isShowForm) {
    $("#genre-name")[0].disabled = isShowForm;
    $("#buttonSaveGenre")[0].hidden = isShowForm;
}

function deleteGenre(id) {
    $.ajax({
        type: "DELETE",
        url: 'genres/' + id,
        success: function (result) {
            alert("Жанр успешно удален");
            clearGenreForm();
            findAllGenres();
        },
        error: function (e) {
            alert("Ошибка удаления жанра: " + e.responseJSON.message);
        }
    });
}