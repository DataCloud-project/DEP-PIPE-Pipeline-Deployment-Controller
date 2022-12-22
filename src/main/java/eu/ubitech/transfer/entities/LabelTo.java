package eu.ubitech.transfer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LabelTo implements Serializable, Comparable<LabelTo> {

    private Long labelID;
    private String name;
    private Date dateCreated;
    private Date lastModified;

    @Override
    public int compareTo(LabelTo labelTo) {
        return (int)(this.labelID - labelTo.getLabelID());
    }
}
