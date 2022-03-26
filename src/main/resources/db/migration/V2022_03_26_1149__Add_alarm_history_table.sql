CREATE TABLE alarm_history (
    alarm_record_id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    district_id BIGINT,
    alarm_state VARCHAR,
    performed_at TIMESTAMP
);

ALTER TABLE alarm_history
    ADD FOREIGN KEY (user_id)
        REFERENCES users (id)
        ON DELETE CASCADE;

ALTER TABLE alarm_history
    ADD FOREIGN KEY (district_id)
        REFERENCES districts (id)
        ON DELETE CASCADE;