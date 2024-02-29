package pers.star.questionnaire.oss.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.star.questionnaire.oss.constant.OSSConstants;
import pers.star.questionnaire.oss.exception.IllegalDatasourceProviderException;
import pers.star.questionnaire.oss.exception.NotFoundDatasourceException;
import pers.star.questionnaire.oss.pojo.DataSource;
import pers.star.questionnaire.oss.pojo.TargetType;
import pers.star.questionnaire.oss.properties.OSSProperties;
import pers.star.questionnaire.oss.service.OSSOperation;
import pers.star.questionnaire.oss.service.OSSService;
import pers.star.questionnaire.oss.service.impl.OSSServiceImpl;
import pers.star.questionnaire.oss.service.impl.QiniuOSSImpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@EnableConfigurationProperties(OSSProperties.class)
public class OSSConfig {
    /**
     * 上传目标源
     */
    Map<String, DataSource> datasourceMap;

    /**
     * 上传类型
     */

    Map<String, TargetType> targetTypeMap;

    @Resource
    OSSProperties ossProperties;


    @PostConstruct
    public void init() {
        List<DataSource> datasources = ossProperties.getDatasources();
        datasourceMap = new HashMap<>(datasources.size());
        datasources.forEach(v -> datasourceMap.put(v.getName(), v));

        List<TargetType> targetTypes = ossProperties.getTargetType();
        targetTypeMap = new HashMap<>(targetTypes.size());
        targetTypes.forEach(v -> {
            v.setDataSource(datasourceMap.get(v.getDatasourceName()));
            targetTypeMap.put(v.getName(), v);
        });

    }

    public OSSOperation getDataSource(String name) throws NotFoundDatasourceException, IllegalDatasourceProviderException {

        DataSource dataSource = datasourceMap.get(name);
        if (dataSource == null) {
            throw new NotFoundDatasourceException(name);
        }
        String provider = dataSource.getProvider();
        if (provider.equals(OSSConstants.QINIU)) {
            return new QiniuOSSImpl(dataSource);
        }
        throw new IllegalDatasourceProviderException(provider);
    }

    public TargetType getTargetType(String name) {
        return targetTypeMap.get(name);
    }

    @Bean
    public OSSService ossService() {
        return new OSSServiceImpl();
    }


}
