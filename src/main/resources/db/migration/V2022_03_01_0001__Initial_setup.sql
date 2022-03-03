CREATE TABLE "districts"
(
    "id"       BIGSERIAL PRIMARY KEY,
    "name"     VARCHAR(40)   NOT NULL,
    "code"     VARCHAR(30)   NOT NULL,
    "geo_json" VARCHAR NOT NULL
);

CREATE TABLE "users"
(
    "id" BIGSERIAL PRIMARY KEY,
    "telegram_user_id" VARCHAR NOT NULL,
    "telegram_username" VARCHAR NOT NULL,
    "is_admin" BOOLEAN
);