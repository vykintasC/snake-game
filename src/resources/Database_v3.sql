PGDMP                          x            Snake_DB    12.1    12.1                0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            	           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            
           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false                       1262    16433    Snake_DB    DATABASE     ?   CREATE DATABASE "Snake_DB" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';
    DROP DATABASE "Snake_DB";
                postgres    false            ?            1259    16434    scores    TABLE     z   CREATE TABLE public.scores (
    user_id integer NOT NULL,
    score integer NOT NULL,
    game_name character varying
);
    DROP TABLE public.scores;
       public         heap    postgres    false            ?            1259    16437    users    TABLE     ?   CREATE TABLE public.users (
    user_id integer NOT NULL,
    username character varying(64),
    password character varying(64)
);
    DROP TABLE public.users;
       public         heap    postgres    false            ?            1259    16440    users_user_id_seq1    SEQUENCE     ?   CREATE SEQUENCE public.users_user_id_seq1
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 )   DROP SEQUENCE public.users_user_id_seq1;
       public          postgres    false    203                       0    0    users_user_id_seq1    SEQUENCE OWNED BY     H   ALTER SEQUENCE public.users_user_id_seq1 OWNED BY public.users.user_id;
          public          postgres    false    204            ?
           2604    16442    users user_id    DEFAULT     o   ALTER TABLE ONLY public.users ALTER COLUMN user_id SET DEFAULT nextval('public.users_user_id_seq1'::regclass);
 <   ALTER TABLE public.users ALTER COLUMN user_id DROP DEFAULT;
       public          postgres    false    204    203                      0    16434    scores 
   TABLE DATA           ;   COPY public.scores (user_id, score, game_name) FROM stdin;
    public          postgres    false    202   e                 0    16437    users 
   TABLE DATA           <   COPY public.users (user_id, username, password) FROM stdin;
    public          postgres    false    203   ?                  0    0    users_user_id_seq1    SEQUENCE SET     @   SELECT pg_catalog.setval('public.users_user_id_seq1', 4, true);
          public          postgres    false    204                  x?3?4437??-JO?J?????? ,?N         N   x?3?t?T1JT14R?()?2uO?H?w??ԫ6?5?,կp.s/p?K?(O??(?1/?t???2??v??????? ?l?     