INSERT INTO public.notionnodedata (name, category_id) VALUES
-- Category: Sport (category_id = 1)
    ('rower', 1),
    ('piłka', 1),
    ('narty', 1),
    ('koszykówka', 1),
    ('siatkówka', 1),
    ('boks', 1),
    ('bieg', 1),
    ('hokej', 1),
    ('pływanie', 1),
    ('kajaki', 1),
    ('wspinaczka', 1),

-- Category: Napoje (category_id = 2)
    ('kawa', 2),
    ('herbata', 2),
    ('sok', 2),
    ('piwo', 2),
    ('wino', 2),
    ('woda', 2),
    ('koktajl', 2),

-- Category: Jedzenie (category_id = 3)
    ('chleb', 3),
    ('jabłko', 3),
    ('banan', 3),
    ('wołowina', 3),
    ('wiepszowina', 3),
    ('cielęcina', 3),
    ('boczek', 3),
    ('barszcz', 3),
    ('kaszanka', 3),
    ('frytki', 3),
    ('ciasteczka', 3),
    ('kaczka', 3),
    ('ryby', 3),
    ('wegateriańskie', 3),

-- Category: Natura (category_id = 4)
    ('morze', 4),
    ('góry', 4),
    ('las', 4),
    ('jezioro', 4),

-- Category: Mieszkanie (category_id = 5)
    ('mieszkanie', 5),
    ('hotel', 5),
    ('hostel', 5),
    ('apartamenty', 5),
    ('schronisko', 5),

-- Category: Lokale (category_id = 6)
    ('kawiarnia', 6),
    ('restauracja', 6),
    ('bar', 6),
    ('pub', 6),
    ('fastfood', 6),
    ('karczma', 6),

-- Category: Transport (category_id = 7)
    ('samolot', 7),
    ('somochód', 7),
    ('rower', 7),
    ('autobus', 7),
    ('helikopter', 7),
    ('motor', 7),
    ('skuter', 7),
    ('statek', 7),
    ('pieszo', 7),

-- Category: Rozrywka (category_id = 8)
    ('kino', 8),
    ('bowling', 8),
    ('bilard', 8),
    ('snooker', 8),
    ('teatr', 8),
    ('taniec', 8),
    ('muzeum', 8),
    ('zoo', 8),

-- Category: Pora roku (category_id = 9)
    ('lato', 9),
    ('jesień', 9),
    ('zima', 9),
    ('wiosna', 9),

-- Category: Podróże (category_id = 10)
    ('camping', 10),
    ('nurkowanie', 10),
    ('jacht', 10),
    ('wspinaczka', 10),
    ('widoki', 10),

-- Category: Udogodnienia (category_id = 11)
    ('klimatyzacja', 11),
    ('telefon', 11),
    ('Internet', 11),
    ('WiFi', 11),
    ('komputer', 11)

on conflict (name) do nothing;
