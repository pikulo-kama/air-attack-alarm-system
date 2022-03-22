ALTER TABLE "users" DROP COLUMN "request_message";
ALTER TABLE "users" ADD COLUMN "administration_request_sent" BOOLEAN NOT NULL DEFAULT FALSE;