#!/usr/bin/env bash

set -euo pipefail

DB_APP_DB=haitaton
DB_APP_DB_TEST=haitaton_test
DB_APP_USER=haitaton_user
DB_APP_PASSWORD=haitaton
POSTGRES_USER=postgres

echo "Creating database \"$DB_APP_DB\", creating role \"$DB_APP_USER\" with database owner privileges…"

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-END
create role "${DB_APP_USER}" with password '${DB_APP_PASSWORD}' login;
create database "${DB_APP_DB}" encoding 'UTF-8' LC_COLLATE = 'fi_FI.UTF-8';
create database "${DB_APP_DB_TEST}" encoding 'UTF-8' LC_COLLATE = 'fi_FI.UTF-8';
grant all privileges on database "${DB_APP_DB}" to "${DB_APP_USER}";
grant all privileges on database "${DB_APP_DB_TEST}" to "${DB_APP_USER}";
END
