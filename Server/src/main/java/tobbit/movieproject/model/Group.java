package tobbit.movieproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Group {
    @JsonProperty("createdBy")
    private String createdBy;
    @JsonProperty("groupName")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("members")
    private ArrayList<String> members;

    public Group(String name, String description, String createdBy, ArrayList<String> members) {
        this.name = name;
        this.description = description;
        this.members = members;
        this.createdBy = createdBy;
    }

    public Group() {
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
}
