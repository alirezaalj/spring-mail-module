# spring-mail-module
My Starter Mail Module - send Mails With html templates using thymeleaf engin
## How to use
GitHub Repository: add to **`pom.xml`** File
```
    <repositories>
        <repository>
            <id>PROJECT-REPO-URL</id>
            <url>https://raw.githubusercontent.com/alirezaalj/spring-mail-module/master/target/mvn-artifact/</url>
                <snapshots>
                    <enabled>true</enabled>
                    <updatePolicy>always</updatePolicy>
                </snapshots>
        </repository>
    </repositories>
```

Module Dependency: add in **`pom.xml`** inside **`dependencies`** section

```
        <dependency>
            <groupId>ir.alirezaalijani</groupId>
            <artifactId>spring-mail-module</artifactId>
            <version>0.0.1</version>
        </dependency>
```

Then add new Configuration Class in your code

```java
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScans({
        @ComponentScan(basePackages = "ir.alirezaalijani.spring.mail.module.*")
})
public class Config {

}
```

## Usage: 
will be add as soon as 
