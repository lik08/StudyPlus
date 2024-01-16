package comp3350.studyplus.objects;

public class QuestionTag {
    private String tagId;
    private String tagName;

    // This constructor is for existing tag ids
    public QuestionTag(final String newId, String newTagName) {
        this.tagId = newId;
        this.tagName = newTagName;
    }

    public String getTagId() {
        return tagId;
    }

    public String getTagName() {
        return tagName;
    }

}
