    bash-3.2$ source nexusScripter.sh 
    
    bash-3.2$ nxaddscript repoStats src/main/groovy/repoStats.groovy
    Resolving dependency: org.apache.james#apache-mime4j;0.6 {default=[default]}
    Resolving dependency: com.sun.mail#javax.mail;1.5.6 {default=[default]}
    ...
    
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
