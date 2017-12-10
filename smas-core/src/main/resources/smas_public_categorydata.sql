INSERT INTO public.categorydata (id, name) VALUES
    (1, 'Sport'),
    (2, 'Napoje'),
    (3, 'Jedzenie'),
    (4, 'Natura'),
    (5, 'Mieszkanie'),
    (6, 'Lokale'),
    (7, 'Transport'),
    (8, 'Rozrywka'),
    (9, 'Pora roku'),
    (10, 'Podróże'),
    (11, 'Udogodnienia')
on conflict (id) do nothing;
