#!/usr/bin/env bash
set -euo pipefail

# A disposable server/domain, never a user's installed GlassFish domain.
task_dir=$(mktemp -d)
curl --fail --location --retry 3 \
  https://repo.maven.apache.org/maven2/org/glassfish/main/distributions/web/8.0.5/web-8.0.5.zip \
  --output "$task_dir/glassfish.zip"
echo "5d8f8845316e415d818cb1f1a3574088578487d251392a1e4b2f45697a2681cf  $task_dir/glassfish.zip" | sha256sum --check
unzip -q "$task_dir/glassfish.zip" -d "$task_dir"
asadmin="$task_dir/glassfish8/glassfish/bin/asadmin"
test -n "$asadmin"
server_dir=$(dirname "$(dirname "$asadmin")")
cleanup() {
  cat "$server_dir/domains/domain1/logs/server.log" 2>/dev/null || true
  "$asadmin" stop-domain domain1 || true
}
trap cleanup EXIT
"$asadmin" start-domain domain1
"$asadmin" deploy --name pokerserver --contextroot pokerserver "$PWD/target/pokerserver.war"
curl --fail --silent --show-error --retry 10 --retry-connrefused --retry-delay 2 \
  http://localhost:8080/pokerserver/ping > "$task_dir/ping.json"
python3 - "$task_dir/ping.json" <<'PY'
import json, sys
with open(sys.argv[1]) as response:
    assert json.load(response) == {"success": True}
PY
# Exercise JSON serialization and a database-backed write/read, not just startup.
curl --fail --silent --show-error --get \
  --data-urlencode gameName=GlassFishSmoke \
  --data-urlencode gameStructure=TWO_HR_SIXPPL \
  http://localhost:8080/pokerserver/create > "$task_dir/game.json"
game_id=$(python3 - "$task_dir/game.json" <<'PY'
import json, sys
with open(sys.argv[1]) as response:
    value = json.load(response)["gameId"]
    assert isinstance(value, int) and value > 0
    print(value)
PY
)
curl --fail --silent --show-error --get --data-urlencode "gameId=$game_id" \
  http://localhost:8080/pokerserver/gamestatus > "$task_dir/status.json"
python3 - "$task_dir/status.json" <<'PY'
import json, sys
with open(sys.argv[1]) as response:
    assert json.load(response)["gameStatus"] == "NOT_STARTED"
PY
