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
        $.get('books/{param}', {"bookId": bookId, "name": null})
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

function fillAuthors(bookId) {
    var url = '/authors';
    if (bookId != null)
        url = '/authors/{bookId}';

    $.get(url, {"bookId": bookId})
        .done(function (authors) {
            var selectFieldAuthors = $("#authorIds");
            selectFieldAuthors.empty();
            var authorOptions = selectFieldAuthors[0].options;
            authors.forEach(function (author) {
                authorOptions[authorOptions.length] = new Option(author.fio, author.id, true, author.selected > 0);
            });
        })
        .fail(function (e) {
            alert("Ошибка загрузки авторов: " + e.toString());
        });
}

function fillGenres(bookId) {
    var url = '/genres';
    if (bookId != null)
        url = '/genres/{bookId}';

    $.get(url, {"bookId": bookId})
        .done(function (genres) {
            var selectFieldGenres = $("#genreIds");
            selectFieldGenres.empty();
            var genreOptions = selectFieldGenres[0].options;
            genres.forEach(function (genre) {
                option = new Option(genre.name, genre.id, true, genre.selected > 0);
                genreOptions[genreOptions.length] = option;
            });
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
    var buttonLoadFile = $("#file")[0];
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
        buttonLoadFile.disabled = true;
        buttonSaveBook[0].hidden = true;
    } else {
        name.disabled = false;
        description.disabled = false;
        selectFieldStatus.disabled = false;
        selectFieldGenres.disabled = false;
        selectFieldAuthors.disabled = false;
        buttonLoadFile.disabled = false;
        buttonSaveBook[0].hidden = false;
    }
}

function findAllBooks() {
    $('#tableBooks tbody').empty();

    $.get('/books').done(function (books) {
        books.forEach(function (book) {
            addTableBooksRow(book);
        });
    });
}

function findBookByName(formId) {
    var userReqString = jQuery("#" + formId)[0].elements[0].value;
    $.get('books/{param}', {"bookId": null, "name": userReqString})
        .done(function (data) {
            $('#tableBooks tbody > tr').remove();

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

    $('#tableBooks tbody').append(`
           <tr>
               <td>${book.name}</td>
               <td>${book.description}</td>
               <td>
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
               </td>
               <td>
                   <input type="checkbox" name="bookId" value="${book.id}" hidden>
               </td>
           </tr>
        `)
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