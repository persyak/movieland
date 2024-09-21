create index if not exists index_rating on movieland.movie
(rating desc);

create index if not exists index_price_asc on movieland.movie
(price asc);

create index if not exists index_price_desc on movieland.movie
(price desc);