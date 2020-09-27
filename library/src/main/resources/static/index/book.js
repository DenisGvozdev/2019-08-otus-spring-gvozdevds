/*---------------- Работаем с книгами ---------------*/

$('.tableBooks tr').click(function () {
    $(this).toggleClass("clicked");
});

function downloadBook(id) {
    downloadFile('/content/bookId?bookId=' + id);
}

function showBookForm(element, operation) {

    // Передаем на форму идентификатор книги
    var bookIdOnForm = $("#bookId")[0];
    bookIdOnForm.value = element.bookId;

    // Задаем режим открытия формы
    var formCreateUpdateBook = $("#formCreateUpdateBook")[0];
    formCreateUpdateBook.mode = operation;

    // Показываем форму
    $("#myModal").modal();
}

function setButtonClickEvents(idForm, isEditForm, isShowForm, isAddForm) {
    var buttonSaveBook = document.getElementById('buttonSaveBook');
    var buttonUpdateBook = document.getElementById('buttonUpdateBook');
    if (isEditForm) {
        buttonSaveBook.setAttribute('class', 'hidden');
        buttonUpdateBook.setAttribute('class', 'visible');
    } else if (isAddForm) {
        buttonSaveBook.setAttribute('class', 'visible');
        buttonUpdateBook.setAttribute('class', 'hidden');
    } else if (isShowForm) {
        buttonSaveBook.setAttribute('class', 'hidden');
        buttonUpdateBook.setAttribute('class', 'hidden');
    }
}

function fillMainFields(bookId, isEditForm, isAddForm, isShowForm) {
    if (bookId != null) {
        $.get('books/{param}',
            {
                "bookId": bookId,
                "name": null,
                "author": null,
                "genre": null
            })
            .done(function (data) {
                var titleBookForm = $("#titleBookForm")[0];
                var bookId = $("#bookId")[0];
                var name = $("#name")[0];
                var imagePreview = $("#imagePreview")[0];

                var titleForm = "";
                if (isEditForm) {
                    titleForm = "Редактирование книги ";
                } else if (isAddForm) {
                    titleForm = "Добавление книги ";
                } else if (isShowForm) {
                    titleForm = "Книга ";
                }

                titleBookForm.innerText = titleForm + data[0].name;

                bookId.value = data[0].id;
                name.value = data[0].name;
                imagePreview.src = data[0].imageString;
                description.value = data[0].description;
            })
            .fail(function (e) {
                alert("При поиске книги возникла ошибка: " + e.responseJSON.message);
            });
    } else if (isAddForm) {
        var titleBookForm = $("#titleBookForm")[0];
        titleBookForm.innerText = "Добавление книги ";
    }
}

function fillAuthors(bookId, forBookPage) {
    var url = '/authors';
    if (bookId != null)
        url = '/authors/{bookId}';

    $.get(url, {"bookId": bookId})
        .done(function (authors) {
            var selectFieldAuthors = (forBookPage) ? $("#rightMenuFormAuthorIds") : $("#authorIds");
            selectFieldAuthors.empty();
            var authorOptions = selectFieldAuthors[0].options;
            authors.forEach(function (author) {
                var option = new Option(author.fio, author.id, true, author.selected > 0);
                option.id = author.id;
                option.value = author.id;
                authorOptions.add(option);
            });
            selectFieldAuthors.selectpicker();
        })
        .fail(function (e) {
            alert("Ошибка загрузки авторов: " + e.toString());
        });
}

function fillGenres(bookId, forBookPage) {
    var url = '/genres';
    if (bookId != null)
        url = '/genres/{bookId}';

    $.get(url, {"bookId": bookId})
        .done(function (genres) {
            var selectFieldGenres = (forBookPage) ? $("#rightMenuFormGeneIds") : $("#genreIds");
            selectFieldGenres.empty();
            var genreOptions = selectFieldGenres[0].options;
            genres.forEach(function (genre) {
                var option = new Option(genre.name, genre.id, true, genre.selected > 0);
                genreOptions.add(option);
            });
            selectFieldGenres.selectpicker();
        })
        .fail(function (e) {
            alert("Ошибка загрузки жанров: " + e.toString());
        });
}

function fillStatuses(bookId) {
    var url = '/statuses';
    if (bookId != null)
        url = '/statuses/{bookId}';

    $.get(url, {"bookId": bookId})
        .done(function (statuses) {
            var selectFieldStatus = $("#statusId");
            selectFieldStatus.empty();
            var statusOptions = selectFieldStatus[0].options;
            statuses.forEach(function (status) {
                option = new Option(status.name, status.id, true, status.selected > 0);
                statusOptions[statusOptions.length] = option;
            });
        })
        .fail(function (e) {
            alert("Ошибка загрузки статусов: " + e.toString());
        });
}

function setDisableFields(isShowForm) {

    var name = $("#name")[0];
    var description = $("#description")[0];
    var buttonLoadTitle = $("#fileTitle")[0];
    var buttonLoadContent = $("#fileContent")[0];
    var selectFieldStatus = $("#statusId")[0];
    var selectFieldGenres = $("#genreIds")[0];
    var selectFieldAuthors = $("#authorIds")[0];
    var buttonSaveBook = $("#buttonSaveBook");

    if (isShowForm) {
        name.disabled = true;
        description.disabled = true;
        selectFieldStatus.disabled = true;
        selectFieldGenres.disabled = true;
        selectFieldAuthors.disabled = true;
        buttonLoadTitle.disabled = true;
        buttonLoadContent.disabled = true;
        buttonSaveBook[0].hidden = true;
    } else {
        name.disabled = false;
        description.disabled = false;
        selectFieldStatus.disabled = false;
        selectFieldGenres.disabled = false;
        selectFieldAuthors.disabled = false;
        buttonLoadTitle.disabled = false;
        buttonLoadContent.disabled = false;
        buttonSaveBook[0].hidden = false;
    }
}

