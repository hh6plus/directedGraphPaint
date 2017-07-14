package test.hh6plus.painting.enums;

/**
 * Created by huhong02 on 17/7/13.
 */
public enum GraphTypeEnum {
    GIF("gif"),
    DOT("dot"),
    FIG("fig"),
    PDF("pdf"),
    PS("ps"),
    SVG("svg"),
    PNG("png"),
    PLAIN("plain");

    private String type;

    GraphTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
