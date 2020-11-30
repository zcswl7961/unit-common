#### java通用模型包创建

目录格式

------

```
├─src
│  ├─main
│  │  ├─java
│  │  │                      
│  │  └─resources
│  │      ├─bin
│  │      │      platform.sh // 通用脚本启动
│  │      │      
│  │      └─config
│  │              application-dev.yml // dev 环境
│  │              application-prod.yml // prod生产环境
│  │              application.yml // 默认
│  │              spring-logback.xml // logback
│  │              
│  └─test
│      └─java  // 单元测试
└─target // 打包路径
├─assembly.xml // assembly打包配置
├─pom.xml  // pom 配置
├─README.md // readme文档 
```

