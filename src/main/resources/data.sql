INSERT INTO STATUSES (ID, `NAME`) VALUES
(1, 'active'),
(0, 'inactive');

INSERT INTO AUTHORS (ID, FIRSTNAME, SECONDNAME, THIRDNAME, BIRTH_DATE) VALUES
(1, 'Ник', 'Перумов', '', CURRENT_TIMESTAMP),
(2, 'Даниэль', 'Дефо', '', CURRENT_TIMESTAMP),
(3, 'Михаил', 'Афанасьевич', 'Булгаков', CURRENT_TIMESTAMP);

INSERT INTO GENRES (ID, `NAME`) VALUES
(1, 'Фэнтези'),
(2, 'Приключения'),
(3, 'Роман');

INSERT INTO COMMENTS (ID, BOOK_ID, COMMENT, CREATE_DATE) VALUES
(1, 1, 'Хорошая книга', CURRENT_TIMESTAMP),
(2, 2, 'Интересная книга', CURRENT_TIMESTAMP);

INSERT INTO BOOKS (ID, `NAME`, CREATE_DATE, DESCRIPTION, `IMAGE`, GENRES, STATUS, AUTHORS) VALUES
(1, 'Кольцо тьмы', CURRENT_TIMESTAMP, 'Магия, миры, приключения...', NULL, 1, 1, 1),
(2, 'Робинзон Крузо', CURRENT_TIMESTAMP, 'Как выжить на необитаемом острове', NULL, 1, 1, 1);

INSERT INTO BOOK_GENRE (BOOK_ID, GENRE_ID) VALUES
(1, 3),
(2, 2),
(3, 1);

INSERT INTO BOOK_AUTHOR (BOOK_ID, AUTHOR_ID) VALUES
(1, 3),
(2, 2),
(3, 1);

INSERT INTO USERS(USERNAME,PASSWORD) VALUES
('admin','password'),
('user','password');

INSERT INTO ROLES(ROLE,DESCRIPTION) VALUES
('ROLE_WRITE','Администратор'),
('ROLE_ADMINISTRATION','Администратор'),
('ROLE_READ','Администратор');

INSERT INTO USER_ROLES (USERNAME, ROLE) VALUES
('admin', 'ROLE_WRITE'),
('admin', 'ROLE_ADMINISTRATION'),
('user', 'ROLE_READ');

-- Субъект авторизации
INSERT INTO acl_sid (id, principal, sid) VALUES
(100, 1, 'admin'),
(101, 1, 'user'),
(102, 0, 'ROLE_WRITE'),
(103, 0, 'ROLE_READ'),
(104, 0, 'ROLE_ADMINISTRATION');

-- Класс объекта идентификации
INSERT INTO acl_class (id, class) VALUES (200, 'ru.gds.spring.domain.Book');

-- Объект идентификации
INSERT INTO acl_object_identity (id, object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES
(300, 200, 0, NULL, 102, 0),  -- id Book bookId=0 parent=null owner=ROLE_WRITE mask=read
(301, 200, 1, NULL, 102, 0),  -- id Book bookId=1 parent=null owner=ROLE_WRITE mask=read
(302, 200, 2, NULL, 102, 0);  -- id Book bookId=2 parent=null owner=ROLE_WRITE mask=read

-- Связка Объект идентификации-Субъект авторизации
INSERT INTO acl_entry (id, acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) VALUES
(401, 300, 1, 100, 1, 1, 1, 1), -- id book=1 order=1 sid=admin mask=read
(402, 301, 1, 100, 1, 1, 1, 1), -- id book=2 order=1 sid=admin mask=read
(413, 302, 1, 100, 1, 1, 1, 1), -- id book=3 order=1 sid=admin mask=read

(404, 300, 2, 101, 1, 1, 1, 1), -- id book=1 order=2 sid=user mask=read
(405, 301, 2, 101, 1, 1, 1, 1), -- id book=2 order=2 sid=user mask=read
(416, 302, 2, 101, 1, 1, 1, 1); -- id book=3 order=2 sid=user mask=read
