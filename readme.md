Nexus scripts
=============
Simple scripts running within nexus to get stats and delete old components.
Requires bash (or cygwin etc.) and groovy


How to use
----------
Define pwd.sh 

    export NEXUS_PWD=admin123

Bash support

    bash-3.2$ source nexusScripter.sh 
    
Upload a script
    
    bash-3.2$ nxaddscript repoStats src/main/groovy/repoStats.groovy
    Resolving dependency: org.apache.james#apache-mime4j;0.6 {default=[default]}
    Resolving dependency: com.sun.mail#javax.mail;1.5.6 {default=[default]}
    ...
    
Run non argument script    
    
    bash-3.2$ nxrunscript repoStats
    *   Trying 10.3.1.64...
    * Connected to artifact.scaling.ch (10.3.1.64) port 443 (#0)
    * TLS 1.2 connection using TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384
    * Server certificate: *.scaling.ch
    * Server certificate: SwissSign Server Gold CA 2014 - G22
    * Server certificate: SwissSign Gold CA - G2
    * Server auth using Basic with user 'admin'
    > POST /service/siesta/rest/v1/script/repoStats/run HTTP/1.1
    > Host: artifact.scaling.ch
    > Authorization: Basic YWRtaW46cFFQSGh3ZDhiSmF1X252Yw==
    > User-Agent: curl/7.43.0
    > Accept: */*
    > Content-Type: text/plain
    > 
    < HTTP/1.1 400 Bad Request
    < Server: nginx/1.10.2
    < Date: Tue, 03 Oct 2017 14:35:54 GMT
    < Content-Type: application/json
    < Content-Length: 152
    < Connection: keep-alive
    < X-Frame-Options: SAMEORIGIN
    < X-Content-Type-Options: nosniff
    ...
    
Run script with json args
    
    nxrunscriptjarg deleteOldComps '{"repoName":"maven-releases","beforeDate":"2017-07-01","delete":"true"}'
    
Scripts
-------

###repoStats 
sum component count per repo

    bash-3.2$ nxrunscript repoStats
    
    {
      "name" : "repoStats",
      "result" : "{\"repos\":[{\"componentCount\":0,\"name\":\"maven-kamonbintray\"},{\"componentCount\":0,\"name\":\"ivy-releases\"},{\"componentCount\":13,\"name\":\"maven-jboss\"},{\"componentCount\":0,\"name\":\"nuget.org-proxy\"},{\"componentCount\":43177,\"name\":\"maven-releases\"},{\"componentCount\":26,\"name\":\"maven-thirdparty\"},{\"componentCount\":8,\"name\":\"npm-thirdparty\"},{\"componentCount\":0,\"name\":\"ivy-typesafe-releases\"},{\"componentCount\":0,\"name\":\"npm\"},{\"componentCount\":0,\"name\":\"maven-typesafe\"},{\"componentCount\":6255,\"name\":\"npm-npmjs\"},{\"componentCount\":0,\"name\":\"ivy\"},{\"componentCount\":0,\"name\":\"maven-sonatype\"},{\"componentCount\":208,\"name\":\"npm-internal\"},{\"componentCount\":0,\"name\":\"ivy-sbt-plugin-releases\"},{\"componentCount\":33194,\"name\":\"maven-snapshots\"},{\"componentCount\":3946,\"name\":\"maven-central\"},{\"componentCount\":0,\"name\":\"nuget-group\"},{\"componentCount\":0,\"name\":\"maven-sbt-bintray\"},{\"componentCount\":3,\"name\":\"maven-jcenterbintray\"},{\"componentCount\":0,\"name\":\"nuget-hosted\"},{\"componentCount\":0,\"name\":\"maven-public\"}]}"


###detailRepoStats
component count (all versions) and component sum per month

    bash-3.2$ nxrunscriptjarg detailRepoStats '{"repoName":"maven-releases"}'
    
    aling:test-app_2.11\":11,\"ch.scaling:test-login-app\":447,\"ch.scaling:test-login-app_2.11\":1,\"ch.scaling:testaccess-general_2.11\":9,\"ch.scaling:testaccess-jaso_2.11\":7,\"ch.scaling:testui-route_2.11\":26,\"ch.scaling:time_2.11\":39,\"ch.scaling:tx-api_2.11\":156,\"ch.scaling:tx-persistence_2.11\":101,\"ch.scaling:tx-steps-persistence_2.11\":101,\"ch.scaling:tx-steps_2.11\":34,\"ch.scaling:tx_2.11\":34,\"ch.scaling:user-api_2.11\":126,\"ch.scaling:user-persistence_2.11\":64,\"ch.scaling:user-steps-persistence_2.11\":101,\"ch.scaling:user-steps_2.11\":34,\"ch.scaling:user_2.11\":184,\"ch.scaling:wealth-api_2.11\":8,\"ch.scaling:wealth-persistence_2.11\":80,\"ch.scaling:wealth_2.11\":15,\"ch.scaling:webtest_2.11\":34,\"ch.scaling:worth-api_2.11\":35,\"ch.scaling:worth-app\":66,\"ch.scaling:worth-commons_2.12\":202,\"ch.scaling:worth-domain-persistence_2.11\":2,\"ch.scaling:worth-fixture-descriptor_2.11\":58,\"ch.scaling:worth-fixture-descriptor_2.12\":300,\"ch.scaling:worth-fixture_2.11\":58,\"ch.scaling:worth-fixture_2.12\":300,\"ch.scaling:worth-persistence_2.11\":83,\"ch.scaling:worth-routes_2.11\":43,\"ch.scaling:worth-routes_2.12\":300,\"ch.scaling:worth-services_2.11\":43,\"ch.scaling:worth-services_2.12\":300,\"ch.scaling:worth-setup_2.11\":43,\"ch.scaling:worth-setup_2.12\":300,\"ch.scaling:worth-steps-api_2.11\":16,\"ch.scaling:worth-steps-api_2.12\":132,\"ch.scaling:worth-steps-persistence_2.11\":90,\"ch.scaling:worth-steps-persistence_2.12\":46,\"ch.scaling:worth-steps_2.11\":58,\"ch.scaling:worth-steps_2.12\":300,\"ch.scaling:worth-systemtest_2.11\":58,\"ch.scaling:worth-systemtest_2.12\":300,\"ch.scaling:worth-task_2.12\":74,\"ch.scaling:worth-test-app-local\":292,\"ch.scaling:worth-testui-route_2.12\":292,\"ch.scaling:worth-wealth-api_2.11\":8,\"ch.scaling:worth-wealth-api_2.12\":132,\"ch.scaling:worth-wealth-persistence_2.11\":8,\"ch.scaling:worth-wealth-persistence_2.12\":47,\"ch.scaling:worth-wealth_2.11\":13,\"ch.scaling:worth-wealth_2.12\":300,\"ch.scaling:worth_2.11\":30},\"compsCountsGrouped\":{\"2016\":{\"1\":0,\"2\":0,\"3\":0,\"4\":0,\"5\":0,\"6\":0,\"7\":0,\"8\":0,\"9\":0,\"10\":0,\"11\":61,\"12\":2674},\"2017\":{\"1\":4823,\"2\":5743,\"3\":8326,\"4\":4362,\"5\":3745,\"6\":4190,\"7\":5065,\"8\":2626,\"9\":6380,\"10\":826,\"11\":0,\"12\":0}}}"

###deleteOldComps
delete comps older than

upload

    nxaddscript deleteOldComps src/main/groovy/deleteOldComps.groovy 

dry run (show components to be deleted)
        
    nxrunscriptjarg deleteOldComps '{"repoName":"maven-releases","beforeDate":"2017-07-01"}'
    
delete components. depending on number of components the http connection might timeout but the script continues...    
    
    nxrunscriptjarg deleteOldComps '{"repoName":"maven-releases","beforeDate":"2017-07-01","delete":"true"}'
