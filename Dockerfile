FROM openjdk:17              
VOLUME /tmp
EXPOSE 8080                   
ADD target/DaznWalletGateway-0.0.1-SNAPSHOT.jar DaznWalletGateway-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-Dspring.profiles.active=Dev","-jar","/DaznWalletGateway-0.0.1-SNAPSHOT.jar"]