package ${tableMo.restMo.basePackage};

/**
 * ApiConstants
 *
 * @author <#if templateMo.author??>${templateMo.author}<#else>${sysProps['user.name']}</#if>
 * @since <#if templateMo.time??>${templateMo.time}<#else>${.now?datetime}</#if>
 * @version V1.0
 */
public interface ApiConstants {
    String BASE_URL = "/rest";
}
