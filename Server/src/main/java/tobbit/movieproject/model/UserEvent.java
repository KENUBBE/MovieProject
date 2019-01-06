package tobbit.movieproject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.EventAttendee;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserEvent {

    @JsonProperty("summary")
    private String summary;
    @JsonProperty("startDate")
    private DateTime startDate;
    private DateTime endDate;
    @JsonProperty("createdBy")
    private String createdBy;

    public UserEvent() {
    }

    public UserEvent(String summary, DateTime startDate, DateTime endDate, String createdBy) {
        this.summary = summary;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }
}
