CREATE TABLE user_alert_subscription
(
    user_id     BIGINT NOT NULL,
    district_id BIGINT NOT NULL
);

ALTER TABLE user_alert_subscription
    ADD FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE;

ALTER TABLE user_alert_subscription
    ADD FOREIGN KEY (district_id)
        REFERENCES districts (id)
        ON DELETE CASCADE;