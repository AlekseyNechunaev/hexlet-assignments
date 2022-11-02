package exercise;

import java.util.Map;

public abstract class Tag {

    private final String tagName;
    private final Map<String, String> tagAttributes;

    public Tag(String tagName, Map<String, String> tagAttributes) {
        this.tagName = tagName;
        this.tagAttributes = tagAttributes;
    }

    public String getTagName() {
        return tagName;
    }

    public Map<String, String> getTagAttributes() {
        return tagAttributes;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("<");
        result.append(this.getTagName());
        for (Map.Entry<String, String> entry : this.getTagAttributes().entrySet()) {
            result.append(" ").append(entry.getKey()).append("=").append("\"").append(entry.getValue()).append("\"");
        }
        result.append(">");
        return result.toString();
    }
}
