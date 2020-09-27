/*---------------- Общая часть ---------------*/

$(function () {

    // Получаем активного пользователя
    getActiveUser();

    // По-умолчанию показываем все книги
    findAllBooks();

    // Подгружаем жанры для фильтра на главной странице
    fillGenres(null, true);

    // Подгружаем авторов для фильтра на главной странице
    fillAuthors(null, true);

    // Вешаем событие на открытие модального окна Добавление/Редактирование/Просмотр книги
    $("#myModal").on('shown', function () {

        var formCreateUpdateBook = $("#formCreateUpdateBook")[0];
        var bookIdOnForm = $("#bookId")[0];
        var mode = formCreateUpdateBook.mode;
        var bookId = bookIdOnForm.value;

        var isShowForm = ("show" === mode);
        var isEditForm = ("edit" === mode);
        var isAddForm = ("add" === mode);

        if (isShowForm || isEditForm) {
            setButtonClickEvents("formCreateUpdateBook", isEditForm, isShowForm, isAddForm);
            fillMainFields(bookId, isEditForm, isAddForm, isShowForm);
            fillAuthors(bookId, false);
            fillGenres(bookId, false);
            fillStatuses(bookId);
            setDisableFields(isShowForm);
        } else {
            setButtonClickEvents("formCreateUpdateBook", isEditForm, isShowForm, isAddForm);
            fillMainFields(null, isEditForm, isAddForm, isShowForm);
            fillAuthors(null, false);
            fillGenres(null, false);
            fillStatuses(null);
        }
    });
});

$(function () {
    var $contextMenu = $("#contextMenu");

    $("#tableBooks tbody").on("click", "tr", function () {
        selectUnselectRowBookCheckBox(this);
    });

    $("body").on("contextmenu", "#tableBooks tr", function (e) {
        $contextMenu.css({
            display: "block",
            left: e.pageX,
            top: e.pageY,
            background: "#FFFFFF",
            margin: "0 0 0 0"
        });

        var element = $(this)[0];
        var bookId = null;
        for (var i = 0; i < element.cells.length; i++) {
            var itm = element.cells[i];
            if (itm != null && itm.children[0] != null && itm.children[0].type === "checkbox") {
                bookId = itm.children[0].value;
            }
        }
        $contextMenu.ready(function () {
            for (var i = 0; i < $contextMenu[0].children[0].children.length; i++) {
                var li = $contextMenu[0].children[0].children[i];
                li.firstChild.bookId = bookId;
            }
        });
        return false;
    });

    $('html').click(function () {
        $contextMenu.hide();
    });

    jQuery.datetimepicker.setLocale('ru');

    jQuery('#author-birth-date').datetimepicker({
        i18n: {
            de: {
                months: [
                    'Январь', 'Февраль', 'Март', 'Апрель',
                    'Май', 'Июнь', 'Июль', 'Август',
                    'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь',
                ],
                dayOfWeek: [
                    "Вс.", "Пн", "Вт", "Ср",
                    "Чт", "Пт", "Сб.",
                ]
            }
        },
        timepicker: false,
        format: 'd.m.Y'
    });
});

function readTitleURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#imagePreview')
                .attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function readContentURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            //$('#imagePreview')
            //    .attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
}

function selectUnselectRowBookCheckBox(element) {
    for (var i = 0; i < element.cells.length; i++) {
        var itm = element.cells[i];
        if (itm != null && itm.children[0] != null && itm.children[0].type === "checkbox") {
            itm.children[0].checked = !itm.children[0].checked;
        }
    }
}

function getActiveUser() {
    $.get('users/{active}').done(function (user) {
        if (user == null)
            return;

        var username = user.username;
        $("#helloUser")[0].innerText = ("Вход" === username) ? username : "Здравствуйте " + username;
        $("#exitUser")[0].hidden = ("Вход" === username);

        var roles = user.roles;
        if (roles == null)
            return;

        roles.forEach(function (role) {
            if ("ROLE_ADMINISTRATION" === role.role || "ROLE_AUTHORS_WRITE" === role.role)
                $("#li-tab-authors")[0].hidden = false;

            if ("ROLE_ADMINISTRATION" === role.role || "ROLE_GENRES_WRITE" === role.role)
                $("#li-tab-genres")[0].hidden = false;

            if ("ROLE_ADMINISTRATION" === role.role || "ROLE_STATUSES_WRITE" === role.role)
                $("#li-tab-statuses")[0].hidden = false;

            if ("ROLE_ADMINISTRATION" === role.role || "ROLE_ROLES_WRITE" === role.role)
                $("#li-tab-roles")[0].hidden = false;

            if ("ROLE_ADMINISTRATION" === role.role || "ROLE_USERS_WRITE" === role.role)
                $("#li-tab-users")[0].hidden = false;

            if ("ROLE_ADMINISTRATION" === role.role || "ROLE_BOOKS_WRITE" === role.role) {
                $("#buttonAddBook")[0].hidden = false;
                $("#contextDeleteBook")[0].hidden = false;
                $("#contextEditBook")[0].hidden = false;
            }
        });
    });
}