
#to store the pwd outside of git
#export NEXUS_PWD=admin123
source pwd.sh

export NEXUS_HOST="artifact.scaling.ch"
export NEXUS_URL="https://$NEXUS_HOST"
export NEXUS_USER=admin
export NEXUS_SCRIPTING_PATH="$NEXUS_URL/service/siesta/rest/v1/script"

# fail if anything errors
set -e
# fail if a function call is missing an argument
set -u

function nxlistscripts {
  curl -v -u $NEXUS_USER:$NEXUS_PWD $NEXUS_SCRIPTING_PATH
}

function nxrunscript {
  name=$1
  curl -v -X POST -u $NEXUS_USER:$NEXUS_PWD --header "Content-Type: text/plain" "$NEXUS_SCRIPTING_PATH/$name/run"
}

function nxrunscriptjarg {
  name=$1
  jsonargs=$2
  curl -v -X POST -u $NEXUS_USER:$NEXUS_PWD --header "Content-Type: text/plain" -d $jsonargs "$NEXUS_SCRIPTING_PATH/$name/run"
}

# add a script to the repository manager and run it
function nxaddscript {
  name=$1
  file=$2
  # using grape config that points to local Maven repo and Central Repository , default grape config fails on some downloads although artifacts are in Central
  # change the grapeConfig file to point to your repository manager, if you are already running one in your organization
  groovy -Dgroovy.grape.report.downloads=true -Dgrape.config=grapeConfig.xml addUpdatescript.groovy -u "$NEXUS_USER" -p "$NEXUS_PWD" -n "$name" -f "$file" -h "$NEXUS_URL"
  printf "\nPublished $file as $name\n\n"
}

