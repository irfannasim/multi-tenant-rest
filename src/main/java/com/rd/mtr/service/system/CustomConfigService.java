package com.rd.mtr.service.system;

import com.rd.mtr.configuration.AppConfigProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CustomConfigService {

    private final Logger logger = LogManager.getLogger(CustomConfigService.class);

    @Autowired
    private AppConfigProperties appConfigProperties;

    CustomConfigService() {
    }

    public List<String> getConfigDetail() {
        logger.info("CustomConfigService - getConfigDetail method call...");
        appConfigProperties.getClientId();
        return Stream.
                of(appConfigProperties.getClientId(), appConfigProperties.getAuthServerScheme(),
                        appConfigProperties.getClientSecret()
                ).collect(Collectors.toList());
    }

}
