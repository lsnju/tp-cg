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

    <insert id="add" <#if tableMo.idColumn??><#if tableMo.idColumn.autoIncrement>useGeneratedKeys="true" keyProperty="${tableMo.idColumn.propName}" </#if></#if>parameterType="${tableMo.daoMo.modelPackage}.${tableMo.daoMo.modelName}">
        insert into ${tableMo.tableName} (
        <include refid="addColumns" />
        ) values
        <![CDATA[
        (
        <#list tableMo.insertList as col>
        <#if col.now>now()<#else>${r"#{"}${col.propName}${r"}"}</#if><#sep>,</#sep>
        </#list>
        )
        ]]>
    </insert>

    <select id="getById" resultMap="RM">
        select
        <include refid="allColumns" />
        from ${tableMo.tableName}
        where ${tableMo.idColumn.columnName} = ${r"#{"}${tableMo.idColumn.propName}${r"}"}
    </select>

    <select id="lockById" resultMap="RM">
        select
        <include refid="allColumns" />
        from ${tableMo.tableName}
        where ${tableMo.idColumn.columnName} = ${r"#{"}${tableMo.idColumn.propName}${r"}"}
        for update nowait
    </select>

    <select id="lockByIdWait" resultMap="RM">
        select
        <include refid="allColumns" />
        from ${tableMo.tableName}
        where ${tableMo.idColumn.columnName} = ${r"#{"}${tableMo.idColumn.propName}${r"}"}
        for update
    </select>

    <update id="update" parameterType="${tableMo.daoMo.modelPackage}.${tableMo.daoMo.modelName}">
        UPDATE ${tableMo.tableName}
        <trim prefix="SET" suffixOverrides=",">
            <![CDATA[
        <#list tableMo.dataColumnList as col>
            ${String.format('%-30s',col.columnName)} = ${r"#{"}${col.propName}${r"}"} ,
        </#list>
        <#if tableMo.gmtModify??>
            ${String.format('%-30s',tableMo.gmtModify.columnName)} = now()
        </#if>
            ]]>
        </trim>
        WHERE ${tableMo.idColumn.columnName} = ${r"#{"}${tableMo.idColumn.propName}${r"}"}
    </update>


    <!-- start pageQuery -->
    <select id="pageQuery" resultMap="RM" parameterType="java.util.HashMap">
        select
        <include refid="allColumns" />
        <![CDATA[
        from ${tableMo.tableName}
        ]]>
        <trim prefix="WHERE" prefixOverrides="AND | OR">
            <include refid="pageQueryCond" />
        </trim>
        <![CDATA[
        order by ${tableMo.idColumn.columnName} desc
        limit  ${r"#{limit}"}
        OFFSET ${r"#{offset}"}
        ]]>
    </select>
    <select id="pageQueryCount" resultType="long" parameterType="java.util.HashMap">
        <![CDATA[
        select
            count(1)
        from ${tableMo.tableName}
        ]]>
        <trim prefix="WHERE" prefixOverrides="AND | OR">
            <include refid="pageQueryCond" />
        </trim>
    </select>
    <sql id="pageQueryCond">
    </sql>
    <!-- end pageQuery -->

    <delete id="delete">
        delete from ${tableMo.tableName} where ${tableMo.idColumn.columnName} = ${r"#{"}${tableMo.idColumn.propName}${r"}"}
    </delete>

    <select id="getByCond" resultMap="RM" parameterType="java.util.HashMap">
        select
        <include refid="allColumns" />
        from ${tableMo.tableName}
        <trim prefix="WHERE" prefixOverrides="AND | OR">
            <if test="pid != null">
                AND pid = ${r"#{pid}"}
            </if>
        </trim>
        order by ${tableMo.idColumn.columnName} desc
        <choose>
            <when test="limit != null and limit > 0">
                limit ${r"#{limit}"}
            </when>
            <otherwise>
                limit 1000
            </otherwise>
        </choose>
    </select>

</mapper>

