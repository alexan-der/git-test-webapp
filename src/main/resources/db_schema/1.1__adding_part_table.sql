INSERT INTO public.db_version
  (version, comment)
VALUES
  ('1.1', 'The part table is added.');

CREATE TABLE public.part
(
    id bigserial PRIMARY KEY NOT NULL,
    part_name varchar(255),
    part_number varchar(255),
    vendor varchar(255),
    qty int,
    shipped timestamp without time zone,
    receive timestamp without time zone
);
CREATE UNIQUE INDEX part_id_uindex ON public.part (id);
COMMENT ON TABLE public.part IS 'Table for the parts information.';