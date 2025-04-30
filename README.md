# tp-cg

tp code generator

## requirement

JDK17+

## maven import

```xml

<dependency>
    <groupId>com.lsnju</groupId>
    <artifactId>tp-cg</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

## usage

```java

@Test
void test_gen_all() {
    //
    gen("task", new String[]{"t_notify_task"}, true, true);
    gen("test", new String[]{"t_test"}, false, false);

}

private static void gen(String moduleName, String[] tableNames, boolean core, boolean rest) {
    try {
        final GenConfigProperties genConfig = new GenConfigProperties();
        genConfig.setNotNull(true);
        genConfig.setGenRepo(core);
        genConfig.setGenRest(rest);
        genConfig.setModuleName(moduleName);
        genConfig.setSpringDocVersion(SpringDocVersionEnum.JDK17);
        genConfig.setRestReturnType(RestReturnTypeEnum.SIMPLE);

        genConfig.setCreateTime("2025-05-20");
        genConfig.setBasePath("/home/ls/test-project");
        genConfig.setBasePackage("com.lsnju.test");
        genConfig.setDalPackage("common.dao");
        genConfig.setRepoPackage("core.model");
        genConfig.setDalModelSuffix("Do");
        genConfig.setMapperPath("/src/main/resources/mappers/");
        genConfig.setRestPackage("web.rest");

        final TableConfigProperties tableConfig = new TableConfigProperties();
        tableConfig.setIgnoreTablePrefix("tb_,t_");
        tableConfig.setIgnoreFieldPrefix("is_");
        tableConfig.setIdField("id,xx_id");
        tableConfig.setModifyField("update_time,update_date");
        tableConfig.setCreateField("create_time,create_date");
        final AutoGenerator generator = AutoGeneratorFactory.autoGenerator(AutoGeneratorConfig.builder()
            .connection(getJdbcConnection())
            .genConfig(genConfig)
            .tableConfig(tableConfig)
            .build());
        for (String tableName : tableNames) {
            log.info("{}", generator.gen(tableName));
        }
    } catch (Exception e) {
        log.error(String.format("%s", e.getMessage()), e);
    }
}
```
