package rsp.service.interfaces;

import rsp.enums.AchievementType;
import rsp.model.Achievement;

public interface AchievementService {

    Achievement create(AchievementType achievementType);

}
