package pers.star.questionnaire.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class QComponent {
    private String feId;
    private String type;
    private String title;
    private boolean isHidden;
    private boolean isLocked;

    private Map<String, Object> props;
}
