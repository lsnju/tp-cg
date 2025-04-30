<#ftl strip_whitespace=true>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${tableMo.daoMo.apiPackage}.${tableMo.daoMo.apiName}">

    <resultMap id="RM" type="${tableMo.daoMo.modelPackage}.${tableMo.daoMo.modelName}">
    <#if tableMo.idColumn??>
        <id property="${tableMo.idColumn.propName}" column="${tableMo.idColumn.columnName}" />
    </#if>
        <#list tableMo.dataColumnList as col>
        <result property="${col.propName}" column="${col.columnName}" />
        </#list>
    <#if tableMo.gmtCreate??>
        <result property="${tableMo.gmtCreate.propName}" column="${tableMo.gmtCreate.columnName}" />
    </#if>
    <#if tableMo.gmtModify??>
        <result property="${tableMo.gmtModify.propName}" column="${tableMo.gmtModify.columnName}" />
    </#if>
    </resultMap>

    <sql id="allColumns">
        <![CDATA[
        <#list tableMo.columnList as col>
        ${col.columnName}<#sep>,</#sep>
        </#list>
        ]]>
    </sql>

    <sql id="addColumns">
        <![CDATA[
        <#list tableMo.insertList as col>
        ${col.columnName}<#sep>,</#sep>
        </#list>
        ]]>
    </sql>

    <select id="lockById" resultMap="RM">
        select
        <include refid="allColumns" />
        from ${tableMo.tableName}
        where ${tableMo.idColumn.columnName} = ${r"#{"}${tableMo.idColumn.propName}${r"}"}
        for update<#if tableMo.dbMo.supportNoWait> nowait</#if>
    </select>

</mapper>

