# Build local 
````
mvn clean install  -Dspring.profiles.active=local

java  -Dspring.profiles.active=local -jar ssm-0.0.1-SNAPSHOT.jar
````
# create paramter store

````
key /config/hub/db.username :  value - hello world
key - /config/hub/categories.types :  value - zone001,zone002,zone003
````

````
 aws ssm get-parameters-by-path --path "/config/hub/"
 {
     "Parameters": [
         {
             "Name": "/config/hub/categories.types",
             "Type": "String",
             "Value": "zone001,zone002,zone003",
             "Version": 1,
             "LastModifiedDate": "2020-12-01T16:39:44.823000+07:00",
             "ARN": "arn:aws:ssm:ap-southeast-1:xxxxxxxxxxxx:parameter/config/hub/categories.types",
             "DataType": "text"
         },
         {
             "Name": "/config/hub/db.username",
             "Type": "String",
             "Value": "hello world",
             "Version": 1,
             "LastModifiedDate": "2020-12-01T15:58:40.870000+07:00",
             "ARN": "arn:aws:ssm:ap-southeast-1:xxxxxxxxxxxx:parameter/config/hub/db.username",
             "DataType": "text"
         }
     ]
 }


````

# bootstrap.yml
````
aws:
    paramstore:
        prefix: /config
        name: hub
        enabled: true
        profile-separator: _
        default-context: hub
        fail-fast: true

cloud:
    aws:
        region:
            static: ap-southeast-1
        stack:
            auto: false

````
# Deploy and run in AWS EC2 server
````
[ec2-user@ip-10-102-1-51 ~]$ java  -Dserver.port=8086 -jar ssm-0.0.1-SNAPSHOT.jar

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.3.6.RELEASE)

2020-12-01 16:44:06.194  INFO 13692 --- [           main] b.c.PropertySourceBootstrapConfiguration : Located property source: [BootstrapPropertySource {name='bootstrapProperties-/config/hub/'}]
2020-12-01 16:44:06.206  INFO 13692 --- [           main] c.sma.parameterstore.ssm.SsmApplication  : No active profile set, falling back to default profiles: default
2020-12-01 16:44:07.139  INFO 13692 --- [           main] o.s.cloud.context.scope.GenericScope     : BeanFactory id=3320e53a-839d-3015-a080-7116960a37e9
2020-12-01 16:44:07.517  INFO 13692 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8086 (http)
2020-12-01 16:44:07.533  INFO 13692 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
2020-12-01 16:44:07.534  INFO 13692 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.39]
2020-12-01 16:44:07.621  INFO 13692 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2020-12-01 16:44:07.622  INFO 13692 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1386 ms
2020-12-01 16:44:07.910  INFO 13692 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2020-12-01 16:44:08.334  INFO 13692 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8086 (http) with context path ''
2020-12-01 16:44:08.351  INFO 13692 --- [           main] c.sma.parameterstore.ssm.SsmApplication  : Started SsmApplication in 5.465 seconds (JVM running for 6.514)
2020-12-01 16:44:08.353  INFO 13692 --- [           main] c.sma.parameterstore.ssm.SsmApplication  : Value dbUsername : hello world
2020-12-01 16:44:08.355  INFO 13692 --- [           main] c.sma.parameterstore.ssm.SsmApplication  : categories : 3 - [zone001, zone002, zone003]


````