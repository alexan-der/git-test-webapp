INSERT INTO public.db_version
  (version, comment)
VALUES
  ('1.2', 'The part data is inserted.');

INSERT INTO public.part
  (part_name, part_number, vendor, qty, shipped, receive)
VALUES
  ('Plug-A', '945102-DBF', 'MTF', 24, '2017-12-12', '2018-01-15'),
  ('Thermostat', 'SYN-234234', 'Powerman', 14, '2018-01-12', '2018-01-15'),
  ('MT Fluid', '459645-12', 'Fluided', 6, '2018-01-10', '2018-02-01'),
  ('PCV Valve', 'OL44512', 'Strongg', 19, '2017-12-10', '2018-02-02'),
  ('Air Filter', '98746FF541', 'Mahnn', 32, '2018-01-05', '2018-03-17'),
  ('Mount', 'E-154-54', 'JFE', 2, '2018-04-25', null),
  ('Switch Plate', '548E-F54', 'JJYS', 3, '2018-03-26', '2018-04-30'),
  ('Brake Pads', 'A34F54', 'Braker', 10, '2018-04-02', '2018-04-20'),
  ('Bumper', '4551754', 'JH3', 1, '2018-02-20', null),
  ('Link', 'R5434D', 'SKF', 3, '2018-04-10', '2018-04-12');