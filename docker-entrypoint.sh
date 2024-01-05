#!/bin/bash

set -eo pipefail
shopt -s nullglob

file_env() {
	local var="$1"
	local fileVar="${var}_FILE"
	local def="${2:-}"

	if [ "${!var:-}" ] && [ "${!fileVar:-}" ]; then
		echo >&2 "error: both $var and $fileVar are set (but are exclusive)"
		exit 1
	fi

	local val="$def"
	if [ "${!var:-}" ]; then
		val="${!var}"
	elif [ "${!fileVar:-}" ]; then
		val="$(< "${!fileVar}")"
	fi
	export "$var"="$val"
	unset "$fileVar"
}

file_env 'SPRING_DATASOURCE_PASSWORD'
file_env 'API_TOKEN_SECRET'

exec java -jar -Dspring.profiles.active=docker tcc_audit.jar