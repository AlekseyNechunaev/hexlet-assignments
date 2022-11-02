package exercise;

import java.util.List;
import java.util.Map;

public class PairedTag extends Tag {

    private final String tagBody;
    private final List<Tag> childrenTags;

    public PairedTag(String tagName, Map<String, String> tagAttributes, String tagBody, List<Tag> childrenTags) {
        super(tagName, tagAttributes);
        this.tagBody = tagBody;
        this.childrenTags = childrenTags;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String singleTag = super.toString();
        result.append(singleTag).append(this.tagBody);
        childrenTags.forEach(tag -> result.append(tag.toString()));
        result.append("</").append(this.getTagName()).append(">");
        return result.toString();
    }
}