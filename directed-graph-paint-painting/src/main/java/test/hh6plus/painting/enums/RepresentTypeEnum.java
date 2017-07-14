package test.hh6plus.painting.enums;

/**
 *
 1. dot 默认布局方式，主要用于有向图
 2. neato 基于spring-model(又称force-based)算法
 3. twopi 径向布局
 4. circo 圆环布局
 5. fdp 用于无向图
 */
public enum RepresentTypeEnum {
    DOT("dot"),
    NEATO("neato"),
    FDP("fdp"),
    SFDP("sfdp"),
    TWOPI("twopi"),
    CIRCO("circo");

    private String type;

    RepresentTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
