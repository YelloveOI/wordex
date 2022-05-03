package rsp.service;

import rsp.enums.AchievementType;
import rsp.exception.NotFoundException;
import rsp.model.Achievement;
import rsp.repo.AchievementRepo;
import rsp.service.interfaces.AchievementService;

public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepo repo;

    public AchievementServiceImpl(AchievementRepo repo) {
        this.repo = repo;
    }

    @Override
    public Achievement create(AchievementType achievementType) {
        Achievement result = new Achievement();
        String name = "";
        String description = "";
        String imgSource = "";
        boolean changeFlag = true;

        switch(achievementType) {
            case CARDS_LEARNED_10: {
                name = "First 10";
                description = "Learn 10 cards";
                imgSource = "";
                changeFlag = false;

                break;
            }

            case CARDS_LEARNED_25: {
                name = "25";
                description = "Learn 25 cards";
                imgSource = "";
                changeFlag = false;

                break;
            }

            case CARDS_LEARNED_50: {
                name = "50";
                description = "Learn 50 cards";
                imgSource = "";
                changeFlag = false;

                break;
            }

        }

        if(changeFlag) {
            result.setName(name);
            result.setDescription(description);
            result.setImgSource(imgSource);

            repo.save(result);

            return result;
        } else {
            throw NotFoundException.create(Achievement.class.getName(), achievementType);
        }
    }

}