function findAllBooks() {

    $('#listBooks').empty();

    $.get('/books').done(function (books) {
        books.forEach(function (book) {
            addTableBooksRow(book);
        });
    });
}

function findBookByName(formId) {
    var userReqString = jQuery("#" + formId)[0].elements[0].value;
    $.get('books/{param}', {
        "bookId": null,
        "name": userReqString,
        "author": null,
        "genre": null})
        .done(function (data) {
            // $('#tableBooks tbody > tr').remove();

            if (data == null || data.length === 0) {
                alert("Не удалось найти книги");

            } else {
                $('#listBooks').empty();

                data.forEach(function (book) {
                    addTableBooksRow(book)
                });
            }
        })
        .fail(function (e) {
            alert("При поиске книги возникла ошибка: " + e.responseJSON.message);
        })
}

function findBookByParams(formId) {
    var formData = jQuery("#" + formId)[0].elements;
    var bookName = formData[0].value;
    var genre = formData[1].value;
    var author = formData[3].value;

    $.get('books/{param}', {
        "bookId": null,
        "name": bookName,
        "author": author,
        "genre": genre})
        .done(function (data) {
            $('#listBooks').empty();

            if (data == null || data.length === 0) {
                alert("Не удалось найти книги");

            } else {
                data.forEach(function (book) {
                    addTableBooksRow(book)
                });
            }
        })
        .fail(function (e) {
            alert("При поиске книги возникла ошибка: " + e.responseJSON.message);
        })
}

function addTableBooksRow(book) {

    var display = (book.write) ? 'true' : 'none';

    var image = $('<img>').prop({
        id: book.id + "Image",
        src: book.imageString,
        className: 'bookImage'
    });
    var bookImageDiv = $('<div>').prop({
        id: book.id + "Image",
        className: 'bookImageDiv'
    });
    bookImageDiv.append(image);

    var bookNameDiv = $('<div>').prop({
        id: book.id + "Name",
        innerHTML: book.name,
        className: 'bookNameDiv'
    });
    var bookDescriptionDiv = $('<div>').prop({
        id: book.id + "Description",
        innerHTML: book.description,
        className: 'bookDescriptionDiv'
    });
    var bookButtonsDiv = $('<div>').prop({
        id: book.id + "Buttons",
        className: 'bookButtonsDiv'
    });
    bookButtonsDiv.append(`
        <a href="/content?bookId=${book.id}&pageStart=1&countPages=1000"  class="refButton" title="Читать" target="_blank">
            <span class="glyphicon glyphicon-book"></span>
        </a>
        <button type="button" class="refButton" title="Скачать" onclick="downloadBook('${book.id}')">
            <span class="glyphicon glyphicon-download-alt"></span>
        </button>
        <button type="button" class="refButton" title="Просмотр" onclick="showBookForm({bookId: '${book.id}'}, 'show')">
            <span class="glyphicon glyphicon-eye-open"></span>
        </button>
        <button type="button" class="refButton" title="Редактировать"
            onclick="showBookForm({bookId: '${book.id}'}, 'edit')" style="display: ${display}">
            <span class="glyphicon glyphicon-edit"></span>
        </button>
        <button type="button" class="refButton" title="Удалить"
            onclick="deleteBook({bookId: '${book.id}'})" style="display: ${display}">
            <span class="glyphicon glyphicon-remove"></span>
        </button>
    `);

    var bookNameAndButtonsDiv = $('<div>').prop({
        id: book.id + "NameAndButtons",
        className: 'nameAndButtonsDiv'
    });
    bookNameAndButtonsDiv.append(bookNameDiv);
    bookNameAndButtonsDiv.append(bookButtonsDiv);

    var bookInfoDiv = $('<div>').prop({
        id: book.id + "Info",
        className: 'bookInfoDiv'
    });

    bookInfoDiv.append(bookNameAndButtonsDiv);
    bookInfoDiv.append(bookDescriptionDiv);

    var bookDiv = $('<div>').prop({
        id: book.id,
        className: 'bookItem'
    });
    bookDiv.append(bookImageDiv);
    bookDiv.append(bookInfoDiv);

    $('#listBooks').append(bookDiv);
}

function addBook() {
    var form = $("#formCreateUpdateBook")[0];
    var data = new FormData(form);

    $.ajax({
        url: "books",
        type: "POST",
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (book) {
            $("#myModal").modal('hide');
            if (book != null) {
                alert("Книга успешно добавлена");
            } else {
                alert("Ошибка создания книги");
            }
            findAllBooks();
        },
        error: function (e) {
            $("#myModal").modal('hide');
            alert("Ошибка создания книги: " + e.toString());
            findAllBooks();
        }
    });
}

function updateBook() {
    var form = $("#formCreateUpdateBook")[0];
    var data = new FormData(form);
    $.ajax({
        url: "books/{id}",
        type: "PUT",
        data: data,
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        cache: false,
        success: function (book) {
            $("#myModal").modal('hide');
            if (book != null) {
                alert("Книга успешно обновлена");
            } else {
                alert("Ошибка обновления книги");
            }
            findAllBooks();
        },
        error: function (e) {
            $("#myModal").modal('hide');
            alert("Ошибка обновления книги: " + e.toString());
            findAllBooks();
        }
    });
}

function deleteBook(element) {
    $.ajax({
        type: "DELETE",
        url: 'books/' + element.bookId,
        success: function (result) {
            $('#tableBooks tbody > tr').remove();
            alert("Книга успешно удалена");
            findAllBooks();
        },
        error: function (e) {
            alert("Ошибка удаления книги: " + e.toString());
        }
    });
}