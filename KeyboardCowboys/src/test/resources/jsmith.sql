-- You can use this file to load seed data into the database using SQL statements
insert into Employee (empId, empFName, empLName, empUserName, empRole) values (1, 'Joe', 'Smith', 'jsmith', 'HR');
insert into Principle (empId, empPassword) values (1, '78675cc176081372c43abab3ea9fb70c74381eb02dc6e93fb6d44d161da6eeb3');
insert into Employee (empId, empFName, empLName, empUserName, empRole) values (2, 'Clark', 'Kent', 'supervisor', 'SUPERVISOR');
insert into Principle (empId, empPassword) values (2, '78675cc176081372c43abab3ea9fb70c74381eb02dc6e93fb6d44d161da6eeb3');
insert into PayLevel (plLevel, plRate) value ('P1', 100);
insert into PayLevel (plLevel, plRate) value ('P2', 200);
insert into PayLevel (plLevel, plRate) value ('P3', 300);
insert into PayLevel (plLevel, plRate) value ('P4', 400);
insert into PayLevel (plLevel, plRate) value ('P5', 500);
insert into PayLevel (plLevel, plRate) value ('P6', 600);
insert into PayLevel (plLevel, plRate) value ('JS', 700);
insert into PayLevel (plLevel, plRate) value ('DS', 800);
insert into PayLevel (plLevel, plRate) value ('SS', 900);