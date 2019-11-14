## 基于Annotation-Processor 注解处理器的自动生成spring.factories文件

###自动生成META/services/javax.annotation.processing.Processor 文件

util-automodule中的pom.xml文件中，依赖对应的google-auto的开源编译包，通过@AutoService(Processor.class)注解
自动生成对应的META/services/javax.annotation.processing.Processor文件，并且之前通过注解@AutoService(Processor.class)生成的类文件
```
<dependencies>
    <!-- 依赖google auto包，使用@AutoService生成Processor注解处理器文件 META-INF/services/javax.annotation.processing.Processor -->
    <dependency>
        <groupId>com.google.auto.service</groupId>
        <artifactId>auto-service</artifactId>
        <version>${google-auto-service.version}</version>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <configuration>
                <annotationProcessorPaths>
                    <path>
                        <groupId>com.google.auto.service</groupId>
                        <artifactId>auto-service</artifactId>
                        <version>${google-auto-service.version}</version>
                    </path>
                </annotationProcessorPaths>
            </configuration>
        </plugin>
    </plugins>
</build>
```

执行mvn clean package -Dmaven.test.skip=true 编译包文件即可


### 版本引用，通过引用util-auto模块
```
<dependency>
    <groupId>com.zcswl</groupId>
    <artifactId>util-auto</artifactId>
    <version>${util-common.version}</version>
    <!-- https://blog.csdn.net/yh_zeng2/article/details/82562596 -->
    <optional>true</optional>
</dependency>
```
在进行编译当前模块时，会扫描当前包中存在的@Component，@Configration 注解，自动装配，并生成
META-INF/spring.factories 和 META-INF/spring-devtools.properties文件

### 参考
- mica-atuo：https://github.com/lets-mica/mica-auto

该开源包依赖于mic-auto中的自动生成spring.factories类