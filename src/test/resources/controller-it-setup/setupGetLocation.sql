INSERT INTO users (id, first_name, last_name, email) VALUES ( '1', 'Potato', 'Development', 'sweetpotatodevelopment@gmail.com');
INSERT INTO users (id, first_name, last_name, email) VALUES ( '2', 'Alena', 'Bekrina', 'abekrina@gmail.com');
INSERT INTO groups (id, name, identity) VALUES (1, 'Default', 'i1d2e3n4t5i6t7y8');
INSERT INTO usersingroups (id, user_id, group_id, joined_at) VALUES (1, 1, 1, {ts '2012-09-17 18:47:52.69'});
INSERT INTO usersingroups (id, user_id, group_id, joined_at) VALUES (2, 2, 1, {ts '2016-02-17 19:47:52.69'});
INSERT INTO locations (id, user_id, timestamp, latitude, longitude, ip, group_identity) VALUES (1, 2, {ts '2016-02-17 19:47:52.69'}, 37.345345, -121.232323, '127.0.0.1', 'i1d2e3n4t5i6t7y8');