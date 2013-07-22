-- You can use this file to load seed data into the database using SQL statements
insert into Employee (empId, empFName, empLName, empUserName, empRole) values (1, 'Joe', 'Smith', 'jsmith', 'HR');
insert into Principle (empId, empPassword) values (1, '78675cc176081372c43abab3ea9fb70c74381eb02dc6e93fb6d44d161da6eeb3');
insert into Employee (empId, empFName, empLName, empUserName, empRole) values (2, 'Clark', 'Kent', 'supervisor', 'SUPERVISOR');
insert into Principle (empId, empPassword) values (2, '78675cc176081372c43abab3ea9fb70c74381eb02dc6e93fb6d44d161da6eeb3');
insert into Employee (empId, empFName, empLName, empUserName, empRole, empSupervisor) values (3, 'Joe', 'Blow', 'user', 'USER', 2);
insert into Principle (empId, empPassword) values (3, '78675cc176081372c43abab3ea9fb70c74381eb02dc6e93fb6d44d161da6eeb3');
insert into Employee (empId, empFName, empLName, empUserName, empRole) values (4, 'Boss', 'Man', 'manager', 'USER');
insert into Principle (empId, empPassword) values (4, '78675cc176081372c43abab3ea9fb70c74381eb02dc6e93fb6d44d161da6eeb3');
insert into PayLevel (plLevel, plRate) values ('P1', 100.0);
insert into PayLevel (plLevel, plRate) values ('P2', 200.0);
insert into PayLevel (plLevel, plRate) values ('P3', 300.0);
insert into PayLevel (plLevel, plRate) values ('P4', 400.0);
insert into PayLevel (plLevel, plRate) values ('P5', 500.0);
insert into PayLevel (plLevel, plRate) values ('P6', 600.0);
insert into PayLevel (plLevel, plRate) values ('JS', 700.0);
insert into PayLevel (plLevel, plRate) values ('DS', 800.0);
insert into PayLevel (plLevel, plRate) values ('SS', 900.0);