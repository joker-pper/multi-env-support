
# multi-env-support

    (spring boot)存在多个环境且每个环境都拥有不同的配置的场景支持


## multi-env-samples

   + (spring boot)简单总结存在多环境拥有不同的配置的情况下如何进行引入的示例


> 配置案例


### 各环境配置值 

| 环境      |    ak | sk |token |other |
|:-----:|:-----:|:-----:|:-----:|:-----:|
| default     | 111 |  qwerty  |12345 |ssss |
| dev     | 222 |  zxc  |12345 | |

    注: 当前激活环境为dev

### 环境变量名称中携带环境key区分

#### 在application.properties/application.yml中进行配置: 详见sample01

+ application.properties

```

sys.env=${SYS_ENV:dev}

sys.default.ak=111
sys.default.sk=qwerty
sys.default.token=12345
sys.default.other=ssss

sys.dev.ak=222
sys.dev.sk=zxc
sys.dev.token=${sys.default.token}

```

#### 在@PropertySource引入对应的配置文件进行配置相关的变量(单独一个配置文件): 详见sample02

+ @PropertySource Config

```
@Configuration
@PropertySource({"classpath:sys-env.properties"})
public class ImportEnvConfig {
}
```

+ sys-env.properties

```

sys.env=${SYS_ENV:dev}

sys.default.ak=111
sys.default.sk=qwerty
sys.default.token=12345
sys.default.other=ssss

sys.dev.ak=222
sys.dev.sk=zxc
sys.dev.token=${sys.default.token}

```

#### 在@PropertySource引入对应的配置文件进行配置相关的变量(default及各环境的配置文件): 详见sample03


+ @PropertySource Config

```
@Configuration
@PropertySource({"classpath:sys-env.properties", "classpath:sys-env-${sys.env}.properties"})
public class ImportEnvConfig {
}

```

+ sys-env.properties

```

sys.env=${SYS_ENV:dev}

sys.default.ak=111
sys.default.sk=qwerty
sys.default.token=12345
sys.default.other=ssss

```
+ sys-env-dev.properties

```

sys.dev.ak=222
sys.dev.sk=zxc
sys.dev.token=${sys.default.token}

```

#### 如何进行取值

```
    /**
     * 取ak值,通过环境动态获取,此时key需存在
     * 注: 若key可以不存在并默认值为空可调整为 ${sys.${sys.env}.ak:}
     */
    @Value("${sys.${sys.env}.ak}")
    private String ak;

    /**
     * 取sk值,通过环境动态获取,此时key需存在
     */
    @Value("${sys.${sys.env}.sk}")
    private String sk;

    /**
     * 取token值,通过环境动态获取,此时key需存在
     */
    @Value("${sys.${sys.env}.token}")
    private String token;

    /**
     * 取other值,通过环境动态获取,并在key不存在时使用缺省值-1
     */
    @Value("${sys.${sys.env}.other:-1}")
    private String other;

    /**
     * 取otherWithDefault值,先通过环境动态获取再通过default环境获取值最后则为设置的缺省值
     */
    @Value("${sys.${sys.env}.other:${sys.default.other:???}}")
    private String otherWithDefault;

```

### 环境变量名称中不携带环境key

#### 通过application多环境机制: 详见sample04

+ application.properties

```
spring.profiles.active=dev

sys.env.ak=111
sys.env.sk=qwerty
sys.env.token=12345
sys.env.other=ssss
```
+ application-dev.properties

```

sys.env.ak=222
sys.env.sk=zxc
sys.env.token=12345
sys.env.other=

```

#### 在@PropertySource引入对应的配置文件进行配置相关的变量(各环境的配置文件及配置所激活的环境配置文件): 详见sample05

+ @PropertySource Config

```
@Configuration
@PropertySource({"classpath:sys-env-${sys.env}.properties"})
public class ImportEnvConfig {
}

```

+ 通过 application.properties 配置所激活的环境配置文件

```
sys.env=${SYS_ENV:dev}
```

+ sys-env-dev.properties

```

sys.env.ak=222
sys.env.sk=zxc
sys.env.token=12345
sys.env.other=

```

#### 如何进行取值

```

    /**
     * 取ak值,此时key需存在
     * 注: 若key可以不存在并默认值为空可调整为 ${sys.env.ak:}
     */
    @Value("${sys.env.ak}")
    private String ak;

    /**
     * 取sk值,此时key需存在
     */
    @Value("${sys.env.sk}")
    private String sk;

    /**
     * 取token值,此时key需存在
     */
    @Value("${sys.env.token}")
    private String token;

    /**
     * 取other值,并在key不存在时使用缺省值-1
     */
    @Value("${sys.env.other:-1}")
    private String other;

```