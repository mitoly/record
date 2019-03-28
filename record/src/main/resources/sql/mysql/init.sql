--初始化数据
insert into ts_user (ACCOUNT, PASSWORD, USER_NAME, PHONE, GENDER, IS_ENABLED, MARK_FOR_DELETE, CREATE_TIME) values ('admin', '1', '管理员', '10086', '男', '是', '0', sysdate());
insert into ts_role (ROLE_CODE, ROLE_NAME, MARK_FOR_DELETE) values ('admin', '系统管理员', '0');
insert into tr_user_role (USER_ID, ROLE_ID) values (1, 1);
insert into tr_role_permission_button(ROLE_ID, PERMISSION_BUTTON_ID) values (1, (select ID from ts_permission_button where PERMISSION_CODE = 'ROLE_ADD'));
insert into tr_role_permission_button(ROLE_ID, PERMISSION_BUTTON_ID) values (1, (select ID from ts_permission_button where PERMISSION_CODE = 'ROLE_EDIT'));
insert into tr_role_permission_button(ROLE_ID, PERMISSION_BUTTON_ID) values (1, (select ID from ts_permission_button where PERMISSION_CODE = 'ROLE_REMOVE'));
insert into tr_role_permission_button(ROLE_ID, PERMISSION_BUTTON_ID) values (1, (select ID from ts_permission_button where PERMISSION_CODE = 'ROLE_PERMISSION'));
insert into tr_role_permission_table(ROLE_ID, PERMISSION_TABLE_ID) values (1, (select ID from ts_permission_table where PERMISSION_CODE = 'ROLE_CODE'));
insert into tr_role_permission_table(ROLE_ID, PERMISSION_TABLE_ID) values (1, (select ID from ts_permission_table where PERMISSION_CODE = 'ROLE_NAME'));