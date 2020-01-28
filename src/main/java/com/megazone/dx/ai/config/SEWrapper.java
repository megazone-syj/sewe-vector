package com.megazone.dx.ai.config;

import org.bytedeco.javacpp.annotation.Platform;
import org.bytedeco.javacpp.annotation.Properties;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

// FIXME: During javacpp compiling time
// [WARNING] Could not load platform properties for class com.saltlux.sc.sw.SeWePredict

/**
 * This configuration file is used for JavaCpp's parser to generate the actual SEWrapper.java
 *  se_wrapper.h includes FastTextApi's declarations,
 *  se_wrapper_javacpp.h includes *.cc files for compiling.
 */
@Properties(value = @Platform(include = {"se_wrapper.h", "se_wrapper_javacpp.h"}),
        target = "com.saltlux.sc.sw.SEWrapper")
public class SEWrapper implements InfoMapper {
    public void map(InfoMap infoMap) {
        infoMap
            .put(new Info("fasttext::real").pointerTypes("float"))
            .put(new Info("std::vector<std::string>").pointerTypes("StringVector").define())
            .put(new Info("std::vector<fasttext::real>").pointerTypes("RealVector").define())
            .put(new Info("std::pair<fasttext::real,std::string>").pointerTypes("FloatStringPair").define())
            .put(new Info("std::vector<std::pair<fasttext::real,std::string> >").pointerTypes("FloatStringPairVector").define())
            .put(new Info("std::pair<fasttext::real,int32_t>").pointerTypes("DoubleIntPair").define());
    }
}